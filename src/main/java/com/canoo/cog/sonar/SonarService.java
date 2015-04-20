package com.canoo.cog.sonar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.canoo.cog.sonar.model.CityModel;
import com.canoo.cog.sonar.model.SonarProject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

public class SonarService {

    private String baseUrl;
    SonarResultParser sonarResultParser;
    private String userName;
    private String password;

    public SonarService() {
        sonarResultParser = new SonarResultParser();
    }

    public void setSonarSettings(String baseUrl, String userName, String password) {
        this.baseUrl = baseUrl;
        this.userName = userName;
        this.password = password;
    }

    public List<SonarProject> getProjects() throws IOException {

        String projectResultString = callSonarAuth("api/resources", userName, password);
        return sonarResultParser.parseProjects(projectResultString);
    }

    public CityModel getCityData(String projectKey) throws IOException {
        String cityResultString = callSonarAuth(SonarConstants.getMetricsQueryForProject(projectKey), userName, password);
        return sonarResultParser.parseCity(cityResultString);
    }

    String callSonarAuth(String path, String userName, String password) throws IOException {
        URL url = new URL(baseUrl + path);
        String encoding = new String(Base64.encodeBase64((userName + ":" + password).getBytes()));

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", "Basic " + encoding);
        InputStream content = connection.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(content));
        return IOUtils.toString(in);
    }
}

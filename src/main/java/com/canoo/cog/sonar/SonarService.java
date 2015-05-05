package com.canoo.cog.sonar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import com.canoo.cog.sonar.model.CityModel;
import com.canoo.cog.sonar.model.SonarProject;

public class SonarService {

    private String baseUrl;
    SonarResultParser sonarResultParser;
    private String userName;
    private String password;
    private String proxyHost;
    private Integer proxyPort;

    public SonarService() {
        sonarResultParser = new SonarResultParser();
    }

    public void setSonarSettings(String baseUrl, String userName, String password, String proxyHostAndPort) {
        this.baseUrl = baseUrl;
        this.userName = userName;
        this.password = password;
        if (proxyHostAndPort != null && proxyHostAndPort.contains(":")) {
         try {
          String[] split = proxyHostAndPort.split(":");
           proxyHost = split[0];
           proxyPort = Integer.valueOf(split[1]);
        }
        catch (RuntimeException re) {
          // ignore
        }
        }
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
        
        String authPropertyKey = null;
        HttpURLConnection connection = null; 
        String encoding = new String(Base64.encodeBase64((userName + ":" + password).getBytes()));
        
        if (proxyHost != null && proxyPort != null) {
          // use proxy
          Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("webproxy.balgroupit.com", 3128)); 
          authPropertyKey = "Proxy-Authorization";
          connection =  (HttpURLConnection) url.openConnection(proxy);
        }
        else {
          // without proxy
          authPropertyKey = "Authorization";
          connection = (HttpURLConnection) url.openConnection();
        }

        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setRequestProperty(authPropertyKey, "Basic " + encoding);
        InputStream content = connection.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(content));
        return IOUtils.toString(in);
    }
}

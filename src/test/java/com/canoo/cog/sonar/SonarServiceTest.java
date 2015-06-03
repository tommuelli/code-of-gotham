package com.canoo.cog.sonar;

/*
 * #%L
 * code-of-gotham
 * %%
 * Copyright (C) 2015 Canoo Engineering AG
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


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
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

public class SonarServiceTest {

    private final String testBaseUrl = "http://localhost:9000/";
    private SonarService sonarService;

    @Before
    public void setUp() throws SonarException {
        sonarService = new SonarService();
        sonarService.setSonarSettings(testBaseUrl, "", "", "");
    }

    @Test
    public void testCallSonarWithHttpRequest() throws IOException, SonarException {
        String resultString = sonarService.callSonarAuth("api/resources", null, null);
        assertNotNull(resultString);
    }

    @Test
    public void testGetCityData() throws Exception {
        CityModel city = sonarService.getCityData("code-of-gotham:code-of-gotham");
        assertNotNull(city);
    }

    @Test
    public void testGetProjects() throws Exception {
        List<SonarProject> projects = sonarService.getProjects();
        assertNotNull(projects);
    }

    @Test
    public void testAuthenticationSonar() throws IOException {
        try {
            URL url = new URL("https://ci.canoo.com/sonar/api/resources");
            String encoding = new String(Base64.encodeBase64("christophh:".getBytes()));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + encoding);
            InputStream content = (InputStream) connection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(content));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
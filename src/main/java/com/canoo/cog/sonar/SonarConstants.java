package com.canoo.cog.sonar;

import org.junit.experimental.theories.FromDataPoints;

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

public class SonarConstants {
    private static final String DATE_RANGE = "$DATERANGE$";
	private static final String RESOURCE_KEY = "$RESOURCE$";
    private static final String DATE_FROM_REPLACE_PATTERN = "$DATE_FROM$";
    private static final String DATE_TO_REPLACE_PATTERN = "$DATE_TO$";
	public static final String CLASSES = "classes";
    public static final String DIRECTORIES = "directories";
    public static final String FILES = "files";
    public static final String FUNCTIONS = "functions";
    public static final String PUBLIC_API = "public_api";
    public static final String STATEMENTS = "statements";
    public static final String LINES_OF_CODE = "ncloc";
    public static final String COVERAGE = "coverage";
    public static final String TESTS = "tests";
    public static final String COMPLEXITY = "complexity";
    public static final String CLASS_COMPLEXITY = "class_complexity";
    public static final String FILE_COMPLEXITY = "file_complexity";
    public static final String FUNCTION_COMPLEXITY = "function_complexity";
    public static final String COMMENT_LINES = "comment_lines";
    public static final String COMMENT_LINES_DENSITY = "comment_lines_density";
    public static final String SQALE_INDEX = "sqale_index";

    public static final String LIST_OF_METRICS = CLASSES + "," + DIRECTORIES + "," + FILES + "," + FUNCTIONS + "," + PUBLIC_API + "," + STATEMENTS + "," + LINES_OF_CODE + "," + COVERAGE + "," + TESTS + "," + COMPLEXITY + "," + CLASS_COMPLEXITY + "," + FILE_COMPLEXITY + "," + FUNCTION_COMPLEXITY + "," + COMMENT_LINES + "," + COMMENT_LINES_DENSITY + "," + SQALE_INDEX;

    /**
     * Metrics of the project.
     */
    static final String PROJECT_METRICS = "api/resources?resource="+ RESOURCE_KEY +"&depth=-1&metrics=" + LIST_OF_METRICS;
    
    /**
     * List of sonar builds of the project
     */
    static final String PROJECT_TIMEMACHINE = "api/timemachine?resource="+ RESOURCE_KEY +"&metrics=files";

    /**
     * All projects
     */
    static final String SONAR_PROJECTS_QUERY = "api/resources";
    
    
    
    static final String RESOURCE_TIMEMACHINE_WITH_METRICS = "api/timemachine?resource="+ RESOURCE_KEY  +DATE_RANGE+"&metrics=" + LIST_OF_METRICS;
    		
    		
    		//"http://nemo.sonarqube.org/api/timemachine?resource=org.apache.myfaces.tobago:tobago&depth=-1&fromDateTime=2014-12-25T23:59:59+0100&metrics=classes,directories,files,functions,public_api,statements,ncloc,coverage,tests,complexity,class_complexity,file_complexity,function_complexity,comment_lines,comment_lines_density,sqale_index";
    //"http://nemo.sonarqube.org/api/timemachine?resource=org.apache.myfaces.tobago:tobago&depth=-1&fromDateTime=2014-12-25T23:59:59+0100&toDateTime=2014-12-25T23:59:59+0100&metrics=classes,directories,files,functions,public_api,statements,ncloc,coverage,tests,complexity,class_complexity,file_complexity,function_complexity,comment_lines,comment_lines_density,sqale_index";
    static final String BY_ID = "http://nemo.sonarqube.org/api/timemachine?resource=156631&metrics=classes";
    
    
    static String getMetricsQueryForProject(String projectKey) {
        return PROJECT_METRICS.replace(RESOURCE_KEY, projectKey);
    }
    
    static String getTimemachineForProject(String projectKey) {
    	return PROJECT_TIMEMACHINE.replace(RESOURCE_KEY, projectKey);
    }
    
    static String getTimemachineMetricsQueryForResource(String resourceKey, String timeMachineFrom, String timeMachineTo) {
     String requestString = RESOURCE_TIMEMACHINE_WITH_METRICS.replace(RESOURCE_KEY, resourceKey);
     requestString = requestString.replace(DATE_RANGE, "");
     
//     if (timeMachineTo == null){
//    	 requestString = requestString.replace(DATE_RANGE, "&fromDateTime=" + timeMachineFrom);    	 
//     }
//     else {
//    	 requestString = requestString.replace(DATE_RANGE, "&fromDateTime=" + timeMachineFrom + "&toDateTime=" + timeMachineTo);    	     	 
//     }
     return requestString;
    }
    
    
}

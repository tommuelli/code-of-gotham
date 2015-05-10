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


public class SonarConstants {
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

    static final String PROJECT_METRICS = "api/resources?resource=PROJECT&depth=-1&metrics=" + LIST_OF_METRICS;

    static String getMetricsQueryForProject(String projectKey) {
        return PROJECT_METRICS.replace("PROJECT",projectKey);
    }
}

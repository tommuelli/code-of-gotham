package com.canoo.cog.sonar;

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

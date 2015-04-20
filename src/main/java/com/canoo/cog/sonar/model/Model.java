package com.canoo.cog.sonar.model;

public interface Model {
    String getName();

    int getLinesOfCode();

    int getFunctions();
    
     String getComplexity();

	 String getTests();

	 String getCoverage();
}

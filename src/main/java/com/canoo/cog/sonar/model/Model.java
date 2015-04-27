package com.canoo.cog.sonar.model;

public interface Model {
	String getName();

	int getLinesOfCode();

	int getFunctions();

	int getComplexity();
	
	String getFileComplexity();

	double getSqaleIndex();
    public double getComplexityPerLineOfCode();
    public double getSqualeIndexPerLineOfCode();
    
    double getTopComplexityPerLineOfCode();
    double getTopSqualeIndexPerLineOfCode();
    double getTopSqualeIndex();
    
	String getTests();

	String getCoverage();
	
	String getInfo();
}

package com.canoo.cog.sonar.model;

public class ModelImpl implements Model {

    private String name;

    private String linesOfCode;
    private String classes;
    private String files;
    private String directories;
    private String functions;
    private String statements;
    private String publicApi;
    private String commentLines;
    private String complexity;
    private String tests;
    private String classComplexity;
    private String commentLineDensity;
    private String coverage;
    private String fileComplexity;
    private String functionComplexity;

    public ModelImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getLinesOfCode() {
        return getIntValue(linesOfCode);
    }

    @Override
    public int getFunctions() {
        
		return getIntValue(functions);
    }

    private int getIntValue(String aString)
    {
    	if (aString == null || aString.isEmpty())
    	{
    		return 0;
    	}
    	return Float.valueOf(aString).intValue();
    }
    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getComplexity() {
		return complexity;
	}

	public String getTests() {
		return tests == null ? "comming soon ...": tests;
	}

	public String getCoverage() {
		return coverage == null ? "not a number": coverage;
	}

	public void setClassComplexity(String classComplexity) {
        this.classComplexity = classComplexity;
    }

    public void setCommentLines(String commentLines) {
        this.commentLines = commentLines;
    }

    public void setCommentLineDensity(String commentLineDensity) {
        this.commentLineDensity = commentLineDensity;
    }

    public void setComplexity(String complexity) {
        this.complexity = complexity;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public void setDirectories(String directories) {
        this.directories = directories;
    }

    public void setFileComplexity(String fileComplexity) {
        this.fileComplexity = fileComplexity;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public void setFunctionComplexity(String functionComplexity) {
        this.functionComplexity = functionComplexity;
    }

    public void setFunctions(String functions) {
        this.functions = functions;
    }

    public void setLinesOfCode(String linesOfCode) {
        this.linesOfCode = linesOfCode;
    }

    public void setPublicApi(String publicApi) {
        this.publicApi = publicApi;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    public void setStatements(String statements) {
        this.statements = statements;
    }

	@Override
	public String toString() {
		return "ModelImpl [name=" + name + ", linesOfCode=" + linesOfCode
				+ ", classes=" + classes + ", files=" + files
				+ ", directories=" + directories + ", functions=" + functions
				+ ", statements=" + statements + ", publicApi=" + publicApi
				+ ", commentLines=" + commentLines + ", complexity="
				+ complexity + ", tests=" + tests + ", classComplexity="
				+ classComplexity + ", commentLineDensity="
				+ commentLineDensity + ", coverage=" + coverage
				+ ", fileComplexity=" + fileComplexity
				+ ", functionComplexity=" + functionComplexity + "]";
	}

}

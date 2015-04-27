package com.canoo.cog.sonar.model;





import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Locale;

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
	private String sqaleIndex;

	private static double sTopComplexityPerLineOfCode = 0d;

	private static double sTopSqualeIndexPerLineOfCode = 0d;
	private static double sTopSqualeIndex = 0d;

	public double getSqaleIndex() {
		return getDoubleValue(sqaleIndex);
	}

	public void setSqaleIndex(String sqaleIndex) {
		this.sqaleIndex = sqaleIndex;
	}

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

	private int getIntValue(String aString) {
		if (aString == null || aString.isEmpty()) {
			return 0;
		}
		return Float.valueOf(aString).intValue();
	}

	private double getDoubleValue(String aString) {
		if (aString == null || aString.isEmpty()) {
			return 0d;
		}
		return Double.valueOf(aString);
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public int getComplexity() {
		return getIntValue(complexity);
	}

	public String getFileComplexity() {
		return fileComplexity;
	}

	public String getTests() {
		return tests == null ? "comming soon ..." : tests;
	}

	public String getCoverage() {
		return coverage == null ? "not a number" : coverage;
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
	public synchronized double getComplexityPerLineOfCode() {
		if (getComplexity() > 0 && getLinesOfCode() > 0) {
			double d = Double.valueOf(getComplexity())
					/ Double.valueOf(getLinesOfCode());
			if (d > sTopComplexityPerLineOfCode) {
				sTopComplexityPerLineOfCode = d;
			}

			return d;
		}
		return 0d;
	}

	@Override
	public synchronized double getSqualeIndexPerLineOfCode() {
		if (getSqaleIndex() > 0d && getLinesOfCode() > 0) {
			if (getSqaleIndex() > sTopSqualeIndex){
				sTopSqualeIndex = getSqaleIndex();
			}
			double d = getSqaleIndex() / Double.valueOf(getLinesOfCode());
			if (d > sTopSqualeIndexPerLineOfCode) {
				sTopSqualeIndexPerLineOfCode = d;
			}
			return d;
		}
		return 0d;
	}

	@Override
	public double getTopComplexityPerLineOfCode() {
		return sTopComplexityPerLineOfCode;
	}

	@Override
	public double getTopSqualeIndexPerLineOfCode() {
		return sTopSqualeIndexPerLineOfCode;
	}

	@Override
	public double getTopSqualeIndex() {
		return sTopSqualeIndex;
	}

	@Override
	public String getInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append("File: ");
		sb.append(getName());
		sb.append("\n");
		sb.append("Functions: ");
		sb.append(getFunctions());
		sb.append("\n");
		sb.append("Lines of Code: ");
		sb.append(getLinesOfCode());
		sb.append("\n");
		sb.append("Complexity: ");
		sb.append(getComplexity());
		sb.append(" / per line ");
		sb.append(format(getComplexityPerLineOfCode(),2));
		sb.append("\n");
		sb.append("Tech. dept.: ");
		sb.append(getDurationWithUnits(getSqaleIndex()));
		sb.append(" / per line ");
		sb.append(format(getSqualeIndexPerLineOfCode(),2));
		sb.append(" min");
		sb.append("\n");
		sb.append("Test Coverage: ");
		sb.append(getCoverage());
		sb.append(" %");

		return sb.toString();
	}

	private String getDurationWithUnits(double min) {
		
		
		Duration ofMinutes = Duration.ofMinutes((long)min);
		long hours = ofMinutes.toHours();
		long subMin = Duration.ofHours(hours).toMinutes();
		
		
		return hours + "h, " +  (((long)min)-subMin) + " min";
	}

	private String format (double aNumber, int fractionDigits){
	    // Use Locale.ENGLISH for '.' as decimal separamtor
	    NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
	    nf.setGroupingUsed(true);
	    nf.setMaximumFractionDigits(fractionDigits);
	    nf.setRoundingMode(RoundingMode.HALF_UP);
	    return nf.format(aNumber);
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
				+ ", functionComplexity=" + functionComplexity
				+ ", sqaleIndex=" + sqaleIndex
				+ "] -> getSqualeIndexPerLineOfCode() : "
				+ getSqualeIndexPerLineOfCode() + " ("
				+ getTopSqualeIndexPerLineOfCode() + ")"
				+ " -> complexityPerLineOfCode : "
				+ getComplexityPerLineOfCode() + "("
				+ getTopComplexityPerLineOfCode() + ")";
	}

}

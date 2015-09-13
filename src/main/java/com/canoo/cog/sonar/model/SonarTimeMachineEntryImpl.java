package com.canoo.cog.sonar.model;

public class SonarTimeMachineEntryImpl implements SonarTimeMachineEntry{

	String time;
	
	public SonarTimeMachineEntryImpl(String time) {
		super();
		this.time = time;
	}

	@Override
	public String getTime() {
		return time;
	}

	@Override
	/**
	 * Sorts backwards.
	 */
	public int compareTo(SonarTimeMachineEntry o) {
		return o.getTime().compareTo(this.time);
	}

	
	
	
}

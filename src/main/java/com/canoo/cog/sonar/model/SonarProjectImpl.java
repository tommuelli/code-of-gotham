package com.canoo.cog.sonar.model;

public class SonarProjectImpl implements SonarProject {

    private final String key;
    private final String name;
    private final String version;

    public SonarProjectImpl(String key, String name, String version) {
        this.key = key;
        this.name = name;
        this.version = version;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getVersion() {
        return version;
    }
}

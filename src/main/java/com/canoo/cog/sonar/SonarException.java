package com.canoo.cog.sonar;

import java.net.ProtocolException;

public class SonarException extends Exception {
    public SonarException(String message) {
        super(message);
    }

    public SonarException(String message, Exception e) {
        super(message,e);
    }
}

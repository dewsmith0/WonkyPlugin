package io.github.dewsmith0.wonkyplugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class WonkyPlugin {
    public static final String PLUGIN_ID = "wonkyplugin";
    public static final Logger LOGGER = LoggerFactory.getLogger(PLUGIN_ID);
    public static final Color COLOR = new Color(162, 59, 236);
    public static final boolean DEV_MODE = true;
    static {
        LOGGER.info("Wonking it up...");
    }
    public static void initialize() {

    }
}

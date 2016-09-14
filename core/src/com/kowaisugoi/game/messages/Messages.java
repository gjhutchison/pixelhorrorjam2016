package com.kowaisugoi.game.messages;

import com.badlogic.gdx.Gdx;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Messages {

    private static final Properties _messages = init();

    private static Properties init() {
        Properties properties = new Properties();
        try {
            FileInputStream in = new FileInputStream("properties/descriptions.properties");
            properties.load(in);
            in.close();
        } catch (IOException e) {
            Gdx.app.log("ERROR", e.getMessage());
        }
        return properties;
    }

    public static Properties getProperties() {
        return _messages;
    }

    public static String getText(String key) {
        if (_messages.containsKey(key)) {
            return _messages.getProperty(key);
        }
        return "<NO TEXT>";
    }
}

package com.livefyre.yacc;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class YaccFacade {

    private JSONObject config;

    public YaccFacade(JSONObject config) {
        super();
        loadJsonConfig(config);
    }

    public YaccFacade(String filename) throws ConfigParseException {
        super();
        byte[] encoded;
        try {
            encoded = Files.readAllBytes(Paths.get(filename));
        } catch (IOException e) {
            throw new ConfigParseException("Cannot open config file.", e);
        }
        String configStr = (Charset.defaultCharset()).decode(ByteBuffer.wrap(encoded)).toString();
        try {
            loadJsonConfig(new JSONObject(configStr));
        } catch (JSONException e) {
            throw new ConfigParseException("Config file is not valid JSON.", e);
        }
    }
    
    public void loadJsonConfig(JSONObject config) {
        this.config = config;
    }

    public JSONObject getSection(String section) throws ConfigException {
        try {
            return (JSONObject) config.get(section);
        } catch (JSONException e) {
            throw new ConfigException(String.format("Section %s does not exist.", section), e);
        }
    }
    
    public Object get(String section, String name) throws ConfigException {
        try {
            return getSection(section).get(name);
        } catch (JSONException e) {
            throw getOptDoesNotExist(name, e);
        }        
    }
    
    public boolean getBoolean(String section, String name) throws ConfigException {
        try {
            return getSection(section).getBoolean(name);
        } catch (JSONException e) {
            throw getOptDoesNotExist(name, e);
        }
    }
    
    public int getInt(String section, String name) throws ConfigException {
        try {
            return getSection(section).getInt(name);
        } catch (JSONException e) {
            throw getOptDoesNotExist(name, e);
        }
    }

    public double getDouble(String section, String name) throws ConfigException {
        try {
            return getSection(section).getDouble(name);
        } catch (JSONException e) {
            throw getOptDoesNotExist(name, e);
        }
    }

    public JSONObject getJSONObject(String section, String name) throws ConfigException {
        try {
            return (JSONObject) getSection(section).get(name);
        } catch (JSONException e) {
            throw getOptDoesNotExist(name, e);
        }
    }

    public JSONArray getJSONArray(String section, String name) throws ConfigException {
        try {
            return (JSONArray) getSection(section).get(name);
        } catch (JSONException e) {
            throw getOptDoesNotExist(name, e);
        }
    }

    public boolean hasSection(String section) {
        return config.has(section);
    }
    
    public boolean hasOption(String section, String name) {
        try {
            return getSection(section).has(name);
        } catch (ConfigException e) {
            return false;
        }
    }

    private ConfigException getOptDoesNotExist(String name, Exception e) throws ConfigException {
        return new ConfigException(String.format("Option %s does not exist.", name), e);
    }
}

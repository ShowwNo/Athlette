package com.showwno.athlette.manager;

import com.showwno.athlette.object.CustomConfiguration;
import org.bukkit.configuration.ConfigurationSection;

//import java.io.*;

public class Resource {
    public static void set(String path, Object data, CustomConfiguration c) {
        c.getConfiguration().set(path, data);
        c.saveConfiguration();
    }


    private static Object get(String path, CustomConfiguration c) {
        return c.getConfiguration().get(path);
    }
    public static Object get(String path, CustomConfiguration c, Object defaultObject) {
        Object result = get(path, c);
        if (result == null) {
            result = defaultObject;
        }
        return result;
    }


    public static ConfigurationSection getConfigurationSection(String path, CustomConfiguration c) {
        return c.getConfiguration().getConfigurationSection(path);
    }

    /*
    public static byte[] serializeObject(Serializable serializable) {
        byte[] bytes = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(serializable);
            bytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            objectOutputStream.close();
        }
        catch (Exception exception) {
            ProgramLogger.writeJavaException(exception);
        }
        return bytes;
    }

    public static Serializable deserializeObject(byte[] bytes) {
        Serializable serializable = null;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            serializable = (Serializable) objectInputStream.readObject();
            byteArrayInputStream.close();
            objectInputStream.close();
        }
        catch (Exception exception) {

        }
        return serializable;
    }

    public static Serializable deserializeObject(byte[] bytes, Serializable defaultObject) {
        Serializable serializable = deserializeObject(bytes);
        if (serializable == null) {
            serializable = defaultObject;
        }
        return serializable;
    }
     */
}
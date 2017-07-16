package com.markchan.carrier.data.cache.serializer;

import com.google.gson.Gson;
import java.lang.reflect.Type;

/**
 * Created by Mark on 2017/7/17.
 */
public class Serializer {

    private final Gson gson = new Gson();

    /**
     * Serialize an object to Json.
     *
     * @param object to serialize.
     */
    public String serialize(Object object, Type clazz) {
        return gson.toJson(object, clazz);
    }

    /**
     * Deserialize a json representation of an object.
     *
     * @param string A json string to deserialize.
     */
    public <T> T deserialize(String string, Class<T> clazz) {
        return gson.fromJson(string, clazz);
    }

    public <T> T deserialize(String string, Type typeOfT) {
        return gson.fromJson(string, typeOfT);
    }
}

package com.xg.w3.aptos.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.lang.reflect.Type;

public class JSONUtil {
    private static final Gson gson;

    public static <T> String serialize(final T src) {
        return gson.toJson((Object)src);
    }

    public static <T> T deserialize(final String json, final Class<T> classOfT) {
        return (T)gson.fromJson(json, (Class)classOfT);
    }

    public static <T> T deserialize(final String json, final Type typeOfT) {
        return (T)gson.fromJson(json, typeOfT);
    }

    public static <T> T deserialize(final Reader json, final Type typeOfT) {
        return (T)gson.fromJson(json, typeOfT);
    }

    static {
        gson = new GsonBuilder().serializeNulls().create();
    }
}

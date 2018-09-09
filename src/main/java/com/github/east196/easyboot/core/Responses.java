package com.github.east196.easyboot.core;

public class Responses {

    public static final String OK_CODE = "00";
    public static final String OK_MESSAGE = "ok";
    public static final String NOT_FOUND_CODE = "33";
    public static final String NOT_FOUND_MESSAGE = "Not Found Results";


    public static <T> Response success(T instance) {
        return new DataResponse<>(OK_CODE, OK_MESSAGE, instance);
    }

    public static Response notFound() {
        return new Response(NOT_FOUND_CODE, NOT_FOUND_MESSAGE);
    }
}

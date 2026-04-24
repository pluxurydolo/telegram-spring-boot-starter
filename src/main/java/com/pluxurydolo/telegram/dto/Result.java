package com.pluxurydolo.telegram.dto;

public enum Result {
    SUCCESS,
    FAILURE;

    public static Result fromBoolean(boolean result) {
        if (result) {
            return SUCCESS;
        }

        return FAILURE;
    }
}

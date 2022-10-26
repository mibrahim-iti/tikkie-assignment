package com.tikkie.util;

/**
 * @author Mohamed Ibrahim
 * @created Tuesday 25 Oct 2022
 */
public final class HttpStatusUtil {

    private HttpStatusUtil() {

        throw new IllegalStateException("Utility Class");
    }

    public static final int BAD_REQUEST = 400;

    public static final int CONFLICT = 409;

    public static final int CREATED = 201;

    public static final int NO_CONTENT = 204;

    public static final int NOT_FOUND = 404;

    public static final int OK = 200;

    public static final int INTERNAL_SERVER_ERROR = 500;

}

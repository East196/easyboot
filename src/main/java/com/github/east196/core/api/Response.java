package com.github.east196.core.api;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class Response {

    protected String code;
    protected String message;

    public Response() {
    }

    public Response(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return Objects.equal(code, response.code) &&
                Objects.equal(message, response.message);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code, message);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("code", code)
                .add("message", message)
                .toString();
    }
}

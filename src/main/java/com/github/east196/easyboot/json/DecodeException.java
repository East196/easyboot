package com.github.east196.easyboot.json;

/**
 * Instances of this Exception are thrown if failed to decode a JSON string, because of invalid JSON.
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class DecodeException extends RuntimeException {

  public DecodeException() {
  }

  public DecodeException(String message) {
    super(message);
  }

  public DecodeException(String message, Throwable cause) {
    super(message, cause);
  }
}

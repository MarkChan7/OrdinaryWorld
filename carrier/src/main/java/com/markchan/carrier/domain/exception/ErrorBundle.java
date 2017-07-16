package com.markchan.carrier.domain.exception;

/**
 * Created by Mark on 2017/7/16.
 */
public interface ErrorBundle {

    Exception getException();

    String getErrorMessage();
}

package com.markchan.carrier.domain.executor;

import io.reactivex.Scheduler;

/**
 * Created by Mark on 2017/7/16.
 */
public interface PostExecutionThread {

    Scheduler getScheduler();
}

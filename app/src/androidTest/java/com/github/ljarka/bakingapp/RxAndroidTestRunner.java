package com.github.ljarka.bakingapp;

import android.support.test.runner.AndroidJUnitRunner;

import com.squareup.rx2.idler.Rx2Idler;

import io.reactivex.plugins.RxJavaPlugins;

public final class RxAndroidTestRunner extends AndroidJUnitRunner {
    @Override
    public void onStart() {
        RxJavaPlugins.setInitComputationSchedulerHandler(Rx2Idler.create("RxJava 2.x Computation Scheduler"));
        RxJavaPlugins.setInitIoSchedulerHandler(Rx2Idler.create("RxJava 2.x io Scheduler"));
        super.onStart();
    }
}
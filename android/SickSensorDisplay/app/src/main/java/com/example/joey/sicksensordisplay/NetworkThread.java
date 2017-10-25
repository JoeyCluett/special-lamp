package com.example.joey.sicksensordisplay;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by joey on 10/11/17.
 */
public class NetworkThread extends Thread {

    CpjlServerInterface cpjl;
    CanvasSickPlot csp;
    private ReentrantLock buffer_mtx = new ReentrantLock();

    private String hostname = "";
    private int port_number;

    // maintain a buffer of data
    float[] buffer = new float[541];

    public NetworkThread(String hostname, int port_number) {
        this.hostname = hostname;
        this.port_number = port_number;
    }

    void getNewData(float[] out_buffer) {
        buffer_mtx.lock();
        for(int i = 0; i < 541; i++)
            out_buffer[i] = buffer[i];
        buffer_mtx.unlock();
    }

    @Override
    public void run() {
        cpjl = new CpjlServerInterface(hostname, port_number);
        float[] tmp_buffer = new float[541];

        while(true) {
            for(int i = 0; i < 541; i++)
                tmp_buffer[i] = cpjl.extractFloat32();

            buffer_mtx.lock();
            for(int i = 0; i < 541; i++)
                buffer[i] = tmp_buffer[i];
            buffer_mtx.unlock();
        }
    }
}

package com.example.joey.sicksensordisplay;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    private CanvasSickPlot sickCanvas = null;
    private String hostname = "10.42.0.1"; // connected directly to desktop Wi-Fi
    //private String hostname = "joey@134.129.53.255"; // connected via eduroam
    private int port_number = 13000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sickCanvas = (CanvasSickPlot)findViewById(R.id.signature_canvas);
        sickCanvas.setHostnamePortNumber(hostname, port_number);
    }

    public void plotZoomIn(View v) {
        sickCanvas.zoomIn();
    }

    public void plotZoomOut(View v) {
        sickCanvas.zoomOut();
    }
}



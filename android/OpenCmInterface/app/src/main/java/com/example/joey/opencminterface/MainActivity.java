package com.example.joey.opencminterface;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.SeekBar;
import android.widget.Toast;

import static com.example.joey.opencminterface.R.id.seek_bar_1;

public class MainActivity extends Activity {

    SeekBar[] seek_bars = new SeekBar[4];
    int[]     seek_bar_ids = new int[4];
    int[]     seek_bar_progs = new int[4];

    CpjlServerInterface cpjlsi;

    public void enableStrictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup network communication
        enableStrictMode();
        cpjlsi = new CpjlServerInterface("10.42.0.1", 13000);

        seek_bars[0] = (SeekBar)findViewById(R.id.seek_bar_0);
        seek_bars[1] = (SeekBar)findViewById(R.id.seek_bar_1);
        seek_bars[2] = (SeekBar)findViewById(R.id.seek_bar_2);
        seek_bars[3] = (SeekBar)findViewById(R.id.seek_bar_3);

        // setup each seek bar with appropiate ID
        for(int i = 0; i < 4; i++) {
            seek_bar_ids[i] = seek_bars[i].getId();
            seek_bars[i].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    for(int i = 0; i < 4; i++) {
                        if(seekBar.getId() == seek_bar_ids[i]) {
                            seek_bar_progs[i] = progress;
                            cpjlsi.insertInt32(seek_bar_progs[i]);
                        }
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    return;
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // display the new value on screen
                    for(int i = 0; i < 4; i++) {
                        if(seekBar.getId() == seek_bar_ids[i]) {
                            Toast.makeText(MainActivity.this, "Seek bar " + (i + 1) + " status is : " + seek_bar_progs[i], Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            });
        }

    }
}

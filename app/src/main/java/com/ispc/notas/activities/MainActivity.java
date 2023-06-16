package com.ispc.notas.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.ispc.notas.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView titleTextView = findViewById(R.id.titleTextView);
        Button startButton = findViewById(R.id.startButton);
        VideoView videoView = findViewById(R.id.videoView);

        titleTextView.setText("Notas");

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListadoNotasActivity.class);
                startActivity(intent);
            }
        });

        String videoPath = "android.resource://" + getPackageName() + "/" + "/raw/videouso";
        Uri videoUri = Uri.parse(videoPath);
        videoView.setVideoURI(videoUri);
        videoView.start();
    }
}

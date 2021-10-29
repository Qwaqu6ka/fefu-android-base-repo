package ru.fefu.activitytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class login_activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageButton back = findViewById(R.id.back_log);
        back.setOnClickListener(this);
        Button logButton = findViewById(R.id.logButton_log);
        logButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_log:
                finish();
                break;
            case R.id.logButton_log:
//                Intent i = new Intent(this, register_activity.class);
//                startActivity(i);
//                finish();
                break;
        }
    }
}
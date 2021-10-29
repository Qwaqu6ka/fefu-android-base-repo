package ru.fefu.activitytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class register_activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ImageButton back = findViewById(R.id.back_reg);
        back.setOnClickListener(this);

        Button regButton = findViewById(R.id.regButton_reg);
        regButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_reg:
                finish();
                break;
            case R.id.regButton_reg:
                Intent i = new Intent(this, login_activity.class);
                startActivity(i);
                break;
        }
    }
}
package ru.fefu.welcomescreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ru.fefu.activitytracker.R;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button regButton = findViewById(R.id.regButton_welcome);
        regButton.setOnClickListener(this);

        Button logButton = findViewById(R.id.logButton_welcome);
        logButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.regButton_welcome:
                i = new Intent(this, RegisterActivity.class);
                startActivity(i);
                break;
            case R.id.logButton_welcome:
                i = new Intent(this, LoginActivity.class);
                startActivity(i);
                break;
        }
    }
}

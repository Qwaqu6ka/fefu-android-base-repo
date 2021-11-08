package ru.fefu.activitytracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class login_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.logToolbar);
        toolbar.setNavigationOnClickListener(e -> onBackPressed());

        Button logButton = findViewById(R.id.logButton_log);
        logButton.setOnClickListener(e -> {
            Intent i = new Intent(this, body_activity.class);
            startActivity(i);
            finish();
        });
    }
}
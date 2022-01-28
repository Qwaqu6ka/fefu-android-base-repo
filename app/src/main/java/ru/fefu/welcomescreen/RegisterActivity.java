package ru.fefu.welcomescreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import ru.fefu.activitytracker.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.regToolbar);

        toolbar.setNavigationOnClickListener(e -> onBackPressed());

        Button regButton = findViewById(R.id.regButton_reg);
        regButton.setOnClickListener(e -> {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        });
    }
}
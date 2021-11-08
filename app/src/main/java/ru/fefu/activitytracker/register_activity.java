package ru.fefu.activitytracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class register_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.regToolbar);

        toolbar.setNavigationOnClickListener(e -> onBackPressed());

        Button regButton = findViewById(R.id.regButton_reg);
        regButton.setOnClickListener(e -> {
            Intent i = new Intent(this, login_activity.class);
            startActivity(i);
        });
    }
}
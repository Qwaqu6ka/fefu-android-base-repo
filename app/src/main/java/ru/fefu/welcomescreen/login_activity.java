package ru.fefu.welcomescreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import ru.fefu.activitytracker.R;
import ru.fefu.mainmenu.MainPartActivity;

public class login_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.logToolbar);
        toolbar.setNavigationOnClickListener(e -> onBackPressed());

        Button logButton = findViewById(R.id.logButton_log);
        logButton.setOnClickListener(e -> {
            Intent i = new Intent(this, MainPartActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finishAffinity();
        });
    }
}
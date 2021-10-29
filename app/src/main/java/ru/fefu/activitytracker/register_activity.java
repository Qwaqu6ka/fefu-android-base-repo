package ru.fefu.activitytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class register_activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ImageButton back = findViewById(R.id.back_reg);
        back.setOnClickListener(this);

        Button regButton = findViewById(R.id.regButton_reg);
        regButton.setOnClickListener(this);

        ListView lv = findViewById(R.id.sex_list);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.sex_list,
                android.R.layout.simple_list_item_single_choice
        );
        lv.setAdapter(adapter);
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
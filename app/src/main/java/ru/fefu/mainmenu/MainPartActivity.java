package ru.fefu.mainmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import ru.fefu.activitytracker.R;


public class MainPartActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{

    FragmentManager fm = getSupportFragmentManager();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment active = fm.findFragmentByTag("ActivityFragment");
        Fragment profile = fm.findFragmentByTag("ProfileFragment");
        switch (item.getItemId()) {
            case R.id.activity_bottom_button:
                if (active != null)
                    fm.beginTransaction().show(active).commit();
                else
                    fm.beginTransaction().add(R.id.container, new ActivityFragment(), "ActivityFragment").commit();

                if (profile != null)
                    fm.beginTransaction().hide(profile).commit();

                return true;

            case R.id.profile_bottom_button:
                if (profile != null)
                    fm.beginTransaction().show(profile).commit();
                else
                    fm.beginTransaction().add(R.id.container, new ProfileFragment(), "ProfileFragment").commit();

                if (active != null)
                    fm.beginTransaction().hide(active).commit();
                return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(this);

        if (savedInstanceState == null) {
            fm.beginTransaction()
                .add(
                    R.id.container, new ActivityFragment(), "ActivityFragment"
                )
                .commit();
        }
    }


}
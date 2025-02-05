package ru.fefu.mainmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import ru.fefu.activities.PagerFragment;
import ru.fefu.activitytracker.R;
import ru.fefu.profile.ProfileFragment;


public class MainPartActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{

    public static boolean IS_ALIVE = false;

    FragmentManager fm = getSupportFragmentManager();

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment active = fm.findFragmentByTag("ActivityFragment");
        Fragment profile = fm.findFragmentByTag("ProfileFragment");
        switch (item.getItemId()) {
            case R.id.activity_bottom_button:
                if (active != null)
                    fm.beginTransaction().show(active).commit();
                else
                    fm.beginTransaction().add(R.id.container, new PagerFragment(), "ActivityFragment").commit();

                while (fm.getBackStackEntryCount() != 0)
                    fm.popBackStackImmediate();

                if (profile != null)
                    fm.beginTransaction().hide(profile).commit();

                return true;

            case R.id.profile_bottom_button:
                if (profile != null)
                    fm.beginTransaction().show(profile).commit();
                else
                    fm.beginTransaction().add(R.id.container, new ProfileFragment(), "ProfileFragment").commit();

                while (fm.getBackStackEntryCount() != 0) {
                    fm.popBackStackImmediate();
                }

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

        IS_ALIVE = true;

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(this);

        if (savedInstanceState == null) {
            fm.beginTransaction()
                .add(R.id.container, new PagerFragment(), "ActivityFragment")
                .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IS_ALIVE = false;
    }
}
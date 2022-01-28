package ru.fefu.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import ru.fefu.activitytracker.R;
import ru.fefu.currentActivity.MapActivity;
import ru.fefu.mainmenu.FragmentAdapter;

public class PagerFragment extends Fragment {

    TabLayout tabLayout;
    FragmentAdapter adapter;
    ViewPager2 viewPager2;
    ImageButton button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activity_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = requireView().findViewById(R.id.tabLayout);
        viewPager2 = requireView().findViewById(R.id.viewPager2);
        button = requireView().findViewById(R.id.startButton);

        FragmentManager fm = requireActivity().getSupportFragmentManager();
        adapter = new FragmentAdapter(fm, getLifecycle());
        viewPager2.setAdapter(adapter);

        TabLayoutMediator mediator = new TabLayoutMediator(
            tabLayout,
            viewPager2,
            (tab, position) -> {
                switch (position) {
                    case 0:
                        tab.setText(R.string.my_activity);
                        break;
                    case 1:
                        tab.setText(R.string.user_activity);
                        break;
                }
            }
        );
        mediator.attach();

        button.setOnClickListener(e -> {
            Intent i = new Intent(getActivity(), MapActivity.class);
            startActivity(i);
        });
    }
}
package ru.fefu.currentActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cardView: CardView = binding.cardView
        val fm: FragmentManager = supportFragmentManager

        cardView.setBackgroundResource(R.drawable.card_view_bg)

        fm.beginTransaction()
            .add(R.id.container, PrepareFragment(), "PrepareFragment")
            .commit()
    }
}
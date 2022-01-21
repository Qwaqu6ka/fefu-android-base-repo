package ru.fefu.profile

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentChangePassBinding
import ru.fefu.basefragment.BaseFragment

class ChangePassFragment : BaseFragment<FragmentChangePassBinding>(R.layout.fragment_change_pass) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar = binding.toolbar
        val acceptBut: MaterialButton = binding.acceptButton

        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
        acceptBut.setOnClickListener() { activity?.onBackPressed() }
    }
}
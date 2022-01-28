package ru.fefu.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentProfileBinding
import ru.fefu.basefragment.BaseFragment
import ru.fefu.welcomescreen.WelcomeActivity


class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fm = activity?.supportFragmentManager
        val changePass = fm?.findFragmentByTag("ChangePassFragment")
        val profile = fm?.findFragmentByTag("ProfileFragment")
        binding.changePassButton.setOnClickListener {
            if (profile != null) {
                if (changePass != null)
                    fm.beginTransaction()
                        .hide(profile)
                        .show(changePass)
                        .addToBackStack("ChangePassTrans")
                        .commit()
                else
                    fm.beginTransaction()
                        .hide(profile)
                        .add(R.id.container, ChangePassFragment(), "ChangePassFragment")
                        .addToBackStack("ChangePassTrans")
                        .commit()
            }
        }

        binding.exitButton.setOnClickListener {
            val i = Intent(activity, WelcomeActivity::class.java)
            startActivity(i)
            activity?.finish()
        }
    }
}
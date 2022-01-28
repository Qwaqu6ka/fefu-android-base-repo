package ru.fefu.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentProfileBinding
import ru.fefu.api.models.ResponseModel
import ru.fefu.basefragment.BaseFragment
import ru.fefu.database.App
import ru.fefu.welcomescreen.WelcomeActivity

class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = context?.getSharedPreferences("user", Context.MODE_PRIVATE)
        val name = sharedPreferences?.getString("name", "")
        val login = sharedPreferences?.getString("login", "")

        binding.login.editText?.setText(login)
        binding.name.editText?.setText(name)


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
            logout()
        }
    }

    private fun logout() {
        App.getApi.logout().enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                Log.d("myLog", ""+response)
                if (response.isSuccessful) {
                    val sharedPreferences =
                        context?.getSharedPreferences("user", Context.MODE_PRIVATE)
                    sharedPreferences?.edit()?.putString("token", "")?.apply()
                    startActivity(Intent(activity, WelcomeActivity::class.java))
                    activity?.finish()
                } else {
                    Toast.makeText(
                        activity,
                        "Что-то пошло не так\n${response.errorBody()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}
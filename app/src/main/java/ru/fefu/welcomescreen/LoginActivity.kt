package ru.fefu.welcomescreen

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.fefu.activitytracker.databinding.ActivityLoginBinding
import ru.fefu.api.models.LoginData
import ru.fefu.api.models.ResponseModel
import ru.fefu.database.App
import ru.fefu.mainmenu.MainPartActivity

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        binding.logButton.setOnClickListener {
            login()
        }
    }

    private fun login() {
        App.getApi.login(
            binding.loginField.text.toString(),
            binding.passField.text.toString()
        ).enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.isSuccessful) {
                    val sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
                    sharedPreferences.edit().putString("token", response.body()?.token).apply()
                    sharedPreferences.edit().putString("login", response.body()?.user?.login).apply()
                    sharedPreferences.edit().putString("name", response.body()?.user?.name).apply()
                    startMain()
                }
                else {
                    Toast.makeText(parent, "Неверные данные", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun startMain() {
        val i = Intent(this, MainPartActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(i)
        finishAffinity()
    }
}
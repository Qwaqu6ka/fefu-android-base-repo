package ru.fefu.welcomescreen

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.ActivityRegisterBinding
import ru.fefu.api.models.RegistrationData
import ru.fefu.api.models.ResponseModel
import ru.fefu.database.App
import ru.fefu.mainmenu.MainPartActivity

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding

    private var gender = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        binding.men.setOnClickListener { gender = 0 }
        binding.women.setOnClickListener { gender = 1 }

        binding.regButton.setOnClickListener {
            if (binding.pass.editText?.text.toString() != binding.rePass.editText?.text.toString()) {
                Toast.makeText(this, "Неверно повторили пароль", Toast.LENGTH_SHORT).show()
            }
            else {
                register()
            }
        }
    }

    private fun register() {
        App.getApi.register(
            binding.loginField.text.toString(),
            binding.passField.text.toString(),
            binding.nameField.text.toString(),
            gender
        ).enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.isSuccessful) {
                    val sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
                    val body = response.body()
                    body?.let {
                        sharedPreferences.edit().putString("token", it.token).apply()
                        sharedPreferences.edit().putString("login", it.user.login).apply()
                        sharedPreferences.edit().putString("name", it.user.name).apply()
                    }
                    startMain()
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun startMain() {
        startActivity(Intent(this, MainPartActivity::class.java))
        finish()
    }
}
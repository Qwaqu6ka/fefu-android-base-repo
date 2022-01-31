package ru.fefu.welcomescreen

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.fefu.activitytracker.databinding.ActivityRegisterBinding
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
            if (binding.passField.text.toString() != binding.rePassField.text.toString()) {
                Toast.makeText(this, "Неверно повторили пароль", Toast.LENGTH_SHORT).show()
            } else {
                binding.regButton.isEnabled = false
                val login = binding.loginField.text.toString()
                val pass = binding.passField.text.toString()
                val name = binding.nameField.text.toString()
                if (login.isNotEmpty() && pass.length >= 8 && name.isNotEmpty() &&
                    validation(login) && validation(pass))
                    register(login, pass, name)
                else {
                    Toast.makeText(
                        this,
                        "Логин и пароль могут состоять из латинских букв, цифр, подчёркивания и дефиса. Также не ложно быть пустых полей. Минимальная длинна пароля 8 символов.",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.regButton.isEnabled = true
                }
            }
        }
    }

    private fun validation(str: String): Boolean {
        for (c in str) {
            if (!c.isDigit() && !c.isLetter() && c != '_' && c != '-')
                return false
        }
        return true
    }

    private fun register(login: String, pass: String, name: String) {
        App.getApi.register(
            login,
            pass,
            name,
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
                } else {
                    Toast.makeText(this@RegisterActivity, "Неверные данные", Toast.LENGTH_SHORT)
                        .show()
                }
                binding.regButton.isEnabled = true
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
//                t.printStackTrace()
                Toast.makeText(
                    parent,
                    "Что-то пошло не по плану, попробуйте позже",
                    Toast.LENGTH_SHORT
                ).show()
                binding.regButton.isEnabled = true
            }
        })
    }

    fun startMain() {
        startActivity(Intent(this, MainPartActivity::class.java))
        finishAffinity()
    }
}
package ru.fefu.api.models

data class User(
    val id: Int,
    val name: String,
    val login: String,
    val gender: Gender
)

package ru.fefu.api.models

data class ErrorData(
    val arr: List<Field>
)

data class Field(
    val someString: String
)
package ru.fefu.activities

class TestList {
    private val tempList = listOf(
        RecyclerItemClass (
            distance = "1200.65 км",
            duration = "1 час",
            activityType = "Седлание ракет",
            time = "1 минут назад",
            userName = ""
        ),
        RecyclerItemClass (
            distance = "1200.65 км",
            duration = "2 часа",
            activityType = "Седлание ракет",
            time = "2 минут назад",
            userName = "@some_PsYcho?"
        ),
        RecyclerItemClass (
            distance = "1200.65 км",
            duration = "3 часа",
            activityType = "Седлание ракет",
            time = "5 минут назад",
            userName = "@some_PsYcho?"
        ),
        RecyclerItemClass (
            distance = "1200.65 км",
            duration = "4 часа",
            activityType = "Седлание ракет",
            time = "5 минут назад",
            userName = "@some_PsYcho?"
        ),
        RecyclerItemClass (
            distance = "1200.65 км",
            duration = "5 часа",
            activityType = "Седлание ракет",
            time = "5 минут назад",
            userName = "@some_PsYcho?"
        ),
        RecyclerItemClass (
            distance = "1200.65 км",
            duration = "6 часа",
            activityType = "Седлание ракет",
            time = "5 минут назад",
            userName = "@some_PsYcho?"
        ),
        RecyclerItemClass (
            distance = "1200.65 км",
            duration = "7 часа",
            activityType = "Седлание ракет",
            time = "5 минут назад",
            userName = "@some_PsYcho?"
        ),
        RecyclerItemClass (
            distance = "1200.65 км",
            duration = "4 часа",
            activityType = "Седлание ракет",
            time = "5 минут назад",
            userName = "@some_PsYcho?"
        ),
        RecyclerItemClass (
            distance = "1200.65 км",
            duration = "4 часа",
            activityType = "Седлание ракет",
            time = "5 минут назад",
            userName = "@some_PsYcho?"
        ),
        RecyclerItemClass (
            distance = "1200.65 км",
            duration = "4 часа",
            activityType = "Седлание ракет",
            time = "5 минут назад",
            userName = "@some_PsYcho?"
        )
    )
    fun getList(): List<RecyclerItemClass> = tempList
}
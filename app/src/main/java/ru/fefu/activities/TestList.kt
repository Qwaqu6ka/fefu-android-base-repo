package ru.fefu.activities

class TestList {
    private var tempList = listOf(
        RecyclerItemClass(
            distance = "53",
            duration = "432",
            activityType = "Зарядка",
            time = "7:30",
            userName = "@qwa"
        ),
        RecyclerItemClass(
            distance = "53",
            duration = "432",
            activityType = "Зарядка",
            time = "7:30",
            userName = "@qwa"
        ),
        RecyclerItemClass(
            distance = "53",
            duration = "432",
            activityType = "Зарядка",
            time = "7:30",
            userName = "@qwa"
        ),
    )

    fun getList(): List<RecyclerItemClass> = tempList
}
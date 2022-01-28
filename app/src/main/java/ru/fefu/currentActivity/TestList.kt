package ru.fefu.currentActivity

class TestList {
    private var tempList = listOf<RecyclerItem>(
        RecyclerItem(
            name = "Велосипед",
            isActive = false,
            image = 123
        ),
        RecyclerItem(
            name = "Бег",
            isActive = false,
            image = 123
        ),
        RecyclerItem(
            name = "Шаг",
            isActive = false,
            image = 123
        ),
    )

    fun getList(): List<RecyclerItem> = tempList
}
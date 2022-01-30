package ru.fefu.activities

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.CoordToDistance
import ru.fefu.activitytracker.R
import ru.fefu.database.Activity
import java.text.SimpleDateFormat
import java.util.*

class RecyclerAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemClickListener: (Activity) -> Unit = {}
    private var listToShow: List<Activity> = listOf()

    companion object {
        private const val ITEM_TYPE_ACTIVE = 1
        private const val ITEM_TYPE_TIME = 2
    }

    @SuppressLint("SimpleDateFormat")
    val formatter = SimpleDateFormat("MMMM yyyy")

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Activity>) {
        listToShow = list
        datePositions.clear()
        this.countDatePositions()
        this.notifyDataSetChanged()
    }

    private val datePositions = mutableListOf<Pair<Int, String>>()
    private fun countDatePositions() {
        var lastDate = ""
        for (i in listToShow.indices) {
            val itemDate = formatter.format(listToShow[i].finishTime ?: 0)
            if (itemDate != lastDate) {
                lastDate = itemDate
                datePositions.add(datePositions.size + i to itemDate)
            }
        }
    }

    private fun binSearch(k: Int, list: List<Pair<Int, String>>, l: Int, r: Int) : Boolean {
        if (l > r) return false

        val m = (l + r) / 2
        if (list[m].first == k)
            return true

        return if (list[m].first < k)
            binSearch(k, list, m + 1, r)
        else
            binSearch(k, list, l, m - 1)
    }

    override fun getItemViewType(position: Int): Int {
        return if (binSearch(position, datePositions, 0, datePositions.size - 1)) ITEM_TYPE_TIME
        else ITEM_TYPE_ACTIVE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE_ACTIVE) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.active_item, parent, false)
            ActiveViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.data_view_type, parent, false)
            DateViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == ITEM_TYPE_ACTIVE) {
            var numOfDates = 0
            for (item in datePositions) {
                if (item.first < position)
                    ++numOfDates
                else
                    break
            }
            (holder as ActiveViewHolder).bind(listToShow[position - numOfDates])
        }
        else {
            for (item in datePositions) {
                if (item.first == position) {
                    (holder as DateViewHolder).bind(item.second)
                    break
                }
            }
        }
    }

    override fun getItemCount(): Int = listToShow.size + datePositions.size

    fun setItemClickListener(listener: (Activity) -> Unit) {
        itemClickListener = listener
    }

    class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dataText: TextView = itemView.findViewById(R.id.data)

        fun bind(data: String) {
            dataText.text = data
        }
    }

    inner class ActiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dist: TextView = itemView.findViewById(R.id.dist)
        private val duration: TextView = itemView.findViewById(R.id.duration)
        private val activity: TextView = itemView.findViewById(R.id.activity)
        private val time: TextView = itemView.findViewById(R.id.time)
        private val name: TextView = itemView.findViewById(R.id.name)

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(card: Activity) {
            activity.text = card.activeType.value

            val distance = CoordToDistance.getDistanceFromLatLonInM(card.coordinates)
            if (distance > 1000)
                dist.text = "${"%.2f".format(distance / 1000)} км"
            else
                dist.text = "${distance.toInt()} м"

            var millis = card.finishTime!!.time - card.startTime.time
            var hours = (millis / (60 * 60 * 1000)).toInt()
            var minutes = (millis / (60 * 1000)).toInt()
            if (hours > 0)
                duration.text = "$hours часов ${minutes - 60 * hours} минут"
            else if (minutes > 0)
                duration.text = "$minutes минут"
            else
                duration.text = "Меньше минуты"

            millis = Date().time - card.finishTime.time
            hours = (millis / (60 * 60 * 1000)).toInt()
            minutes = (millis / (60 * 1000)).toInt()
            if (hours > 24)
                time.text = SimpleDateFormat("d M y").format(card.finishTime)
            else if (hours > 0)
                time.text = "$hours часов ${minutes - 60 * hours} минут"
            else if (minutes > 0)
                time.text = "$minutes минут"
            else
                time.text = "Меньше минуты"

            if (card.userName == "defalut@user") // имя если активноть наша
                name.text = ""
            else
                name.text = card.userName

            itemView.setOnClickListener {
                itemClickListener.invoke(card)
            }
        }
    }
}

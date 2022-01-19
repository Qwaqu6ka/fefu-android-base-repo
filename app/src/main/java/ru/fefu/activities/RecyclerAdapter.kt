package ru.fefu.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.R

class RecyclerAdapter(activities: List<RecyclerItemClass>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemClickListener: (Array<String?>) -> Unit = {}

    private val mutableActivity = activities.toMutableList()

    companion object {
        private const val ITEM_TYPE_ACTIVE = 1
        private const val ITEM_TYPE_TIME = 2
    }

    override fun getItemViewType(position: Int): Int =
        if (position == 0 || position == 3) ITEM_TYPE_TIME
        else ITEM_TYPE_ACTIVE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE_ACTIVE) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.active_item, parent, false)
            ActiveViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.data_view_type, parent, false)
            DataViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == ITEM_TYPE_ACTIVE) {
            val activePosition =
                when {
                    position in 0..3 -> position - 1
                    position > 3 -> position - 2
                    else -> position
                }
            (holder as ActiveViewHolder).bind(mutableActivity[activePosition])
        }
        else {
            (holder as DataViewHolder).bind()
        }
    }

    override fun getItemCount(): Int = mutableActivity.size

    fun setItemClickListener(listener: (Array<String?>) -> Unit) {
        itemClickListener = listener
    }

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dataText: TextView = itemView.findViewById(R.id.data)

        fun bind() {
            dataText.text = "Вчера"
        }
    }

    inner class ActiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dist: TextView = itemView.findViewById(R.id.dist)
        private val duration: TextView = itemView.findViewById(R.id.duration)
        private val activity: TextView = itemView.findViewById(R.id.activity)
        private val time: TextView = itemView.findViewById(R.id.time)
        private val name: TextView = itemView.findViewById(R.id.name)

        // @SuppressLint("SetText")
        fun bind(card: RecyclerItemClass) {
            dist.text = card.distance
            duration.text = card.duration
            activity.text = card.activityType
            time.text = card.time
            name.text = card.userName

            itemView.setOnClickListener {
                itemClickListener.invoke(arrayOf(
                    card.distance,
                    card.duration,
                    card.activityType,
                    card.time,
                    card.userName
                ))
            }
        }
    }
}

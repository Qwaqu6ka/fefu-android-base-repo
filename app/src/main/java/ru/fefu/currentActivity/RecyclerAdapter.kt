package ru.fefu.currentActivity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.R
import ru.fefu.database.ActiveTypes
import kotlin.coroutines.coroutineContext

class RecyclerAdapter(activeType: Array<ActiveTypes>, context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mutActiveType = activeType.toMutableList()
    private var itemClickListener: (Int) -> Unit = {}
    private val listOfHolders = mutableListOf<RecyclerView.ViewHolder>()

    @SuppressLint("UseCompatLoadingForDrawables")
    private val selectedBack: Drawable? = context.getDrawable(R.drawable.selected_card_bg)

    @SuppressLint("UseCompatLoadingForDrawables")
    private val unselectedBack: Drawable? = context.getDrawable(R.drawable.unselected_card_bg)

    fun setItemClickListener(listener: (Int) -> Unit) {
        itemClickListener = listener
    }

    override fun getItemCount(): Int = mutActiveType.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_to_run, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyViewHolder).bind(mutActiveType[position], position)
        listOfHolders.add(holder)
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val activeTypeText:TextView = itemView.findViewById(R.id.text)

        fun bind(item: ActiveTypes, position: Int) {
            activeTypeText.text = item.value
            if (position == 0) {
                itemView.background = selectedBack
            }

            itemView.setOnClickListener {
                if (it.background != selectedBack) {
                    for (i in listOfHolders) {
                        if (i.itemView.background == selectedBack) {
                            i.itemView.background = unselectedBack
                            break
                        }
                    }
                    it.background = selectedBack

                }
                itemClickListener.invoke(position)
            }
        }
    }
}
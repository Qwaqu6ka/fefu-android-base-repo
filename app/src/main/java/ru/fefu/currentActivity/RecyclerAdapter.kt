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
import kotlin.coroutines.coroutineContext

class RecyclerAdapter(activeType: List<RecyclerItem>, context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mActiveType = activeType.toMutableList()
    private var itemClickListener: (Int) -> Unit = {}
    private val listOfHolders = mutableListOf<RecyclerView.ViewHolder>()

    @SuppressLint("UseCompatLoadingForDrawables")
    private val selectedBack: Drawable? = context.getDrawable(R.drawable.selected_card_bg)

    @SuppressLint("UseCompatLoadingForDrawables")
    private val unselectedBack: Drawable? = context.getDrawable(R.drawable.unselected_card_bg)

    fun setItemClickListener(listener: (Int) -> Unit) {
        itemClickListener = listener
    }

    override fun getItemCount(): Int = mActiveType.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_to_run, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyViewHolder).bind(mActiveType[position], position)
        listOfHolders.add(holder)
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val activeTypeText:TextView = itemView.findViewById(R.id.text)

        fun bind(item: RecyclerItem, position: Int) {
            activeTypeText.text = item.name
            if (position == 0) {
                itemView.background = selectedBack
                mActiveType[0].isActive = true
            }

            itemView.setOnClickListener {
                if (!mActiveType[position].isActive) {
                    for (i in mActiveType.indices) {
                        if (mActiveType[i].isActive) {
                            mActiveType[i].isActive = false
                            listOfHolders[i].itemView.background = unselectedBack
                            break
                        }
                    }

                    mActiveType[position].isActive = true
                    itemView.background = selectedBack

                }
                itemClickListener.invoke(position)
            }
        }
    }
}
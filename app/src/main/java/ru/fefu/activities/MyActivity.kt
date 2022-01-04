package ru.fefu.activities

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentMyActivityBinding
import ru.fefu.basefragment.BaseFragment
import ru.fefu.mainmenu.MainPartActivity
import ru.fefu.welcomescreen.login_activity

class MyActivity : BaseFragment<FragmentMyActivityBinding>(R.layout.fragment_my_activity) {

    private val myList = TestList()

    private val myAdapter = RecyclerAdapter(myList.getList())


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myAdapter.setItemClickListener {
            val i = Intent(activity, ActivityDetails::class.java)
            i.putExtra("itemInfo", it)
            startActivity(i)
        }

        binding.recycler.adapter = myAdapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
    }
}
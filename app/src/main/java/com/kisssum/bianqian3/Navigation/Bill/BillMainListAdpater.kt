package com.kisssum.bianqian3.Navigation.Bill

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.kisssum.bianqian3.Data.Database.BillDatabase
import com.kisssum.bianqian3.R

class BillMainListAdpater(context: Context) :
    RecyclerView.Adapter<BillMainListAdpater.myViewHodel>() {
    private val db = Room.databaseBuilder(context, BillDatabase::class.java, "bill").build()
    private val billDao = db.billDao()

    class myViewHodel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHodel {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_bill, parent, false)

        return myViewHodel(item)
    }

    override fun onBindViewHolder(holder: myViewHodel, position: Int) {
       
    }

    override fun getItemCount() = 0
}
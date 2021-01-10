package com.kisssum.bianqian3.Navigation.Bill

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kisssum.bianqian3.Data.Entity.Bill
import com.kisssum.bianqian3.R

class BillMainListAdpater(context: Context) :
    RecyclerView.Adapter<BillMainListAdpater.myViewHodel>() {
    private var data: List<Bill>? = null

    fun updateData(newData: List<Bill>) {
        data = newData
        notifyDataSetChanged()
    }

    class myViewHodel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHodel {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_bill, parent, false)

        return myViewHodel(item)
    }

    override fun onBindViewHolder(holder: myViewHodel, position: Int) {
        val bill = data?.get(position)?.uid
        holder.name.text = "$bill"
    }

    override fun getItemCount() = data!!.size
}
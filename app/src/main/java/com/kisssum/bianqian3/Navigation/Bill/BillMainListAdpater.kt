package com.kisssum.bianqian3.Navigation.Bill

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kisssum.bianqian3.Data.Entity.Bill
import com.kisssum.bianqian3.R
import java.util.*

class BillMainListAdpater(val context: Context, val billViewModel: BillViewModel) :
    RecyclerView.Adapter<BillMainListAdpater.myViewHodel>() {
    private var data: List<Bill>? = null

    fun updateData(newData: List<Bill>) {
        data = newData
        notifyDataSetChanged()
    }

    class myViewHodel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notes = itemView.findViewById<TextView>(R.id.notes)
        val price = itemView.findViewById<TextView>(R.id.price)
        val time = itemView.findViewById<TextView>(R.id.time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHodel {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_bill, parent, false)

        return myViewHodel(item)
    }

    override fun onBindViewHolder(holder: myViewHodel, position: Int) {
        val bill = data?.get(position)!!

        holder.notes.text = bill.notes
        holder.price.text = when {
            (bill.price > 0) -> "+${bill.price}"
            else -> "-${bill.price}"
        }

        val t = Calendar.getInstance()
        t.timeInMillis = bill.time
        holder.time.text = "${t.get(Calendar.HOUR_OF_DAY)}:${t.get(Calendar.MINUTE)}"

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("uid", bill.uid)

            Navigation.findNavController(context as Activity, R.id.fragment_main)
                .navigate(R.id.action_tabControlFragment_to_billEditFragment, bundle)
        }

        holder.itemView.setOnLongClickListener {
            Snackbar.make(it, "是否删除?", Snackbar.LENGTH_SHORT).setAction("确定") {
                billViewModel.getBillDao().dels(bill)
                billViewModel.update()
            }.show()
            true
        }
    }

    override fun getItemCount() = data!!.size
}
package com.kisssum.bianqian3.Navigation.Bill

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kisssum.bianqian3.Data.Entity.Bill
import com.kisssum.bianqian3.Navigation.ViewModel
import com.kisssum.bianqian3.R
import java.util.*

class BillAdpater(val context: Context, val viewModel: ViewModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data: List<Bill>? = null
    private val NO_TITLE = 0
    private val HAVE_TITLE = 1
    private val typeImg = arrayListOf(
        R.drawable.icon_consumption,
        R.drawable.icon_eat,
        R.drawable.icon_shop,
        R.drawable.icon_housing,
        R.drawable.icon_traffic,
        R.drawable.icon_phone,
        R.drawable.icon_sing,
        R.drawable.icon_medical,
        R.drawable.icon_education,
        R.drawable.icon_redpackge,
        R.drawable.icon_tourism,
        R.drawable.icon_travel,
        R.drawable.icon_savings,
        R.drawable.icon_bankcard,
        R.drawable.icon_other
    )

    fun updateData(newData: List<Bill>) {
        data = newData
        notifyDataSetChanged()
    }

    class NoTitleViewHodel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon = itemView.findViewById<ImageView>(R.id.icon)
        val notes = itemView.findViewById<TextView>(R.id.notes)
        val price = itemView.findViewById<TextView>(R.id.price)
        val time = itemView.findViewById<TextView>(R.id.time)
    }

    class HaveTitleViewHodel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon = itemView.findViewById<ImageView>(R.id.icon)
        val notes = itemView.findViewById<TextView>(R.id.notes)
        val price = itemView.findViewById<TextView>(R.id.price)
        val time = itemView.findViewById<TextView>(R.id.time)
        val longTime = itemView.findViewById<TextView>(R.id.longTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        // 根据类型判断用什么布局
        NO_TITLE -> {
            val item = LayoutInflater.from(parent.context)
                .inflate(R.layout.bill_list_item_no_date, parent, false)

            NoTitleViewHodel(item)
        }
        else -> {
            val item = LayoutInflater.from(parent.context)
                .inflate(R.layout.bill_list_item_have_date, parent, false)

            HaveTitleViewHodel(item)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val bill = data?.get(position)!!

        when {
            (holder is NoTitleViewHodel) -> {
                holder.notes.text = bill.notes
                holder.price.text = when {
                    (bill.price > 0) -> "-${bill.price}"
                    else -> "${bill.price}"
                }

                val t = Calendar.getInstance()
                t.timeInMillis = bill.time
                holder.time.text = "${t.get(Calendar.HOUR_OF_DAY)}:${t.get(Calendar.MINUTE)}"
                holder.icon.setImageResource(typeImg[bill.type])
            }
            (holder is HaveTitleViewHodel) -> {
                holder.notes.text = bill.notes
                holder.price.text = when {
                    (bill.price >= 0) -> "-${bill.price}"
                    else -> "${bill.price}"
                }

                val t = Calendar.getInstance()
                t.timeInMillis = bill.time
                holder.time.text = "${t.get(Calendar.HOUR_OF_DAY)}:${t.get(Calendar.MINUTE)}"
                holder.icon.setImageResource(typeImg[bill.type])

                var week = when (t.get(Calendar.DAY_OF_WEEK)) {
                    1 -> "星期日"
                    2 -> "星期一"
                    3 -> "星期二"
                    4 -> "星期三"
                    5 -> "星期四"
                    6 -> "星期五"
                    7 -> "星期六"
                    else -> ""
                }

                if ((t.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance()
                        .get(Calendar.DAY_OF_YEAR))
                    && (t.get(Calendar.YEAR) == Calendar.getInstance()
                        .get(Calendar.YEAR))
                )
                    week = "今天"

                holder.longTime.text =
                    "${t.get(Calendar.YEAR)}年${t.get(Calendar.MONTH) + 1}月${t.get(Calendar.DAY_OF_MONTH)}日 $week"
            }
        }

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("uid", bill.uid)

            Navigation.findNavController(context as Activity, R.id.fragment_main)
                .navigate(R.id.action_tabControlFragment_to_billEditFragment, bundle)
        }

        holder.itemView.setOnLongClickListener {
            Snackbar.make(it, "是否删除?", Snackbar.LENGTH_SHORT).setAction("确定") {
                viewModel.getBillDao().dels(bill)
                viewModel.reLoadBillData()
            }.show()
            true
        }
    }

    override fun getItemViewType(position: Int) = when (position) {
        // 根据时间返回布局类型
        0 -> HAVE_TITLE
        else -> {
            val lastTime = Calendar.getInstance()
            val nowTime = Calendar.getInstance()
            lastTime.timeInMillis = data?.get(position - 1)?.time!!
            nowTime.timeInMillis = data?.get(position)?.time!!

            // 判断是否在同一天
            when {
                ((lastTime.get(Calendar.YEAR) == nowTime.get(Calendar.YEAR))
                        && (lastTime.get(Calendar.MONTH) == nowTime.get(Calendar.MONTH))
                        && (lastTime.get(Calendar.DAY_OF_MONTH) == nowTime.get(Calendar.DAY_OF_MONTH))) -> NO_TITLE
                else -> HAVE_TITLE
            }
        }
    }

    override fun getItemCount() = data!!.size
}
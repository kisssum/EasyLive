package com.kisssum.bianqian3.Navigation.Meno

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kisssum.bianqian3.Data.Entity.Meno
import com.kisssum.bianqian3.Navigation.ViewModel
import com.kisssum.bianqian3.R
import com.kisssum.bianqian3.databinding.MenoItemBinding
import java.util.*

class MenoAdpater(val context: Context, val viewModel: ViewModel) :
    RecyclerView.Adapter<MenoAdpater.DefaultViewHolder>() {
    private var data: List<Meno>? = null

    inner class DefaultViewHolder(binding: MenoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title = binding.menoShowTitle
        val time = binding.menoShowTime
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultViewHolder {
        val binding = MenoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DefaultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DefaultViewHolder, position: Int) {
        // 获取对应meno
        val meno = data?.get(position)

        // 是否用内容替代标题
        if (meno?.title.toString() != "") {
            holder.title.text = meno?.title.toString()
        } else {
            holder.title.text = meno?.text.toString()
        }

        // 时间显示
        val lastTime = meno?.lastTime
        val c = Calendar.getInstance()
        val newC = Calendar.getInstance()
        c.timeInMillis = lastTime!!
        holder.time.text = when {
            (c[Calendar.YEAR] == newC[Calendar.YEAR] && c[Calendar.MONTH] == newC[Calendar.MONTH] && c[Calendar.DAY_OF_MONTH] == newC[Calendar.DAY_OF_MONTH]) -> {
                "${c[Calendar.HOUR_OF_DAY]}:${c[Calendar.MINUTE]}"
            }
            (c[Calendar.YEAR] == newC[Calendar.YEAR] && c[Calendar.MONTH] == newC[Calendar.MONTH] && c[Calendar.DAY_OF_MONTH] != newC[Calendar.DAY_OF_MONTH])
                    || (c[Calendar.YEAR] == newC[Calendar.YEAR] && c[Calendar.MONTH] != newC[Calendar.MONTH]) -> {
                "${c[Calendar.MONTH] + 1}月${c[Calendar.DAY_OF_MONTH]}日"
            }
            (c[Calendar.YEAR] != newC[Calendar.YEAR]) -> {
                "${c[Calendar.YEAR]}年${c[Calendar.MONTH] + 1}月${c[Calendar.DAY_OF_MONTH]}日"
            }
            else -> ""
        }

        // 单击
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("uid", meno.uid)

            Navigation.findNavController(context as Activity, R.id.fragment_main)
                .navigate(R.id.action_tabControlFragment_to_menoEditFragment, bundle)
        }

        // 长按
        holder.itemView.setOnLongClickListener {
            Snackbar.make(it, "是否删除?", Snackbar.LENGTH_SHORT).setAction("确定") {
                viewModel.getMenoDao().deletes(meno)
                viewModel.reLoadMenoData()
            }.show()
            true
        }
    }

    override fun getItemCount() = data!!.size

    fun refreshData(newData: List<Meno>) {
        data = newData
        notifyDataSetChanged()
    }
}
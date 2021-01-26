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
        val meno = data?.get(position)

        holder.title.text = meno?.title.toString()

        val lastTime = meno?.lastTime
        val c = Calendar.getInstance()
        c.timeInMillis = lastTime!!
        holder.time.text =
            "${c[Calendar.YEAR]}年${c[Calendar.MONTH] + 1}月${c[Calendar.DAY_OF_MONTH]}日"

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("uid", meno!!.uid)

            Navigation.findNavController(context as Activity, R.id.fragment_main)
                .navigate(R.id.action_tabControlFragment_to_menoEditFragment, bundle)
        }

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
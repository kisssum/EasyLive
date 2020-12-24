package com.kisssum.bianqian3.Navigation.Bill

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.room.Room
import com.kisssum.bianqian3.Data.Database.BillDatabase
import com.kisssum.bianqian3.Data.Entity.Bill
import com.kisssum.bianqian3.R
import com.kisssum.bianqian3.databinding.FragmentBillEditBinding
import kotlin.math.floor

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BillEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BillEditFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentBillEditBinding
    private var price = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBillEditBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBtn()
    }

    private fun initBtn() {
        binding.btnCancel.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
        }

//        binding.btnDone.setOnClickListener {
//            val billDao =
//                Room.databaseBuilder(requireContext(), BillDatabase::class.java, "bill")
//                    .allowMainThreadQueries().build()
//                    .billDao()
//
//
//            // 测试
//            billDao.delAll()
//
//            val b1 = Bill(notes = binding.tNotes.text.toString())
//            billDao.inserts(b1)
//
//            Log.d("TAG", billDao.getCount().toString())
//            val all = billDao.getAll()
//
//            for (i in all) {
//                Log.d("TAG", "${i.uid} ${i.notes} ${i.time}")
//            }
//        }

        binding.btn0.setOnClickListener(this)
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        binding.btn4.setOnClickListener(this)
        binding.btn5.setOnClickListener(this)
        binding.btn6.setOnClickListener(this)
        binding.btn7.setOnClickListener(this)
        binding.btn8.setOnClickListener(this)
        binding.btn9.setOnClickListener(this)
        binding.btnCut.setOnClickListener(this)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BillEditFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BillEditFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn1, R.id.btn2, R.id.btn3,
            R.id.btn4, R.id.btn5, R.id.btn6,
            R.id.btn7, R.id.btn8, R.id.btn9 -> {
                // 判断字符长度是否超过8位
                if (price.toString().length >= 10) {
                    Toast.makeText(requireContext(), "已超过最大字符数", Toast.LENGTH_SHORT).show()
                    return
                } else {
                    // 计算
                    val number = (v as Button).text.toString().toInt()
                    price = if (price == 0.0) number * 1.0
                    else price * 10 + number
                }
            }
            R.id.btnCut -> {
                price = floor(price / 10)
            }
            else -> 0.0
        }

        binding.tPrice.text =
            if (price == floor(price) && price == 0.0) price.toString()
            else if (price == floor(price) && price != 0.0) price.toInt().toString()
            else price.toString()
    }
}
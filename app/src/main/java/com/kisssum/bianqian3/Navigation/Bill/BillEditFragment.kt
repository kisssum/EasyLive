package com.kisssum.bianqian3.Navigation.Bill

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.kisssum.bianqian3.Data.Entity.Bill
import com.kisssum.bianqian3.R
import com.kisssum.bianqian3.databinding.FragmentBillEditBinding
import java.util.*
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
class BillEditFragment() : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentBillEditBinding
    private lateinit var billViewModel: BillViewModel
    private var price = 0.0
    private var price2 = 0.0
    private var ch = ""
    private var type = 0
    private var uid = -1
    private lateinit var bill: Bill

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

        initBinding()
        restoreOrinit()
        initBtn()

    }

    private fun restoreOrinit() {
        // 是否是列表而不是新建账单
        if (arguments != null)
            uid = arguments?.getInt("uid")!!

        if (uid == -1) {
            bill = Bill()
        } else {
            bill = billViewModel.getBillDao().findBill(uid)

            // 金额
            binding.tPrice.text = bill.price.toString()
            price = bill.price

            // 笔记
            binding.tNotes.setText(bill.notes)
            binding.type.setSelection(bill.type)
        }

        // 时间
        val time = Calendar.getInstance()
        time.timeInMillis = bill.time
        binding.time.text =
            "${time[Calendar.MONTH] + 1}月${time[Calendar.DAY_OF_MONTH]}日 ${time[Calendar.HOUR_OF_DAY]}:${time[Calendar.MINUTE]}"
    }

    private fun initBinding() {
        billViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(BillViewModel::class.java)
    }

    private fun initBtn() {
        binding.btnCancel.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
        }

        binding.type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                type = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        binding.time.setOnClickListener {
            val time = Calendar.getInstance()
            time.timeInMillis = bill.time
            var year = time[Calendar.YEAR]
            var month = time[Calendar.MONTH]
            var day = time[Calendar.DAY_OF_MONTH]
            var hour = time[Calendar.HOUR_OF_DAY]
            var minute = time[Calendar.MINUTE]

            AlertDialog.Builder(requireContext())
                .setMessage("设置时间")
                .setNegativeButton("日期") { dialogInterface: DialogInterface, i: Int ->
                    DatePickerDialog(
                        requireContext(),
                        { datePicker: DatePicker, i: Int, i1: Int, i2: Int ->
                            year = i
                            month = i1
                            day = i2

                            time.set(year, month, day, hour, minute)
                            bill.time = time.timeInMillis

                            // 时间
                            binding.time.text =
                                "${time[Calendar.MONTH] + 1}月${time[Calendar.DAY_OF_MONTH]}日 ${time[Calendar.HOUR_OF_DAY]}:${time[Calendar.MINUTE]}"
                        }, year, month, day
                    ).show()
                }
                .setPositiveButton("时间") { dialogInterface: DialogInterface, i: Int ->
                    TimePickerDialog(
                        requireContext(),
                        { timePicker: TimePicker, i: Int, i1: Int ->
                            hour = i
                            minute = i1

                            time.set(year, month, day, hour, minute)
                            bill.time = time.timeInMillis

                            // 时间
                            binding.time.text =
                                "${time[Calendar.MONTH] + 1}月${time[Calendar.DAY_OF_MONTH]}日 ${time[Calendar.HOUR_OF_DAY]}:${time[Calendar.MINUTE]}"
                        }, hour, minute, true
                    ).show()
                }
                .create()
                .show()

        }

        binding.btnDone.setOnClickListener {
            bill.price = price
            bill.notes = binding.tNotes.text.toString()
            bill.type = type

            val billDao = billViewModel.getBillDao()
            if (uid == -1) {
                bill.time = Calendar.getInstance().timeInMillis
                billDao.inserts(bill)
            } else billDao.updates(bill)

            // 更新数据
            billViewModel.update()

            Toast.makeText(requireContext(), "账单已保存", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
        }

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
        binding.btnLess.setOnClickListener(this)
        binding.btnPlus.setOnClickListener(this)
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
            R.id.btn0 -> {
                price *= 10
            }
            R.id.btn1, R.id.btn2, R.id.btn3,
            R.id.btn4, R.id.btn5, R.id.btn6,
            R.id.btn7, R.id.btn8, R.id.btn9 -> {
                // 判断字符长度是否超过8位
                if (price.toString().length >= 10
                    || price2.toString().length >= 10
                ) {
                    Toast.makeText(requireContext(), "已超过最大字符数", Toast.LENGTH_SHORT).show()
                    return
                } else {
                    // 计算
                    val number = (v as Button).text.toString().toInt()

                    if (ch == "") {
                        price = if (price == 0.0) number * 1.0
                        else price * 10 + number
                    } else {
                        price2 = if (price2 == 0.0) number * 1.0
                        else price2 * 10 + number
                    }
                }
            }
            R.id.btnCut -> {
                if (ch == "") price = floor(price / 10)
                else {
                    if (price2 == 0.0) ch = ""
                    else price2 = floor(price2 / 10)
                }
            }
            R.id.btnLess -> {
                if (price == 0.0) return

                if (ch == "") ch = "-"
                else {
                    if (ch == "-") price -= price2
                    else if (ch == "+") price += price2
                    price2 = 0.0
                    ch = ""
                }
            }
            R.id.btnPlus -> {
                if (price == 0.0) return

                if (ch == "") ch = "+"
                else {
                    if (ch == "-") price -= price2
                    else if (ch == "+") price += price2
                    price2 = 0.0
                    ch = ""
                }
            }
            else -> 0.0
        }

        // 显示
        val oneNumber =
            if (price == 0.0) price.toString()
            else if (price == floor(price) && price != 0.0) price.toInt().toString()
            else price.toString()

        val twoNumber =
            if (price2 == 0.0) ""
            else if (price2 == floor(price2) && price2 != 0.0) price2.toInt().toString()
            else price2.toString()

        binding.tPrice.text =
            if (ch == "") oneNumber
            else oneNumber + ch + twoNumber
    }
}
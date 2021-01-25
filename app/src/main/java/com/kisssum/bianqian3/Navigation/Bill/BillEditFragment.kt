package com.kisssum.bianqian3.Navigation.Bill

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.kisssum.bianqian3.Data.Entity.Bill
import com.kisssum.bianqian3.R
import com.kisssum.bianqian3.databinding.FragmentBillEditBinding
import java.util.*


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
    private lateinit var bill: Bill
    private var ch = ""
    private var uid = -1
    private var price = ""

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

        restoreOrinit()
        initBtn()
    }

    private fun hideInput() {
        val imm = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
        val v = requireActivity().window.peekDecorView()
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }

    private fun restoreOrinit() {
        // 连接viewModel
        billViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(BillViewModel::class.java)

        // 获取uid，-1为新建账单,其它为还原账单
        uid = if (arguments == null) -1
        else arguments?.getInt("uid")!!

        bill = if (uid == -1) Bill()
        else billViewModel.getBillDao().findBill(uid)

        // 金额
        binding.tPrice.text = bill.price.toString()
        price = if (bill.price.toString() == "0.0") ""
        else bill.price.toString()

        // 笔记
        binding.tNotes.setText(bill.notes)
        // 类型
        binding.type.setSelection(bill.type)
        // 时间
        val time = Calendar.getInstance()
        time.timeInMillis = bill.time
        binding.time.text =
            "${time[Calendar.MONTH] + 1}月${time[Calendar.DAY_OF_MONTH]}日 ${time[Calendar.HOUR_OF_DAY]}:${time[Calendar.MINUTE]}"
    }

    private fun initBtn() {
        // 返回按钮
        binding.btnCancel.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
            hideInput()
        }

        // 选择类型
        binding.type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                bill.type = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        // 选择时间
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

                            // 时间
                            time.set(year, month, day, hour, minute)
                            binding.time.text =
                                "${time[Calendar.MONTH] + 1}月${time[Calendar.DAY_OF_MONTH]}日 ${time[Calendar.HOUR_OF_DAY]}:${time[Calendar.MINUTE]}"
                            bill.time = time.timeInMillis
                        }, year, month, day
                    ).show()
                }
                .setPositiveButton("时间") { dialogInterface: DialogInterface, i: Int ->
                    TimePickerDialog(
                        requireContext(),
                        { timePicker: TimePicker, i: Int, i1: Int ->
                            hour = i
                            minute = i1

                            // 时间
                            time.set(year, month, day, hour, minute)
                            binding.time.text =
                                "${time[Calendar.MONTH] + 1}月${time[Calendar.DAY_OF_MONTH]}日 ${time[Calendar.HOUR_OF_DAY]}:${time[Calendar.MINUTE]}"
                            bill.time = time.timeInMillis
                        }, hour, minute, true
                    ).show()
                }
                .setNeutralButton("cancel") { dialogInterface: DialogInterface, i: Int -> }
                .create()
                .show()
        }

        binding.btnDone.setOnClickListener { save() }

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
        binding.btnOK.setOnClickListener(this)
    }

    private fun save() {
        bill.price = if (price == "") 0.0 else price.toDouble()
        bill.notes = binding.tNotes.text.toString()

        val billDao = billViewModel.getBillDao()
        if (uid == -1) billDao.inserts(bill) else billDao.updates(bill)

        // 更新数据并返回
        billViewModel.update()

        Toast.makeText(requireContext(), "账单已保存", Toast.LENGTH_SHORT).show()
        Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
        hideInput()
    }

    private fun calPriceLen(v: View) {
        if (price.length >= 8) {
            Toast.makeText(requireContext(), "已超过最大字符数", Toast.LENGTH_SHORT).show()
            return
        } else {
            price += (v as Button).text.toString()
        }
    }

    private fun calPrice(v: View) {
        // 判断price不是“” - +
        if (price != "" && price.last() != '-' && price.last() != '+') {
            // 如果ch为空就给符号
            if (ch == "") {
                ch = if ((v as Button).id == R.id.btnLess) "-" else "+"
                price += ch
            } else {
                // 计算
                val list = price.split(ch)

                price = when (ch) {
                    "-" -> {
                        // 两个正数
                        if (list.size == 2)
                            (list[0].toDouble() - list[1].toDouble()).toString()
                        // 第一个数负数
                        else
                            (-list[1].toDouble() - list[2].toDouble()).toString()
                    }
                    else -> {
                        // 两个正数
                        if (list.size == 2)
                            (list[0].toDouble() + list[1].toDouble()).toString()
                        // 第一个数负数
                        else
                            (-list[1].toDouble() + list[2].toDouble()).toString()
                    }
                }

                ch = ""
            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn0 -> {
                if (price == "") return else calPriceLen(v)
            }
            R.id.btn1, R.id.btn2, R.id.btn3,
            R.id.btn4, R.id.btn5, R.id.btn6,
            R.id.btn7, R.id.btn8, R.id.btn9 -> {
                calPriceLen(v)
            }
            R.id.btnCut -> {
                if (price != "") price = price.substring(0, price.length - 1)
            }
            R.id.btnLess, R.id.btnPlus -> {
                calPrice(v)
            }
            R.id.btnOK -> {
                save()
            }
        }

        binding.tPrice.text = if (price == "") "0.0" else price
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
}
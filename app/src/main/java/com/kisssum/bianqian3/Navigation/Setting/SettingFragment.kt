package com.kisssum.bianqian3.Navigation.Setting

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.kisssum.bianqian3.Navigation.ViewModel
import com.kisssum.bianqian3.databinding.FragmentSettingBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingMainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentSettingBinding
    private lateinit var viewModel: ViewModel

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
        binding = FragmentSettingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initBtn()
    }

    private fun initBtn() {
        binding.btnClearBill.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setMessage("是否删除所有账单?")
                .setPositiveButton("确认") { dialogInterface: DialogInterface, i: Int ->
                    viewModel.getBillDao().delAll()
                    viewModel.reLoadBillData()
                }
                .setNeutralButton("取消") { dialogInterface: DialogInterface, i: Int -> }
                .create()
                .show()
        }

        binding.btnClearMeno.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setMessage("是否删除所有账单?")
                .setPositiveButton("确认") { dialogInterface: DialogInterface, i: Int ->
                    viewModel.getMenoDao().delAll()
                    viewModel.reLoadMenoData()
                }
                .setNeutralButton("取消") { dialogInterface: DialogInterface, i: Int -> }
                .create()
                .show()
        }

        binding.changeSwitchUiMode.setOnCheckedChangeListener { buttonView, isChecked ->
            run {
                when (isChecked) {
                    true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }

        binding.exit.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setMessage("是否退出软件?")
                .setPositiveButton("确认") { dialogInterface: DialogInterface, i: Int ->
                    requireActivity().finish()
                }
                .setNeutralButton("取消") { dialogInterface: DialogInterface, i: Int -> }
                .create()
                .show()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(ViewModel::class.java)

        viewModel.getBillData().observe(requireActivity()) {
            binding.billCount.text = it.size.toString()
        }

        viewModel.getMenoData().observe(requireActivity()) {
            binding.menoCount.text = it.size.toString()
        }
    }

    override fun onResume() {
        super.onResume()

        binding.changeSwitchUiMode.isChecked = isDarkTheme(requireContext())
    }

    private fun isDarkTheme(context: Context): Boolean {
        val flag = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return flag == Configuration.UI_MODE_NIGHT_YES
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingMainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
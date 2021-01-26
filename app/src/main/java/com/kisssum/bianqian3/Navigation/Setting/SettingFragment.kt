package com.kisssum.bianqian3.Navigation.Setting

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(ViewModel::class.java)

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
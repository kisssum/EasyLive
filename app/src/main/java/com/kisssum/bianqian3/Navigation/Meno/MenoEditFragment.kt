package com.kisssum.bianqian3.Navigation.Meno

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.kisssum.bianqian3.Data.Entity.Meno
import com.kisssum.bianqian3.Navigation.ViewModel
import com.kisssum.bianqian3.R
import com.kisssum.bianqian3.databinding.FragmentMenoEditBinding
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MenoEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenoEditFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentMenoEditBinding
    private lateinit var meno: Meno
    private lateinit var viewModel: ViewModel
    private var uid = -1

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
        binding = FragmentMenoEditBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBinding()
        initView()
        restoreOrinit()
    }

    private fun restoreOrinit() {
        uid = if (arguments == null)
            -1
        else
            arguments?.getInt("uid", -1)!!

        meno = if (uid == -1) {
            Meno()
        } else {
            viewModel.getMenoDao().findMeno(uid)
        }

        binding.menoEditTitle.setText(meno.title)
        binding.menoEditText.setText(meno.text)

        val c = Calendar.getInstance()
        c.timeInMillis = meno.lastTime
        binding.menoEditTime.text =
            "${c[Calendar.YEAR]}年${c[Calendar.MONTH] + 1}月${c[Calendar.DAY_OF_MONTH]}日" +
                    "${c[Calendar.HOUR_OF_DAY]}:${c[Calendar.MINUTE]}"
    }

    private fun initBinding() {
        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(ViewModel::class.java)
    }

    private fun initView() {
        binding.menoEditBar.apply {
            this.setNavigationOnClickListener {
                hideInput()
                Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
            }

            this.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.item_save -> save()
                    else -> ""
                }
                true
            }
        }
    }

    private fun save() {
        meno.title = binding.menoEditTitle.text.toString()
        meno.lastTime = Calendar.getInstance().timeInMillis
        meno.text = binding.menoEditText.text.toString()

        viewModel.getMenoDao().inserts(meno)
        viewModel.reLoadMenoData()

        Toast.makeText(requireContext(), "Save successful", Toast.LENGTH_SHORT).show()
        hideInput()
        Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
    }

    private fun hideInput() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        val v = requireActivity().window.peekDecorView()
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MenoEditFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MenoEditFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
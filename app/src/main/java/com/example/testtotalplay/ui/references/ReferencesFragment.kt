package com.example.testtotalplay.ui.references

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtotalplay.databinding.FragmentReferencesBinding
import com.example.testtotalplay.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReferencesFragment : Fragment() {

    private var _binding : FragmentReferencesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReferencesViewModel by viewModels()
    private val adapter = ReferencesAdapter()
    private var pref: SharedPreferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentReferencesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
        pref = requireActivity().getSharedPreferences("DATA_USER", AppCompatActivity.MODE_PRIVATE)
        viewModel.getListado(pref!!.getString("session", "").toString())
        setUiComponents()
        setObservers()
    }

    private fun setUiComponents() {
        binding.toolbarReferences.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.rvReferences.apply {
            adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    private fun setObservers(){
        viewModel.res.observe(viewLifecycleOwner){
            when(it.status){
                Resource.Status.SUCCESS -> {
                    if(it.data != null){
                        if(it.data.arrayReferences.isNotEmpty()){
                            binding.titleReferences.visibility = View.VISIBLE
                            binding.rvReferences.visibility = View.VISIBLE
                            binding.tvInfoError.visibility = View.GONE
                            binding.progressbar.visibility = View.GONE
                            adapter.addItems(it.data.arrayReferences)
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    binding.titleReferences.visibility = View.GONE
                    binding.rvReferences.visibility = View.GONE
                    binding.progressbar.visibility = View.GONE
                    binding.tvInfoError.visibility = View.VISIBLE
                    binding.tvInfoError.text = "${it.message}"
                }
                Resource.Status.LOADING -> {
                    binding.titleReferences.visibility = View.GONE
                    binding.rvReferences.visibility = View.GONE
                    binding.tvInfoError.visibility = View.GONE
                    binding.progressbar.visibility = View.VISIBLE
                }
            }
        }
    }
}
package com.example.testtotalplay.ui.login

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.testtotalplay.R
import com.example.testtotalplay.databinding.FragmentLoginBinding
import com.example.testtotalplay.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()
    private var pref: SharedPreferences? = null
    private lateinit var editor : SharedPreferences.Editor

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = requireActivity().getSharedPreferences("DATA_USER", AppCompatActivity.MODE_PRIVATE)
        editor = pref!!.edit()
        binding.tvCrearCuenta.text = Html.fromHtml("<font color=#000000>¿No tienes contraseña?</font> <font color=#3E77B6> Regístrate aquí</font>")
        binding.btnIniciarSesion.setOnClickListener {
            if(binding.etUser.text.isNullOrEmpty()){
                binding.tilUser.error = "¡Campo obligatorio!"
            }else if(binding.etPass.text.isNullOrEmpty()){
                binding.tilPassword.error = "¡Campo obligatorio!"
            }else{
                viewModel.login(binding.etUser.text.toString(), binding.etPass.text.toString())
            }
        }
        setObservers()
    }

    private fun setObservers(){
        viewModel.res.observe(viewLifecycleOwner){
            when(it.status){
                Resource.Status.SUCCESS -> {
                    if(it.data != null){
                        if(it.data.session.isNotEmpty()){
                            binding.btnIniciarSesion.visibility = View.VISIBLE
                            binding.tvInfoError.visibility = View.GONE
                            binding.progressbar.visibility = View.GONE
                            editor.putString("session", it.data.session)
                            editor.commit()
                            findNavController().navigate(R.id.action_loginFragment_to_referencesFragment)
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    binding.btnIniciarSesion.visibility = View.GONE
                    binding.progressbar.visibility = View.GONE
                    binding.tvInfoError.visibility = View.VISIBLE
                    binding.tvInfoError.text = "${it.message}"
                }
                Resource.Status.LOADING -> {
                    binding.btnIniciarSesion.visibility = View.GONE
                    binding.tvInfoError.visibility = View.GONE
                    binding.progressbar.visibility = View.VISIBLE
                }
            }
        }
    }
}
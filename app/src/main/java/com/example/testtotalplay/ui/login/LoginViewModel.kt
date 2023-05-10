package com.example.testtotalplay.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtotalplay.domain.model.BodyLogin
import com.example.testtotalplay.domain.model.LoginResponse
import com.example.testtotalplay.domain.repository.LoginRepository
import com.example.testtotalplay.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject
constructor(private val repository: LoginRepository) : ViewModel() {

    private val _res = MutableLiveData<Resource<LoginResponse>>()
    val res get() = _res

    fun login(user : String, password : String) = viewModelScope.launch {
        _res.postValue(Resource.loading(null))
        val bodyLogin = BodyLogin(user, password)
        try {
            repository.login(bodyLogin = bodyLogin).let { response ->
                if (response.isSuccessful){
                    if (response.body()!!.session.isNotEmpty()){
                        _res.postValue(Resource.success(response.body()!!))
                    }
                }else{
                    _res.postValue(Resource.error(response.message().toString(), null))
                }

            }
        }catch (e: HttpException) {
            _res.postValue(Resource.error("Oops, algo salió mal, intente nuevamente.", null))
        } catch (e: IOException) {
            _res.postValue(Resource.error("No podemos acceder al servidor, Couldn't reach server, check your internet connection.", null))
        }
    }

}
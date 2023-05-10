package com.example.testtotalplay.ui.references

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtotalplay.domain.model.BodyListado
import com.example.testtotalplay.domain.model.ListadoResponse
import com.example.testtotalplay.domain.model.LoginResponse
import com.example.testtotalplay.domain.repository.ListadoRepository
import com.example.testtotalplay.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ReferencesViewModel @Inject
constructor(private val repository: ListadoRepository)  : ViewModel() {

    private val _res = MutableLiveData<Resource<ListadoResponse>>()
    val res get() = _res

    fun getListado(session : String) = viewModelScope.launch {
        _res.postValue(Resource.loading(null))
        val bodyListado = BodyListado(session)
        try {
            repository.getListado(bodyListado).let { response ->
                if (response.isSuccessful){
                    if (response.body()!!.status == 0){
                        _res.postValue(Resource.success(response.body()!!))
                    }else{
                        _res.postValue(Resource.error("Cuenta y/o contraseña incorrectas, intenta nuevamente.", null))
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
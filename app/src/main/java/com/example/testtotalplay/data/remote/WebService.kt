package com.example.testtotalplay.data.remote

import com.example.testtotalplay.domain.model.BodyListado
import com.example.testtotalplay.domain.model.BodyLogin
import com.example.testtotalplay.domain.model.ListadoResponse
import com.example.testtotalplay.domain.model.LoginResponse
import retrofit2.http.POST
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface WebService {

    @GET("/cliente")
    suspend fun login(@Body bodyLogin: BodyLogin
    ) : Response<LoginResponse>

    @GET("/clienteresp")
    suspend fun listado(@Body bodyListado: BodyListado
    ) : Response<ListadoResponse>
}
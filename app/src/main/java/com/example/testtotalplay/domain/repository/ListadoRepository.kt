package com.example.testtotalplay.domain.repository

import com.example.testtotalplay.data.remote.WebService
import com.example.testtotalplay.domain.model.BodyListado
import javax.inject.Inject

class ListadoRepository @Inject
constructor(private val api : WebService){

    suspend fun getListado(bodyListado: BodyListado) = api.listado(bodyListado)
}
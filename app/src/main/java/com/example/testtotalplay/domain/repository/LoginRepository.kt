package com.example.testtotalplay.domain.repository

import com.example.testtotalplay.data.remote.WebService
import com.example.testtotalplay.domain.model.BodyLogin
import javax.inject.Inject

class LoginRepository @Inject
constructor(private val api : WebService) {

    suspend fun login(bodyLogin: BodyLogin) = api.login(bodyLogin)

}
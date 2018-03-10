package com.example.logonpf.carros.api


import com.example.logonpf.carros.model.Carro
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Andre on 10/03/2018.
 */
interface CarroAPI{
    @GET("/carro")
    fun buscarTodos() : Call<List<Carro>>
}
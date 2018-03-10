package com.example.logonpf.carros.model

import android.media.Image

/**
 * Created by Andre on 10/03/2018.
 */
data class  Carro(var id: String?,
                  var marca: String,
                  var modelo: String,
                  var ano: Int,
                  var placa: String,
                  var urlImagem: String?)
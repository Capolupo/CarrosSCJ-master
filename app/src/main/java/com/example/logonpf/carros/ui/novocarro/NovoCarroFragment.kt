package com.example.logonpf.carros.ui.novocarro


import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.text.InputFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.example.logonpf.carros.R
import com.example.logonpf.carros.api.CarroAPI
import com.example.logonpf.carros.api.RetrofitClient
import com.example.logonpf.carros.model.Carro
import com.example.logonpf.carros.model.Global
import kotlinx.android.synthetic.main.fragment_novo_carro.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 */
class NovoCarroFragment : Fragment() {

    val btSalvar by lazy { view!!.findViewById<Button>(R.id.btSalvar) }
    val inputMarca by lazy { view!!.findViewById<TextInputLayout>(R.id.inputMarca) }
    val inputAno by lazy { view!!.findViewById<TextInputLayout>(R.id.inputMarca) }
    val inputModelo by lazy { view!!.findViewById<TextInputLayout>(R.id.inputMarca) }
    val inputPlaca by lazy { view!!.findViewById<TextInputLayout>(R.id.inputMarca) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_novo_carro, container, false)    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btSalvar.setOnClickListener {
            if (!camposVazil()) {
                val api = RetrofitClient
                        .getInstance()
                        .create(CarroAPI::class.java)
                val carro = Carro(null,
                        inputMarca.editText?.text.toString(),
                        inputModelo.editText?.text.toString(),
                        inputAno.editText?.text.toString().toInt(),
                        inputPlaca.editText?.text.toString(),
                        "", 0)
                api.salvar(carro)
                        .enqueue(object : Callback<Void> {
                            override fun onFailure(call: Call<Void>?, t: Throwable?) {
                                Log.e("CARRO", t?.message)
                            }

                            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                                Toast.makeText(context, "Errou coloque aqui o Faustao", Toast.LENGTH_SHORT).show()
                            }
                        })
            }
            else{
                Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun camposVazil():Boolean{
        if(inputMarca.editText?.text.isNullOrEmpty()||
                inputModelo.editText?.text.isNullOrEmpty()||
                inputAno.editText?.text.isNullOrEmpty()||
                inputPlaca.editText?.text.isNullOrEmpty())
            return true
        else
            return false
    }


    private fun limparCampos(){
        inputMarca.editText?.setText("")
        inputModelo.editText?.setText("")
        inputAno.editText?.setText("")
        inputPlaca.editText?.setText("")
    }
}// Required empty public constructor

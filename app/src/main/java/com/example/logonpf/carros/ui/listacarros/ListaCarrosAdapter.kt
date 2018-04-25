package com.example.logonpf.carros.ui.listacarros

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.logonpf.carros.R
import com.example.logonpf.carros.model.Carro
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.item_carro.view.*

/**
 * Created by Andre on 10/03/2018.
 */
class ListaCarrosAdapter(private val carros:List<Carro>,private val context:Context)
    :RecyclerView.Adapter<ListaCarrosAdapter.MeuViewHolder>(){

    override fun onBindViewHolder(holder: MeuViewHolder, position: Int) {
        val carro = carros[position]
        holder?.let { it.bindView(carro) }
    }

    override fun getItemCount(): Int {
        return carros.size;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeuViewHolder {
        val view =  LayoutInflater.from(context).inflate(R.layout.item_carro, parent, false)
        return  MeuViewHolder(view)
    }



    class MeuViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
       fun bindView(carro:Carro) {
           itemView.findViewById<TextView>(R.id.tvMarca).text = carro.marca
           itemView.findViewById<TextView>(R.id.tvModelo).text = carro.modelo
           if(carro.urlImagem.isNullOrEmpty()){
               itemView.findViewById<ImageView>(R.id.ivFoto).setImageDrawable(
                       ContextCompat.getDrawable(itemView.context,R.drawable.erroufaustao)
               )
           }
           else{
               Picasso.get()
                       .load(carro.urlImagem)
                       .placeholder(R.drawable.refresh)
                       .error(R.drawable.cancel)
                       .into(itemView.findViewById<ImageView>(R.id.ivFoto));
           }

       }
    }
}
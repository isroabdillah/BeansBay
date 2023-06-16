package com.example.beansbay.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beansbay.R
import com.example.beansbay.network.CartItems

class CheckoutResultAdapter(
    private val listProductCart : List<CartItems>
    )
    :RecyclerView.Adapter<CheckoutResultAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivProductPhoto : ImageView = view.findViewById(R.id.IVgambarproduk)
        val tvProductName : TextView = view.findViewById(R.id.TVnameproduk)
        val tvQuantity : TextView = view.findViewById(R.id.TVqty)
        val tvHargaTotal : TextView = view.findViewById(R.id.TVhargatotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_checkout_result, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.ivProductPhoto)
            .load(listProductCart[position].url)
            .into(holder.ivProductPhoto)
        holder.tvProductName.text = listProductCart[position].namaProduk


        holder.tvQuantity.text = listProductCart[position].quantity.toString()
        holder.tvHargaTotal.text = "Rp" + listProductCart[position].totalHarga.toString()
    }

    override fun getItemCount(): Int = listProductCart.size

}
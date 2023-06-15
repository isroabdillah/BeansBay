package com.example.beansbay.adapter


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beansbay.network.Product
import com.example.beansbay.R
import com.example.beansbay.network.Results
import com.example.beansbay.ui.detail.DetailProduct

class SpotlightProductAdapter(
    private val listProduct : List<Product>,
    private val listProductId : List<Results>
    ):RecyclerView.Adapter<SpotlightProductAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvProductName :TextView = view.findViewById(R.id.nama_produk_spotlight)
        val tvProductPrice : TextView = view.findViewById(R.id.harga_produk_spotlight)
        val tvProductStore : TextView = view.findViewById(R.id.nama_toko_spotlight)
        val ivProductPhoto : ImageView = view.findViewById(R.id.foto_produk_spotlight)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product_spotlight, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvProductName.text = listProduct[position].namaProduk
        holder.tvProductPrice.text = "Rp" + listProduct[position].hargaRupiah.toString()
        holder.tvProductStore.text = listProduct[position].toko

        Glide.with(holder.itemView.context)
            .load(listProduct[position].url)
            .into(holder.ivProductPhoto)

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailProduct::class.java )
            intent.putExtra("product_id", listProductId[position].predictedIdProduk)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listProduct.size
}
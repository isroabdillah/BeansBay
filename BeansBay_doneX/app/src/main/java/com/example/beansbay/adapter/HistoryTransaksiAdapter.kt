package com.example.beansbay.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beansbay.R
import com.example.beansbay.network.TransaksiResponse

class HistoryTransaksiAdapter(private val listTransaksi : List<TransaksiResponse>    )
    :RecyclerView.Adapter<HistoryTransaksiAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTanggalTransaksi : TextView = view.findViewById(R.id.tanggal_transaksi)
        val tvInvoiceTransaksi : TextView = view.findViewById(R.id.invoice_transaksi)
        val tvTotalHargaTransaksi : TextView = view.findViewById(R.id.totalHargaTransaksi)
        val tvTotalItem : TextView = view.findViewById(R.id.jumlah_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_transaksi_history, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTanggalTransaksi.text = listTransaksi[position].tanggalWaktuCheckout
        holder.tvInvoiceTransaksi.text = listTransaksi[position].invoice
        holder.tvTotalHargaTransaksi.text = "Rp"+listTransaksi[position].hargaKeranjang.toString()
        holder.tvTotalItem.text = listTransaksi[position].cartItems.size.toString() + " Barang"
    }

    override fun getItemCount(): Int = listTransaksi.size

}
package com.example.macpop_uas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RincianProdukAdapter(private val products: List<DetailPemesanan>) :
    RecyclerView.Adapter<RincianProdukAdapter.RincianProdukViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RincianProdukViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rincian_produk, parent, false)
        return RincianProdukViewHolder(view)
    }

    override fun onBindViewHolder(holder: RincianProdukViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    inner class RincianProdukViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNamaProduk: TextView = itemView.findViewById(R.id.tv_nama_menu)
        private val tvHarga: TextView = itemView.findViewById(R.id.tv_harga)

        fun bind(product: DetailPemesanan) {
            // Implement logic to get product name and price based on product ID
            val namaProduk = getProductName(product.idMenu)
            val hargaProduk = getProductPrice(product.idMenu)

            tvNamaProduk.text = namaProduk
            tvHarga.text = "Harga: Rp $hargaProduk"
        }
    }

    // Implement methods to get product name and price based on product ID
    private fun getProductName(idMenu: Int): String {
        // Dummy implementation, replace with actual logic
        return "Nama Produk $idMenu"
    }

    private fun getProductPrice(idMenu: Int): Int {
        // Dummy implementation, replace with actual logic
        return 15000 // Contoh harga produk
    }
}

package com.example.macpop_uas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PembayaranAdapter(private val productList: List<DetailPemesanan>) :
    RecyclerView.Adapter<PembayaranAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rincian_produk, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNamaMenu: TextView = itemView.findViewById(R.id.tv_nama_menu)
        private val tvHarga: TextView = itemView.findViewById(R.id.tv_harga)

        fun bind(product: DetailPemesanan) {
            // Set nama produk
            val namaProduk = getProductName(product.idMenu)
            tvNamaMenu.text = namaProduk

            // Set kuantitas dan harga
            val hargaTotal = product.kuantitas * getProductPrice(product.idMenu)
            tvHarga.text = "Total: Rp ${formatCurrency(hargaTotal)}"
        }

        private fun getProductName(menuId: Int): String {
            // Implementasi ini bergantung pada bagaimana Anda mengakses nama produk dari database atau sumber lainnya
            // Contoh sederhana:
            return when (menuId) {
                1 -> "MacPop Coklat"
                2 -> "MacPop Original"
                3 -> "MacPop Vanilla"
                4 -> "MacPop Balado"
                5 -> "MacPop BBQ"
                // Tambahkan case lain sesuai dengan id menu yang dimiliki
                else -> "Nama Produk Tidak Dikenal"
            }
        }

        private fun getProductPrice(menuId: Int): Int {
            // Implementasi ini bergantung pada bagaimana Anda mengakses harga produk dari database atau sumber lainnya
            // Contoh sederhana:
            return when (menuId) {
                1 -> 15000
                2 -> 15000
                3 -> 15000
                4 -> 15000
                5 -> 15000
                // Tambahkan case lain sesuai dengan id menu yang dimiliki
                else -> 0
            }
        }

        private fun formatCurrency(amount: Int): String {
            // Method ini digunakan untuk mengubah nilai harga menjadi format mata uang yang sesuai
            // Anda bisa menyesuaikan formatnya sesuai kebutuhan (misalnya menggunakan locale tertentu)
            return "Rp " + amount.toString()
        }
    }
}

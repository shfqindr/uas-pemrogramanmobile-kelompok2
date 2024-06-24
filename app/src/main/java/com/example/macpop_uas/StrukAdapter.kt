package com.example.macpop_uas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StrukAdapter(private val products: List<DetailPemesanan>) : RecyclerView.Adapter<StrukAdapter.StrukViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StrukViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_struk, parent, false)
        return StrukViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StrukViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    class StrukViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvProductName: TextView = itemView.findViewById(R.id.tv_product_name)
        private val tvProductQuantity: TextView = itemView.findViewById(R.id.tv_product_quantity)
        private val tvProductPrice: TextView = itemView.findViewById(R.id.tv_product_price)

        fun bind(product: DetailPemesanan) {
            // Anda harus mengganti getProductName dan getProductPrice dengan logika Anda sendiri untuk mendapatkan data produk yang sesuai
            tvProductName.text = getProductName(product.idMenu)
            tvProductQuantity.text = "${product.kuantitas} unit"
            tvProductPrice.text = "Rp ${product.kuantitas * getProductPrice(product.idMenu)}"
        }

        private fun getProductName(menuId: Int): String {
            return when (menuId) {
                1 -> "MacPop Coklat"
                2 -> "MacPop Original"
                3 -> "MacPop Vanilla"
                4 -> "MacPop Balado"
                5 -> "MacPop BBQ"
                else -> "Nama Produk Tidak Dikenal"
            }
        }

        private fun getProductPrice(menuId: Int): Int {
            return when (menuId) {
                1 -> 15000
                2 -> 15000
                3 -> 15000
                4 -> 15000
                5 -> 15000
                else -> 0
            }
        }
    }
}


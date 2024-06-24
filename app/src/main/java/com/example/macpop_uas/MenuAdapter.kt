package com.example.macpop_uas

import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(
    private val menuList: List<Menu>,
    private val dbHelper: DatabaseHelper,
    private val onQuantityChanged: (idMenu: Int, quantity: Int) -> Unit,
    private val onProductSelected: (menuId: Int, selected: Boolean) -> Unit,
    private val selectedProducts: MutableList<DetailPemesanan>
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNamaMenu: TextView = itemView.findViewById(R.id.tv_nama_menu)
        private val tvDeskripsiMenu: TextView = itemView.findViewById(R.id.tv_deskripsi_menu)
        private val tvHargaMenu: TextView = itemView.findViewById(R.id.tv_harga_menu)
        private val tvStokMenu: TextView = itemView.findViewById(R.id.tv_stok_menu)
        private val ivPhotoMenu: ImageView = itemView.findViewById(R.id.image_menu)
        private val checkboxSelected: CheckBox = itemView.findViewById(R.id.checkboxSelected)

        fun bind(menu: Menu) {
            tvNamaMenu.text = menu.namaMenu
            tvDeskripsiMenu.text = menu.deskripsi
            tvHargaMenu.text = "Rp ${menu.harga}"
            tvStokMenu.text = "Stok: ${menu.stok}"
            val bitmap = BitmapFactory.decodeByteArray(menu.photo, 0, menu.photo.size)
            ivPhotoMenu.setImageBitmap(bitmap)

            // Set initial state of checkbox
            checkboxSelected.isChecked = selectedProducts.any { it.idMenu == menu.id }

            checkboxSelected.setOnCheckedChangeListener { _, isChecked ->
                onProductSelected.invoke(menu.id, isChecked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_produk, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menu = menuList[position]
        holder.bind(menu)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }
}

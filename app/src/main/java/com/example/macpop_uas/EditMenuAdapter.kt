package com.example.macpop_uas

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EditMenuAdapter(
    private val menuList: List<Menu>,
    private val editClickListener: (Menu) -> Unit,
    private val hapusClickListener: (Menu) -> Unit
) : RecyclerView.Adapter<EditMenuAdapter.EditMenuViewHolder>() {

    inner class EditMenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNamaMenu: TextView = itemView.findViewById(R.id.tv_nama_menu)
        val tvDeskripsiMenu: TextView = itemView.findViewById(R.id.tv_deskripsi_menu)
        val tvHargaMenu: TextView = itemView.findViewById(R.id.tv_harga_menu)
        val tvStokMenu: TextView = itemView.findViewById(R.id.tv_stok_menu)
        val ivPhotoMenu: ImageView = itemView.findViewById(R.id.image_menu)
        val btnEdit: Button? = itemView.findViewById(R.id.btn_edit)
        val btnHapus: Button? = itemView.findViewById(R.id.btn_hapus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditMenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return EditMenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: EditMenuViewHolder, position: Int) {
        val menu = menuList[position]
        holder.tvNamaMenu.text = menu.namaMenu
        holder.tvDeskripsiMenu.text = menu.deskripsi
        holder.tvHargaMenu.text = menu.harga.toString()
        holder.tvStokMenu.text = menu.stok.toString()
        val bitmap = BitmapFactory.decodeByteArray(menu.photo, 0, menu.photo.size)
        holder.ivPhotoMenu.setImageBitmap(bitmap)

        holder.btnEdit?.setOnClickListener { editClickListener(menu) }
        holder.btnHapus?.setOnClickListener { hapusClickListener(menu) }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }
}

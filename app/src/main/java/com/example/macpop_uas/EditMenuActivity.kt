package com.example.macpop_uas

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.io.ByteArrayOutputStream
import android.view.LayoutInflater

class EditMenuActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var editMenuAdapter: EditMenuAdapter
    private lateinit var menuList: ArrayList<Menu>
    private lateinit var dbHelper: DatabaseHelper

    private val PICK_IMAGE_REQUEST = 1
    private var selectedImageUri: Uri? = null
    private lateinit var btn_back: Button

    // Properti kelas untuk menyimpan referensi ImageView
    private lateinit var ivSelectedPhoto: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_menu)
        dbHelper = DatabaseHelper(this)

        // Inisialisasi RecyclerView, adapter, dan layout manager
        recyclerView = findViewById(R.id.rv_menu)
        recyclerView.layoutManager = LinearLayoutManager(this)
        menuList = ArrayList()

        // Buat adapter untuk EditMenuActivity
        editMenuAdapter = EditMenuAdapter(menuList,
            { menu -> onItemClick(menu, "Edit") },
            { menu -> onItemClick(menu, "Hapus") }
        )

        // Set adapter ke RecyclerView
        recyclerView.adapter = editMenuAdapter

        // Load data menu
        loadMenuData()
        val btnBack: ImageButton = findViewById(R.id.btn_back)
        btnBack.setOnClickListener {
            onBackPressed()
        }
        val btnTambahProduk: Button = findViewById(R.id.btn_tambah_produk)
        btnTambahProduk.setOnClickListener {
            showTambahMenuDialog()
        }
    }
    override fun onBackPressed() {
        // Handle your back button behavior here, for example:
        super.onBackPressed()
    }
    private fun loadMenuData() {
        menuList.clear()
        val db = dbHelper.readableDatabase
        val cursor = db.query("menu", null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val namaMenu = cursor.getString(cursor.getColumnIndexOrThrow("nama_menu"))
                val photo = cursor.getBlob(cursor.getColumnIndexOrThrow("photo")) // Ambil sebagai byte array
                val deskripsi = cursor.getString(cursor.getColumnIndexOrThrow("deskripsi"))
                val harga = cursor.getDouble(cursor.getColumnIndexOrThrow("harga"))
                val stok = cursor.getInt(cursor.getColumnIndexOrThrow("stok"))

                val menu = Menu(id, namaMenu, photo, deskripsi, harga, stok)
                menuList.add(menu)
            } while (cursor.moveToNext())
        } else {
            Toast.makeText(this, "Data menu kosong", Toast.LENGTH_SHORT).show()
        }

        cursor.close()
        db.close()
    }

    private fun showTambahMenuDialog(menu: Menu? = null) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_tambah_menu, null)
        val dialogBuilder = AlertDialog.Builder(this).setView(dialogView).setCancelable(false)
        val dialog = dialogBuilder.create()

        val etNamaMenu: EditText = dialogView.findViewById(R.id.et_nama_menu)
        val etDeskripsiMenu: EditText = dialogView.findViewById(R.id.et_deskripsi_menu)
        val etHarga: EditText = dialogView.findViewById(R.id.et_harga)
        val etStok: EditText = dialogView.findViewById(R.id.et_stok)
        val btnSelectPhoto: Button = dialogView.findViewById(R.id.btn_select_photo)

        // Inisialisasi ivSelectedPhoto di sini
        ivSelectedPhoto = dialogView.findViewById(R.id.iv_selected_photo)

        val btnCancel: Button = dialogView.findViewById(R.id.btn_cancel)
        val btnSimpan: Button = dialogView.findViewById(R.id.btn_simpan)

        if (menu != null) {
            // Jika sedang mengedit, set nilai field sesuai data yang akan diubah
            etNamaMenu.setText(menu.namaMenu)
            etDeskripsiMenu.setText(menu.deskripsi)
            etHarga.setText(menu.harga.toString())
            etStok.setText(menu.stok.toString())
            val bitmap = BitmapFactory.decodeByteArray(menu.photo, 0, menu.photo.size)
            ivSelectedPhoto.setImageBitmap(bitmap)
            ivSelectedPhoto.visibility = View.VISIBLE
        }

        btnSelectPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnSimpan.setOnClickListener {
            val namaMenu = etNamaMenu.text.toString()
            val deskripsi = etDeskripsiMenu.text.toString()
            val harga = etHarga.text.toString().toDoubleOrNull()
            val stok = etStok.text.toString().toIntOrNull()

            if (namaMenu.isNotEmpty() && deskripsi.isNotEmpty() && harga != null && stok != null && selectedImageUri != null) {
                val bitmap = (ivSelectedPhoto.drawable as BitmapDrawable).bitmap
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val photo = stream.toByteArray()

                val db = dbHelper.writableDatabase
                val values = ContentValues().apply {
                    put("nama_menu", namaMenu)
                    put("deskripsi", deskripsi)
                    put("photo", photo)
                    put("harga", harga)
                    put("stok", stok)
                }

                if (menu != null) {
                    // Jika sedang mengedit, update data yang ada
                    db.update("menu", values, "id=?", arrayOf(menu.id.toString()))
                } else {
                    // Jika sedang menambah, tambahkan data baru
                    db.insert("menu", null, values)
                }
                db.close()

                // Tambahkan data baru ke menuList dan perbarui adapter
                loadMenuData()
                editMenuAdapter.notifyDataSetChanged()

                Toast.makeText(this, if (menu != null) "Produk berhasil diubah" else "Produk baru berhasil disimpan", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Harap isi semua field", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data
            Glide.with(this)
                .load(selectedImageUri)
                .into(ivSelectedPhoto)
        }
    }

    private fun onItemClick(menu: Menu, action: String) {
        when (action) {
            "Edit" -> showTambahMenuDialog(menu)
            "Hapus" -> {
                val db = dbHelper.writableDatabase
                db.delete("menu", "id=?", arrayOf(menu.id.toString()))
                db.close()

                // Hapus data dari menuList dan perbarui adapter
                menuList.remove(menu)
                editMenuAdapter.notifyDataSetChanged()

                Toast.makeText(this, "Produk berhasil dihapus", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

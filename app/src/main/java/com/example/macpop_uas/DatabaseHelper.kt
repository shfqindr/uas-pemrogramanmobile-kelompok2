package com.example.macpop_uas

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "kasir.db"
        private const val DATABASE_VERSION = 2

        // Tabel Menu
        private const val TABLE_MENU = "menu"
        private const val KEY_ID = "id"
        private const val KEY_NAMA_MENU = "nama_menu"
        private const val KEY_PHOTO = "photo"
        private const val KEY_DESKRIPSI = "deskripsi"
        private const val KEY_HARGA = "harga"
        private const val KEY_STOK = "stok"

        // Tabel Pemesanan
        private const val TABLE_PEMESANAN = "pemesanan"
        private const val KEY_ID_PEMESANAN = "id_pemesanan"
        private const val KEY_TOTAL_HARGA = "total_harga"
        private const val KEY_TANGGAL = "tanggal"
        private const val KEY_METODE_PEMBAYARAN = "metode_pembayaran"

        // Tabel Detail Pemesanan
        private const val TABLE_DETAIL_PEMESANAN = "detail_pemesanan"
        private const val KEY_ID_DETAIL = "id_detail"
        private const val KEY_ID_PEMESANAN_DETAIL = "id_pemesanan_detail"
        private const val KEY_ID_MENU_DETAIL = "id_menu"
        private const val KEY_KUANTITAS = "kuantitas"+
                "FOREIGN KEY($KEY_ID_PEMESANAN_DETAIL) REFERENCES $TABLE_PEMESANAN($KEY_ID_PEMESANAN), " +
                "FOREIGN KEY($KEY_ID_MENU_DETAIL) REFERENCES $TABLE_MENU($KEY_ID))"

        // Query untuk membuat tabel Menu
        private const val CREATE_TABLE_MENU = "CREATE TABLE $TABLE_MENU (" +
                "$KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$KEY_NAMA_MENU TEXT, " +
                "$KEY_PHOTO BLOB, " +
                "$KEY_DESKRIPSI TEXT, " +
                "$KEY_HARGA REAL, " +
                "$KEY_STOK INTEGER)"

        // Query untuk membuat tabel Pemesanan
        private const val CREATE_TABLE_PEMESANAN = "CREATE TABLE $TABLE_PEMESANAN (" +
                "$KEY_ID_PEMESANAN INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$KEY_TOTAL_HARGA REAL, " +
                "$KEY_TANGGAL TEXT DEFAULT (datetime('now','localtime')), " +
                "$KEY_METODE_PEMBAYARAN TEXT)"

        // Query untuk membuat tabel Detail Pemesanan
        private const val CREATE_TABLE_DETAIL_PEMESANAN = "CREATE TABLE $TABLE_DETAIL_PEMESANAN (" +
                "$KEY_ID_DETAIL INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$KEY_ID_PEMESANAN_DETAIL INTEGER, " +
                "$KEY_ID_MENU_DETAIL INTEGER, " +
                "$KEY_KUANTITAS INTEGER, " +
                "FOREIGN KEY($KEY_ID_PEMESANAN_DETAIL) REFERENCES $TABLE_PEMESANAN($KEY_ID_PEMESANAN), " +
                "FOREIGN KEY($KEY_ID_MENU_DETAIL) REFERENCES $TABLE_MENU($KEY_ID))"

    }

    override fun onCreate(db: SQLiteDatabase) {
        // Membuat tabel-tabel yang diperlukan
        db.execSQL(CREATE_TABLE_MENU)
        db.execSQL(CREATE_TABLE_PEMESANAN)
        db.execSQL(CREATE_TABLE_DETAIL_PEMESANAN)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop tabel lama jika ada dan membuat yang baru
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MENU")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PEMESANAN")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DETAIL_PEMESANAN")
        onCreate(db)
    }

    // Operasi CRUD untuk Tabel Menu

    fun insertMenu(menu: Menu): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(KEY_NAMA_MENU, menu.namaMenu)
            put(KEY_PHOTO, menu.photo)  // Memasukkan foto sebagai ByteArray
            put(KEY_DESKRIPSI, menu.deskripsi)
            put(KEY_HARGA, menu.harga)
            put(KEY_STOK, menu.stok)
        }

        val id = db.insert(TABLE_MENU, null, values)
        db.close()
        return id
    }

    @SuppressLint("Range")
    fun getAllMenu(): MutableList<Menu> {
        val menuList = mutableListOf<Menu>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_MENU"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val namaMenu = cursor.getString(cursor.getColumnIndex(KEY_NAMA_MENU))
                val photoByteArray =
                    cursor.getBlob(cursor.getColumnIndex(KEY_PHOTO)) // Ambil foto sebagai ByteArray
                val deskripsi = cursor.getString(cursor.getColumnIndex(KEY_DESKRIPSI))
                val harga = cursor.getDouble(cursor.getColumnIndex(KEY_HARGA))
                val stok = cursor.getInt(cursor.getColumnIndex(KEY_STOK))

                val menu = Menu(id, namaMenu, photoByteArray, deskripsi, harga, stok)
                menuList.add(menu)
            } while (cursor.moveToNext())}

            cursor.close()
            db.close()
            return menuList
        }

        fun updateMenu(menu: Menu): Int {
            val db = writableDatabase
            val values = ContentValues().apply {
                put(KEY_NAMA_MENU, menu.namaMenu)
                put(KEY_PHOTO, menu.photo)
                put(KEY_DESKRIPSI, menu.deskripsi)
                put(KEY_HARGA, menu.harga)
                put(KEY_STOK, menu.stok)
            }

            val rows = db.update(TABLE_MENU, values, "$KEY_ID=?", arrayOf(menu.id.toString()))
            db.close()
            return rows
        }

        fun deleteMenu(menuId: Int): Int {
            val db = writableDatabase
            val rows = db.delete(TABLE_MENU, "$KEY_ID=?", arrayOf(menuId.toString()))
            db.close()
            return rows
        }

        @SuppressLint("Range")
        fun getMenuById(menuId: Int): Menu? {
            val db = readableDatabase
            var menu: Menu? = null
            val query = "SELECT * FROM $TABLE_MENU WHERE $KEY_ID = ?"
            val cursor = db.rawQuery(query, arrayOf(menuId.toString()))

            if (cursor.moveToFirst()) {
                val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val namaMenu = cursor.getString(cursor.getColumnIndex(KEY_NAMA_MENU))
                val photoByteArray = cursor.getBlob(cursor.getColumnIndex(KEY_PHOTO))
                val deskripsi = cursor.getString(cursor.getColumnIndex(KEY_DESKRIPSI))
                val harga = cursor.getDouble(cursor.getColumnIndex(KEY_HARGA))
                val stok = cursor.getInt(cursor.getColumnIndex(KEY_STOK))

                menu = Menu(id, namaMenu, photoByteArray, deskripsi, harga, stok)
            }

            cursor.close()
            db.close()
            Log.d("DatabaseHelper", "Menu: ${menu?.namaMenu}")
            return menu
        }

        // Operasi untuk Tabel Pemesanan

        fun insertPemesanan(pemesanan: Pemesanan): Long {
            val db = writableDatabase
            val values = ContentValues().apply {
                put(KEY_TOTAL_HARGA, pemesanan.totalHarga)
                put(KEY_METODE_PEMBAYARAN, pemesanan.metodePembayaran)
            }

            val id = db.insert(TABLE_PEMESANAN, null, values)
            db.close()
            return id
        }

        @SuppressLint("SimpleDateFormat", "Range")
        fun getAllPemesanan(): List<Pemesanan> {
            val pemesananList = mutableListOf<Pemesanan>()
            val db = readableDatabase
            val query = "SELECT * FROM $TABLE_PEMESANAN"
            val cursor = db.rawQuery(query, null)

            while (cursor.moveToNext()) {
                val idPemesanan = cursor.getInt(cursor.getColumnIndex(KEY_ID_PEMESANAN))
                val totalHarga = cursor.getDouble(cursor.getColumnIndex(KEY_TOTAL_HARGA))
                val tanggalString = cursor.getString(cursor.getColumnIndex(KEY_TANGGAL))
                val metodePembayaran =
                    cursor.getString(cursor.getColumnIndex(KEY_METODE_PEMBAYARAN))

                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val tanggal = sdf.parse(tanggalString)

                val pemesanan = Pemesanan(idPemesanan, totalHarga, tanggal, metodePembayaran)
                pemesananList.add(pemesanan)
            }

            cursor.close()
            db.close()
            return pemesananList
        }

        // Operasi untuk Tabel Detail Pemesanan

        fun insertDetailPemesanan(detailPemesanan: DetailPemesanan): Long {
            val db = writableDatabase
            val values = ContentValues().apply {
                put(KEY_ID_PEMESANAN_DETAIL, detailPemesanan.idPemesanan)
                put(KEY_ID_MENU_DETAIL, detailPemesanan.idMenu)
                put(KEY_KUANTITAS, detailPemesanan.kuantitas) // Masukkan kuantitas
            }

            val id = db.insert(TABLE_DETAIL_PEMESANAN, null, values)
            db.close()
            return id
        }

    fun updateDetailPemesanan(detailPemesanan: DetailPemesanan): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(KEY_KUANTITAS, detailPemesanan.kuantitas)
        }

        val rows = db.update(
            TABLE_DETAIL_PEMESANAN,
            values,
            "$KEY_ID_DETAIL=?",
            arrayOf(detailPemesanan.id.toString())
        )
        db.close()
        return rows
    }

    fun insertOrUpdateDetailPemesanan(detailPemesanan: DetailPemesanan): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(KEY_ID_PEMESANAN_DETAIL, detailPemesanan.idPemesanan)
            put(KEY_ID_MENU_DETAIL, detailPemesanan.idMenu)
            put(KEY_KUANTITAS, detailPemesanan.kuantitas)
        }

        val id = db.insertWithOnConflict(
            TABLE_DETAIL_PEMESANAN,
            null,
            values,
            SQLiteDatabase.CONFLICT_REPLACE
        )
        db.close()
        return id
    }

    @SuppressLint("Range")
        fun getAllDetailPemesanan(): List<DetailPemesanan> {
            val detailPemesananList = mutableListOf<DetailPemesanan>()
            val db = readableDatabase
            val query = "SELECT * FROM $TABLE_DETAIL_PEMESANAN"
            val cursor = db.rawQuery(query, null)

            if (cursor.moveToFirst()) {
                do {
                    val idDetail = cursor.getInt(cursor.getColumnIndex(KEY_ID_DETAIL))
                    val idPemesanan = cursor.getInt(cursor.getColumnIndex(KEY_ID_PEMESANAN_DETAIL))
                    val idMenu = cursor.getInt(cursor.getColumnIndex(KEY_ID_MENU_DETAIL))
                    val kuantitas =
                        cursor.getInt(cursor.getColumnIndex(KEY_KUANTITAS)) // Ambil kuantitas

                    val detailPemesanan = DetailPemesanan(idDetail, idPemesanan, idMenu, kuantitas)
                    detailPemesananList.add(detailPemesanan)
                } while (cursor.moveToNext())
            }

            cursor.close()
            db.close()
            Log.d("DatabaseHelper", "DetailPemesananList: ${detailPemesananList.size}")
            return detailPemesananList
        }

    fun updateMenuQuantity(menuId: Int, updatedMenu: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(KEY_STOK, updatedMenu)
        }

        db.update(TABLE_MENU, values, "$KEY_ID=?", arrayOf(menuId.toString()))
        db.close()
    }

}

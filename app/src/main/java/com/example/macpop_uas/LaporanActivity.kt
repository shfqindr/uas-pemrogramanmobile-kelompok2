package com.example.macpop_uas

import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LaporanActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var tvTitle: TextView
    private lateinit var btnHarian: Button
    private lateinit var btnMingguan: Button
    private lateinit var btnBulanan: Button
    private lateinit var containerLayout: FrameLayout
    private lateinit var tvPlaceholder: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan)

        btnBack = findViewById(R.id.btn_back)
        tvTitle = findViewById(R.id.tv_title)
        btnHarian = findViewById(R.id.btn_harian)
        btnMingguan = findViewById(R.id.btn_mingguan)
        btnBulanan = findViewById(R.id.btn_bulanan)
        containerLayout = findViewById(R.id.container_layout)
        tvPlaceholder = findViewById(R.id.tv_placeholder)

        btnBack.setOnClickListener {
            onBackPressed()
        }

        btnHarian.setOnClickListener {
            showHarianLaporan()
        }

        btnMingguan.setOnClickListener {
            showMingguanLaporan()
        }

        btnBulanan.setOnClickListener {
            showBulananLaporan()
        }

        // Menampilkan pesan placeholder awal
        tvPlaceholder.text = "Pilih jenis laporan di atas"
    }

    private fun showHarianLaporan() {
        // Implementasi untuk menampilkan laporan harian
        // Misalnya, tampilkan tabel/grafik pemesanan hari ini

        // Contoh menampilkan pesan jika tidak ada data
        tvPlaceholder.text = "Belum ada data penjualan harian"
    }

    private fun showMingguanLaporan() {
        // Implementasi untuk menampilkan laporan mingguan
        // Tampilkan tabel/grafik pemesanan mingguan

        // Contoh menampilkan pesan jika tidak ada data
        tvPlaceholder.text = "Belum ada data penjualan mingguan"
    }

    private fun showBulananLaporan() {
        // Implementasi untuk menampilkan laporan bulanan
        // Tampilkan tabel/grafik pemesanan bulanan

        // Contoh menampilkan pesan jika tidak ada data
        tvPlaceholder.text = "Belum ada data penjualan bulanan"
    }
}

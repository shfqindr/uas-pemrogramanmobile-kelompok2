package com.example.macpop_uas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class PembayaranActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var pembayaranAdapter: PembayaranAdapter
    private lateinit var selectedProducts: List<DetailPemesanan>
    private lateinit var etKuantitas: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var radioCash: RadioButton
    private lateinit var radioQris: RadioButton
    private var totalKuantitas: Int = 0
    private var totalHarga: Int = 0
    private var idPemesanan: String = "" // Nilai default untuk idPemesanan
    private lateinit var metodePembayaran: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembayaran)

        // Mendapatkan data produk yang dipilih dari intent
        selectedProducts = intent.getParcelableArrayListExtra("selectedProducts") ?: emptyList()

        // Mendapatkan idPemesanan dari intent atau inisialisasi dengan nilai default
        idPemesanan = intent.getStringExtra("idPemesanan") ?: ""

        // Set up RecyclerView untuk menampilkan rincian pembayaran produk
        recyclerView = findViewById(R.id.rv_pembayaran)
        recyclerView.layoutManager = LinearLayoutManager(this)
        pembayaranAdapter = PembayaranAdapter(selectedProducts)
        recyclerView.adapter = pembayaranAdapter

        // Inisialisasi elemen UI
        etKuantitas = findViewById(R.id.et_kuantitas)
        radioGroup = findViewById(R.id.radio_group_metode)
        radioCash = findViewById(R.id.radio_cash)
        radioQris = findViewById(R.id.radio_qris)

        // Set tanggal terkini
        val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        findViewById<EditText>(R.id.et_tanggal).setText(currentDate)

        // Menghitung total kuantitas dan total harga dari produk yang dipilih
        calculateTotal()

        // Mengatur button untuk cetak struk
        val btnCetakStruk: Button = findViewById(R.id.btn_cetak_struk)
        btnCetakStruk.setOnClickListener {
            // Mengambil metode pembayaran yang dipilih
            metodePembayaran = if (radioCash.isChecked) {
                "Cash"
            } else {
                "QRIS"
            }

            // Mengarahkan ke halaman StrukActivity
            navigateToStruk()
        }
        val btnBack: Button = findViewById(R.id.btn_kembali)
        btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun calculateTotal() {
        totalKuantitas = 0
        totalHarga = 0
        for (produk in selectedProducts) {
            totalKuantitas += produk.kuantitas
            totalHarga += produk.kuantitas * getProductPrice(produk.idMenu) // Diganti dengan logika sesuai aplikasi Anda
        }
    }

    private fun generateRincianProdukText(): String {
        val stringBuilder = StringBuilder()
        for (produk in selectedProducts) {
            val namaProduk = getProductName(produk.idMenu)
            stringBuilder.append("$namaProduk - ${produk.kuantitas} unit\n")
        }
        return stringBuilder.toString()
    }

    private fun navigateToStruk() {
        val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        val intent = Intent(this, StrukActivity::class.java).apply {
            // Mengirim data yang diperlukan ke StrukActivity
            putExtra("idPemesanan", idPemesanan)
            putExtra("tanggal", currentDate)
            putExtra("rincianProduk", generateRincianProdukText())
            putExtra("totalKuantitas", totalKuantitas)
            putExtra("totalHarga", totalHarga)
            putExtra("metodePembayaran", metodePembayaran)
            putParcelableArrayListExtra("selectedProducts", ArrayList(selectedProducts))
        }
        println("Navigating to StrukActivity with idPemesanan: $idPemesanan")
        println("Intent data: $intent")
        startActivity(intent)
    }

    // Implementasikan logika ini sesuai dengan cara Anda mengambil harga produk dari database atau sumber lainnya
    private fun getProductPrice(menuId: Int): Int {
        return when (menuId) {
            1 -> 15000 // Contoh harga untuk produk dengan id 1
            2 -> 15000 // Contoh harga untuk produk dengan id 2
            3 -> 15000
            4 -> 15000
            5 -> 15000
            else -> 0 // Default jika id produk tidak dikenali
        }
    }

    // Implementasikan logika ini sesuai dengan kebutuhan Anda
    private fun getProductName(menuId: Int): String {
        return when (menuId) {
            1 -> "MacPop Coklat" // Contoh nama untuk produk dengan id 1
            2 -> "MacPop Original" // Contoh nama untuk produk dengan id 2
            3 -> "MacPop Vanilla"
            4 -> "MacPop Balado"
            5 -> "MacPop BBQ"
            else -> "Nama Produk Tidak Dikenal" // Default jika id produk tidak dikenali
        }
    }
}

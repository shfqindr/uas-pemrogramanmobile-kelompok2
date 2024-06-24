package com.example.macpop_uas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StrukActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var strukAdapter: StrukAdapter
    private lateinit var selectedProducts: List<DetailPemesanan>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_struk)

        // Mendapatkan data dari intent
        val idPemesanan = intent.getStringExtra("idPemesanan")
        val tanggal = intent.getStringExtra("tanggal")
        val totalKuantitas = intent.getIntExtra("totalKuantitas", 0)
        val totalHarga = intent.getIntExtra("totalHarga", 0)
        val metodePembayaran = intent.getStringExtra("metodePembayaran")
        selectedProducts = intent.getParcelableArrayListExtra("selectedProducts") ?: emptyList()

        // Menampilkan data pada UI
        findViewById<TextView>(R.id.tv_struk_id_pemesanan).text = idPemesanan
        findViewById<TextView>(R.id.tv_struk_tanggal).text = tanggal
        findViewById<TextView>(R.id.tv_struk_total_kuantitas).text = totalKuantitas.toString()
        findViewById<TextView>(R.id.tv_struk_total_harga).text = totalHarga.toString()
        findViewById<TextView>(R.id.tv_struk_metode_pembayaran).text = metodePembayaran

        // Set up RecyclerView untuk menampilkan rincian produk
        recyclerView = findViewById(R.id.rv_struk)
        recyclerView.layoutManager = LinearLayoutManager(this)
        strukAdapter = StrukAdapter(selectedProducts)
        recyclerView.adapter = strukAdapter

        val btnKembaliBeranda: Button = findViewById(R.id.btn_kembali_beranda)
        btnKembaliBeranda.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            // Menyelesaikan aktivitas saat ini sehingga tidak berada di tumpukan belakang
            finish()
            startActivity(intent)
        }
    }
}

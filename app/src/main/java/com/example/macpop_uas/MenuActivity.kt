package com.example.macpop_uas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MenuActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var menuAdapter: MenuAdapter
    private lateinit var menuList: ArrayList<Menu>
    private lateinit var dbHelper: DatabaseHelper
    private val selectedProducts = mutableListOf<DetailPemesanan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Initialize the database helper
        dbHelper = DatabaseHelper(this)

        // Set up the recycler view
        recyclerView = findViewById(R.id.rv_menu)
        recyclerView.layoutManager = LinearLayoutManager(this)
        menuList = ArrayList()
        menuAdapter = MenuAdapter(
            menuList,
            dbHelper,
            { idMenu, quantity -> updateSelectedProducts(idMenu, quantity) },
            { menuId, selected -> onProductSelected(menuId, selected) },
            selectedProducts // Pass selectedProducts to MenuAdapter
        )

        recyclerView.adapter = menuAdapter

        // Load the menu data from the database
        loadMenuData()

        // Set up the back button
        val btnBack: ImageButton = findViewById(R.id.btn_back)
        btnBack.setOnClickListener {
            onBackPressed()
        }

        // Set up the order button
        val btnOrder: Button = findViewById(R.id.button_order)
        btnOrder.setOnClickListener {
            if (selectedProducts.isNotEmpty()) {
                navigateToPembayaran(selectedProducts)
            } else {
                Toast.makeText(this, "Pilih setidaknya satu produk", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadMenuData() {
        // Clear the existing menu list and load the latest data from the database
        menuList.clear()
        menuList.addAll(dbHelper.getAllMenu())
        menuAdapter.notifyDataSetChanged()
    }

    private fun updateSelectedProducts(menuId: Int, quantity: Int) {
        val existingProduct = selectedProducts.find { it.idMenu == menuId }
        if (existingProduct != null) {
            if (quantity > 0) {
                existingProduct.kuantitas = quantity
            } else {
                selectedProducts.remove(existingProduct)
            }
        } else if (quantity > 0) {
            selectedProducts.add(DetailPemesanan(idPemesanan = 0, idMenu = menuId, kuantitas = quantity))
        }
    }

    private fun onProductSelected(menuId: Int, selected: Boolean) {
        if (selected) {
            // Add the product to selectedProducts if it's not already there
            if (selectedProducts.none { it.idMenu == menuId }) {
                val menu = menuList.find { it.id == menuId }
                menu?.let {
                    selectedProducts.add(DetailPemesanan(idPemesanan = 0, idMenu = it.id, kuantitas = 1))
                }
            }
        } else {
            // Remove the product from selectedProducts
            selectedProducts.removeAll { it.idMenu == menuId }
        }
    }

    private fun navigateToPembayaran(selectedProducts: List<DetailPemesanan>) {
        // Create an intent to navigate to the PembayaranActivity
        val intent = Intent(this, PembayaranActivity::class.java)
        intent.putParcelableArrayListExtra("selectedProducts", ArrayList(selectedProducts))
        startActivity(intent)
    }

    override fun onBackPressed() {
        // Handle the back button press
        super.onBackPressed()
    }
}

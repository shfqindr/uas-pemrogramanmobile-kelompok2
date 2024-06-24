package com.example.macpop_uas

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
@Entity(tableName = "pemesanan")
data class Pemesanan(
    @PrimaryKey(autoGenerate = true) val idPemesanan: Int=0,
    val totalHarga: Double,
    val tanggal: Date,
    val metodePembayaran: String
)


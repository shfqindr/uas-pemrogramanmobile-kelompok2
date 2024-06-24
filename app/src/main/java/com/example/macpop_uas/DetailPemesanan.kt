package com.example.macpop_uas

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "detail_pemesanan",
    foreignKeys = [
        ForeignKey(
            entity = Pemesanan::class,
            parentColumns = ["id_pemesanan"],
            childColumns = ["idPemesanan"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Menu::class,
            parentColumns = ["id"],
            childColumns = ["idMenu"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DetailPemesanan(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idPemesanan: Int,
    val idMenu: Int,
    var kuantitas: Int
) : Parcelable {

    constructor(idPemesanan: Int, idMenu: Int, kuantitas: Int) : this(
        id = 0,
        idPemesanan = idPemesanan,
        idMenu = idMenu,
        kuantitas = kuantitas
    )

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(idPemesanan)
        parcel.writeInt(idMenu)
        parcel.writeInt(kuantitas)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DetailPemesanan> {
        override fun createFromParcel(parcel: Parcel): DetailPemesanan {
            return DetailPemesanan(parcel)
        }

        override fun newArray(size: Int): Array<DetailPemesanan?> {
            return arrayOfNulls(size)
        }
    }
}

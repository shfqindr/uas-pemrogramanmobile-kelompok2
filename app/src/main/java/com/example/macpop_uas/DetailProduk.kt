package com.example.macpop_uas

import android.os.Parcel
import android.os.Parcelable

data class DetailProduk(
    val namaProduk: String,
    val jumlah: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(namaProduk)
        parcel.writeInt(jumlah)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DetailProduk> {
        override fun createFromParcel(parcel: Parcel): DetailProduk {
            return DetailProduk(parcel)
        }

        override fun newArray(size: Int): Array<DetailProduk?> {
            return arrayOfNulls(size)
        }
    }
}

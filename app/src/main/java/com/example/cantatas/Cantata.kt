package com.example.cantatas

import android.R.attr.phoneNumber
import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import kotlin.Comparator


@SuppressLint("ParcelCreator")
data class Cantata(
    val bwv: String = "",
    val name: String = "",
    val date: String = "",
    val occasion: String = "",
    val textBy: String = "",
    var id: Int,
    var rating: Float = 0f
) : Comparable<Cantata>, Parcelable {

    private val bwvInt = bwv
        .replace("[(/].+".toRegex(), "")
        .replace("[^0-9]".toRegex(), "")
        .toInt()

    var cleanDate = date
        .replace("[cbDate:-]".toRegex(), "")
        .replace("\\?".toRegex(), "")
        .replace("\\.".toRegex(), "")
        .replace(" ", "")

    private val dateInt =
        when {
            cleanDate.isEmpty() -> 0
            else -> cleanDate.substring(0,4).toInt()
        }

    val url1 = "https://bach-cantatas.com/${bwv.replace(" ", "")}.htm"

    companion object CREATOR : Parcelable.Creator<Cantata> {
        override fun createFromParcel(parcel: Parcel): Cantata {
            return Cantata(parcel)
        }

        override fun newArray(size: Int): Array<Cantata?> {
            return arrayOfNulls(size)
        }

        val byBwv = object : Comparator<Cantata> {
            override fun compare(c1: Cantata?, c2: Cantata?): Int {
                if (c1 == null || c2 == null) {
                    return 0
                }
                return c1.bwvInt.compareTo(c2.bwvInt)
            }
        }
        val byName = object : Comparator<Cantata> {
            override fun compare(c1: Cantata?, c2: Cantata?): Int {
                if (c1 == null || c2 == null) {
                    return 0
                }
                return c1.name.compareTo(c2.name)
            }
        }

        val byDate = object : Comparator<Cantata> {
            override fun compare(c1: Cantata?, c2: Cantata?): Int {
                if (c1 == null || c2 == null) {
                    return 0
                }
                return c1.dateInt.compareTo(c2.dateInt)
            }
        }

        val byRating = object : Comparator<Cantata> {
            override fun compare(c1: Cantata?, c2: Cantata?): Int {
                if (c1 == null || c2 == null) {
                    return 0
                }
                return c1.rating.compareTo(c2.rating)
            }
        }

        var activeComparator = byBwv
    }

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readFloat()
    )

    override fun compareTo(other: Cantata): Int {

        return other.bwvInt.let { bwvInt.compareTo(it) }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(bwv)
        parcel.writeString(name)
        parcel.writeString(date)
        parcel.writeString(occasion)
        parcel.writeString(textBy)
        parcel.writeInt(id)
        parcel.writeFloat(rating)
    }

    override fun describeContents(): Int {
        return 0
    }


}

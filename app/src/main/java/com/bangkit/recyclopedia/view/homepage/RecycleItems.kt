package com.bangkit.recyclopedia.view.homepage

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes

data class RecycleItem(
    @DrawableRes val image: Int,
    val title: String,
    val description: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(image)
        parcel.writeString(title)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecycleItem> {
        override fun createFromParcel(parcel: Parcel): RecycleItem {
            return RecycleItem(parcel)
        }

        override fun newArray(size: Int): Array<RecycleItem?> {
            return arrayOfNulls(size)
        }
    }
}

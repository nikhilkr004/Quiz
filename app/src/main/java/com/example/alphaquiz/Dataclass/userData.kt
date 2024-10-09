package com.example.alphaquiz.Dataclass

import android.os.Parcel
import android.os.Parcelable

data class userData(
    val name:String?=null,
    val email:String?=null,
    val password:String?=null,
    val userImage:String?=null,
    val userId:String?=null
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(password)
        parcel.writeString(userImage)
        parcel.writeString(userId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<userData> {
        override fun createFromParcel(parcel: Parcel): userData {
            return userData(parcel)
        }

        override fun newArray(size: Int): Array<userData?> {
            return arrayOfNulls(size)
        }
    }
}
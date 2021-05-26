package com.example.memesearch.models

import android.nfc.Tag
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MemeObject(
        var source: String? = null,
        var tags: String? = null,
        var created: String? = null,
        var objectId: String? = null,
        var ownerId: String? = null,
        var title: String? = null
    //Note for future developer self: make sure these are VARs, not VALs, know the difference come on!
) : Parcelable


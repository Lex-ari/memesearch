package com.example.memesearch.models

import android.nfc.Tag
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MemeObject(
        var resolution: String? = "resolution",
        var source: String? = "source",
        var tags: String? = null,
        var uploadDate: String? = "uploadDate",
        var objectId: String? = null,
        var ownerId: String? = null,
    //Note for future developer self: make sure these are VARs, not VALs, know the difference come on!
) : Parcelable


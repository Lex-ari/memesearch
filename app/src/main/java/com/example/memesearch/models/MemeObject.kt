package com.example.memesearch.models

data class MemeObject(
    var resolution: String? = "resolution",
    var source: String? = "source",
    var tags: String? = "",
    var uploadDate: String? = "uploadDate",
    var objectId: String? = null,
    var ownerId: String? = null,
    //Note for future developer self: make sure these are VARs, not VALs, know the difference come on!
){

}

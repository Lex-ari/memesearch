package com.example.memesearch

data class MemeObject(
    val source: String = "source",
    val resolution: String = "resolution",
    val tags: List<String> = listOf(),
    val uploadDate: String = "uploadDate",
    var objectId: String? = null,
    val ownerId: String? = null,
)

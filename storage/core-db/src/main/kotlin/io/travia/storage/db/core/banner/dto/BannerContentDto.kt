package io.travia.storage.db.core.banner.dto

data class BannerContentDto (
    val id: Long? = null,
    val title: String,
    val description: String,
    val imageUrl: String,
)
package io.travia.storage.db.core.banner.dto

data class BannerDto (
    val id: Long? = null,
    val title: String,
    val description: String,
    val notice: String,
    val gradient: String,
    val moveUrl: String,
    val contents: MutableList<BannerContentDto> = mutableListOf(),
)
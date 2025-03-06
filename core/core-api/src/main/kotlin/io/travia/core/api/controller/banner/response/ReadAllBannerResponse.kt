package io.travia.core.api.controller.banner.response



data class ReadAllBannerResponse (
    val id: Long,
    val title: String,
    val notice: String,
    val description: String,
    val gradient: String,
    val moveUrl: String,
    val contents: List<ReadAllBannerContentResponse>
)

data class ReadAllBannerContentResponse(
    val id: Long,
    val title: String,
    val description: String,
    val imageUrl: String,
)
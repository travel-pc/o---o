package io.travia.banner


data class Banner (
    val id: Long,
    val title: String,
    val notice: String,
    val description: String,
    val gradient: String,
    val moveUrl: String,
    val contents: List<BannerContent>
) {
}
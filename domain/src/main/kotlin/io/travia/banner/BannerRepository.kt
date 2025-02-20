package io.travia.banner

interface BannerRepository {
    fun readAll(): List<Banner>
}
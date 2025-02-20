package io.travia.banner

import org.springframework.stereotype.Component


@Component
class ReadBannerService(
    private val bannerReader: BannerReader
){
    fun readAll(): List<Banner> {
        return bannerReader.readAll()
    }
}
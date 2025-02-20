package io.travia.banner

import org.springframework.stereotype.Component

@Component
class BannerReader(
    private val bannerRepository: BannerRepository
) {

    fun readAll(): List<Banner> {
        return bannerRepository.readAll()
    }

}
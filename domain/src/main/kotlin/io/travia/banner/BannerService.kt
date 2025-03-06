package io.travia.banner

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


@Component
@Transactional(readOnly = true)
class BannerService(
    private val bannerReader: BannerReader
){

    /**
     * Banner 전체 조회
     */
    fun readAll(): List<Banner> {
        return bannerReader.readAll()
    }

}
package io.travia.storage.db.core.banner.repository

import io.travia.banner.Banner
import io.travia.banner.BannerContent
import io.travia.banner.BannerRepository
import org.springframework.stereotype.Repository


@Repository
class BannerEntityRepository(
    private val queryDslBannerEntityRepository: QueryDslBannerEntityRepository
) : BannerRepository {

    override fun readAll(): List<Banner> {
        return queryDslBannerEntityRepository.readAll().let {
            it.map { bannerDto ->
                Banner(
                    id = bannerDto.id!!,
                    title = bannerDto.title,
                    description = bannerDto.description,
                    notice = bannerDto.notice,
                    gradient = bannerDto.gradient,
                    moveUrl = bannerDto.moveUrl,
                    contents = bannerDto.contents.map { contentDto ->
                        BannerContent(
                            id = contentDto.id!!,
                            title = contentDto.title,
                            description = contentDto.description,
                            imageUrl = contentDto.imageUrl
                        )
                    }
                )
            }
        }
    }


}
package io.travia.storage.db.core.banner.repository

import io.travia.banner.Banner
import io.travia.banner.BannerContent
import io.travia.banner.BannerRepository
import io.travia.storage.db.core.banner.persistent.BannerContentEntity
import io.travia.storage.db.core.banner.persistent.BannerEntity
import org.springframework.stereotype.Repository


@Repository
class BannerEntityRepository(
    private val queryDslBannerEntityRepository: QueryDslBannerEntityRepository,
    private val queryDslBannerContentRepository: QueryDslBannerContentEntityRepository
) : BannerRepository {

    override fun readAll(): List<Banner> {
        val banners = queryDslBannerEntityRepository.findAll()
        val bannerIds = banners.map { it.bannerId }
        val bannerContentEntityMaps = queryDslBannerContentRepository.findByBannerIds(bannerIds);
        return banners.map { bannerEntity ->
            Banner(
                id = bannerEntity.bannerId,
                title = bannerEntity.title,
                description = bannerEntity.description,
                notice = bannerEntity.notice,
                gradient = bannerEntity.gradient,
                moveUrl = bannerEntity.moveUrl,
                contents = bannerContentEntityMaps[bannerEntity.bannerId]?.map {
                    it -> BannerContent(
                        id = it.bannerContentId,
                        title = it.title,
                        description
                        = it.description,
                        imageUrl = it.imageUrl,
                    )
                } ?: emptyList()
            )
        }
    }
}
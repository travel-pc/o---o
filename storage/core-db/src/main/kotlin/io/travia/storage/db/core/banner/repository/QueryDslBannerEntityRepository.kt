package io.travia.storage.db.core.banner.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import io.travia.banner.Banner
import io.travia.storage.db.core.banner.dto.BannerContentDto
import io.travia.storage.db.core.banner.dto.BannerDto
import io.travia.storage.db.core.banner.persistent.BannerEntity
import io.travia.storage.db.core.banner.persistent.QBannerContentEntity.bannerContentEntity
import io.travia.storage.db.core.banner.persistent.QBannerContentImageEntity.bannerContentImageEntity
import io.travia.storage.db.core.banner.persistent.QBannerEntity.bannerEntity
import org.springframework.stereotype.Repository


@Repository
class QueryDslBannerEntityRepository (
    private val jpaQueryFactory: JPAQueryFactory
){


    fun readAll(): List<BannerDto> {
        val banners: List<BannerEntity> = jpaQueryFactory
            .selectFrom(bannerEntity)
            .fetch()

        return banners.let { bannerList ->

            val bannerIds = bannerList.map { it.id }

            val contentsByBanner = bannerIds.associateWith { bannerId ->
                jpaQueryFactory
                    .selectFrom(bannerContentEntity)
                    .where(bannerContentEntity.bannerId.eq(bannerId))
                    .fetch()
            }

            val contentIds = contentsByBanner.values.flatten().map { it.id }

            val imagesByContent = contentIds.associateWith { contentId ->
                jpaQueryFactory
                    .selectFrom(bannerContentImageEntity)
                    .where(bannerContentImageEntity.bannerContentId.eq(contentId))
                    .fetchOne()
            }

            bannerList.map { banner ->
                BannerDto(
                    id = banner.id,
                    title = banner.title,
                    description = banner.description,
                    notice = banner.notice,
                    gradient = banner.gradient,
                    moveUrl = banner.moveUrl,
                    contents = contentsByBanner[banner.id]?.map { content ->
                        BannerContentDto(
                            id = content.id,
                            title = content.title,
                            description = content.description,
                            imageUrl = imagesByContent[content.id]?.imageUrl!!
                        )
                    }?.toMutableList() ?: mutableListOf()
                )
            }
        }
    }

}
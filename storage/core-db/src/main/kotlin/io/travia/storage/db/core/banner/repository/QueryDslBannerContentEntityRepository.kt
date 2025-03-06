package io.travia.storage.db.core.banner.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import io.travia.storage.db.core.banner.persistent.BannerContentEntity
import io.travia.storage.db.core.banner.persistent.QBannerContentEntity.bannerContentEntity
import org.springframework.stereotype.Repository


@Repository
class QueryDslBannerContentEntityRepository (
    private val jpaQueryFactory: JPAQueryFactory
){
    fun findByBannerIds(bannerIds: List<Long>): Map<Long, List<BannerContentEntity>> {
        val results = jpaQueryFactory
            .selectFrom(bannerContentEntity)
            .where(bannerContentEntity.bannerId.`in`(bannerIds))
            .fetch()

        return results.groupBy { it.bannerId }
    }
}
package io.travia.storage.db.core.banner.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import io.travia.storage.db.core.banner.persistent.BannerContentEntity
import io.travia.storage.db.core.banner.persistent.BannerEntity
import io.travia.storage.db.core.banner.persistent.QBannerEntity.bannerEntity
import org.springframework.stereotype.Repository


@Repository
class QueryDslBannerEntityRepository (
    private val jpaQueryFactory: JPAQueryFactory
){

    fun findAll(): List<BannerEntity> {
        return jpaQueryFactory.selectFrom(bannerEntity)
            .fetch();
    }

}
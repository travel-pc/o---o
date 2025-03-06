package io.travia.storage.db.core.product.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import io.travia.storage.db.core.product.persistent.ProductPartsEntity
import io.travia.storage.db.core.product.persistent.QPartsEntity.partsEntity
import io.travia.storage.db.core.product.persistent.QProductPartsEntity.productPartsEntity
import org.springframework.stereotype.Repository


@Repository
class QueryDslProductPartsEntityRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {

    fun findByProductId(productId: Long): List<ProductPartsEntity> {
        return jpaQueryFactory
            .selectFrom(productPartsEntity)
            .innerJoin(productPartsEntity.partsEntity, partsEntity).fetchJoin()
            .where(productPartsEntity.productId.eq(productId))
            .fetch()
    }

}
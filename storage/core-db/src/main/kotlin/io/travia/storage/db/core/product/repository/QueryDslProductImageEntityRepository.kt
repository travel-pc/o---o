package io.travia.storage.db.core.product.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import io.travia.storage.db.core.product.persistent.ProductImageEntity
import io.travia.storage.db.core.product.persistent.QProductImageEntity.productImageEntity
import org.springframework.stereotype.Repository


@Repository
class QueryDslProductImageEntityRepository(
    private val jpaQueryFactory: JPAQueryFactory
) {

    fun findByProductId(productId: Long): List<ProductImageEntity> {
        return jpaQueryFactory.selectFrom(productImageEntity)
            .where(productImageEntity.productId.eq(productId))
            .fetch()
    }
}
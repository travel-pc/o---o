package io.travia.storage.db.core.product.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import io.travia.storage.db.core.product.persistent.ProductDetailImageEntity
import io.travia.storage.db.core.product.persistent.QProductDetailImageEntity.productDetailImageEntity
import org.springframework.stereotype.Repository


@Repository
class QueryDslProductDetailImageEntityRepository (
    private val jpaQueryFactory: JPAQueryFactory
) {

    fun findByProductId(productId: Long): List<ProductDetailImageEntity> {
        return jpaQueryFactory.selectFrom(productDetailImageEntity)
            .where(productDetailImageEntity.productId.eq(productId))
            .fetch()
    }


}
package io.travia.storage.db.core.product.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import io.travia.storage.db.core.product.persistent.QShippingMethodEntity.shippingMethodEntity
import io.travia.storage.db.core.product.persistent.ShippingMethodEntity
import org.springframework.stereotype.Repository


@Repository
class QueryDslShippingMethodEntityRepository (
    private val jpaQueryFactory: JPAQueryFactory
){
    fun findByProductId(productId: Long): List<ShippingMethodEntity> {
        return jpaQueryFactory.selectFrom(shippingMethodEntity)
            .where(shippingMethodEntity.productId.eq(productId))
            .fetch();
    }
}
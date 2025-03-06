package io.travia.storage.db.core.product.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import io.travia.storage.db.core.product.persistent.ProductEntity
import io.travia.storage.db.core.product.persistent.QProductEntity.productEntity
import org.springframework.stereotype.Repository
import java.util.Optional


@Repository
class QueryDslProductEntityRepository(
    private val jpaQueryFactory: JPAQueryFactory
) {

    fun findById(productId: Long): ProductEntity {
        return jpaQueryFactory.selectFrom(productEntity)
            .where(productEntity.productId.eq(productId))
            .fetchFirst() ?: throw NoSuchElementException("Product with ID $productId not found")
    }
}
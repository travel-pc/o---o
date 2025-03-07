package io.travia.storage.db.core.product.persistent

import io.travia.ProductType
import io.travia.storage.db.core.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "product")
class ProductEntity (
    @Id
    val productId: Long,
    val discountRate: Long,
    val manufacturer: String,
    val title: String,

    @Enumerated(EnumType.STRING)
    val productType: ProductType,

) : BaseEntity() {

}
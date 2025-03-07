package io.travia.storage.db.core.product.persistent

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "product_detail_image")
class ProductDetailImageEntity(

    @Id
    val productDetailImageId: Long,
    val productId: Long,
    val imageUrl: String,
) {
}
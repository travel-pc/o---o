package io.travia.storage.db.core.product.persistent

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "product_image")
class ProductImageEntity (
    @Id
    val partsImageId: Long,

    @Column(name = "product_id")
    val productId: Long,

    @Column(name = "image_url")
    val imageUrl: String,
){

}
package io.travia.storage.db.core.product.persistent

import io.travia.ShippingType
import io.travia.storage.db.core.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "shipping_method")
class ShippingMethodEntity(

    @Id
    val shippingMethodId: Long,

    @Column(name = "product_id")
    val productId: Long,

    @Column(name = "shipping_type")
    @Enumerated(EnumType.STRING)
    val shippingType: ShippingType,

    @Column(name = "additional_cost")
    val additionalCost: Long,

    @Column(name = "is_free")
    val isFree: Boolean,

    ) : BaseEntity() {

}
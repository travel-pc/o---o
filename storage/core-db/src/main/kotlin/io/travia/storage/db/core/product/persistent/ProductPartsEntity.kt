package io.travia.storage.db.core.product.persistent

import io.travia.storage.db.core.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table


@Entity
@Table(name = "product_parts")
class ProductPartsEntity(

    @Id
    @Column(name = "product_parts_id")
    val productPartsId: Long,

    @Column(name = "product_id")
    val productId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parts_id")
    val partsEntity: PartsEntity,

    @Column(name = "is_option")
    val isOption: Boolean,

) : BaseEntity() {


}
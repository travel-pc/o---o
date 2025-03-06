package io.travia.storage.db.core.product.persistent

import io.travia.PartsType
import io.travia.StockStatus
import io.travia.storage.db.core.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "parts")
class PartsEntity(

    @Id
    val partsId: Long,

    @Column(name = "parts_name")
    val partsName: String,

    @Column(name = "price")
    val price: Long,

    @Column(name = "spec")
    val spec: String,

    val manufacturer: String,

    val imageUrl: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "stock_status")
    val stockStatus: StockStatus,

    @Enumerated(EnumType.STRING)
    @Column(name = "parts_type")
    val partsType: PartsType,

    ) : BaseEntity() {

}
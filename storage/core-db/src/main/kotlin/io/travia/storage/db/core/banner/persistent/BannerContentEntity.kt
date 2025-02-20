package io.travia.storage.db.core.banner.persistent

import io.travia.storage.db.core.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity


@Entity
class BannerContentEntity (

    @Column(name = "banner_id")
    val bannerId: Long,

    @Column(name = "title")
    val title: String,

    @Column(name = "description")
    val description: String,

) : BaseEntity() {

}
package io.travia.storage.db.core.banner.persistent

import io.travia.storage.db.core.BaseEntity
import jakarta.persistence.Column

class BannerContentImageEntity(

    @Column(name = "image_url")
    val imageUrl: String,

    @Column(name = "extension")
    val extension: String,

    @Column(name = "size")
    val fileSize: Long,

    @Column(name = "original_name")
    val originalName: String,

    @Column(name = "uuid")
    val uuid: String,

    ) : BaseEntity() {
}
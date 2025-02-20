package io.travia.storage.db.core.banner.persistent

import io.travia.storage.db.core.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity(name = "banner_content_image")
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

    @Column(name = "banner_content_id")
    val bannerContentId: Long,

    ) : BaseEntity() {
}
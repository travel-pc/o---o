package io.travia.storage.db.core.banner.persistent

import io.travia.storage.db.core.BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table


@Entity
@Table(name = "banner_content")
class BannerContentEntity (

    @Id
    @Column(name = "banner_content_id")
    val bannerContentId: Long,

    @Column(name = "banner_id")
    val bannerId: Long,

    @Column(name = "title")
    val title: String,

    @Column(name = "description")
    val description: String,

    @Column(name = "image_url")
    val imageUrl: String,

) : BaseEntity() {

}
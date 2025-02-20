package io.travia.storage.db.core.banner.persistent

import io.travia.storage.db.core.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity


@Entity(name = "banner")
class BannerEntity(

    @Column(name = "title")
    val  title: String,

    @Column(name = "notice")
    val notice: String,

    @Column(name = "description")
    val description: String,

    @Column(name = "gradient")
    val gradient: String,

    @Column(name = "move_url")
    val moveUrl: String,

) : BaseEntity(){
}
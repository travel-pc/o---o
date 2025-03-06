package io.travia.storage.db.core.banner.persistent

import io.travia.storage.db.core.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity(name = "banner")
@Table(name = "banner")
class BannerEntity(

    @Id
    @Column(name = "banner_id")
    val bannerId: Long,

    @Column(name = "title")
    val title: String,

    @Column(name = "notice")
    val notice: String,

    @Column(name = "description")
    val description: String,

    @Column(name = "gradient")
    val gradient: String,

    @Column(name = "move_url")
    val moveUrl: String,

) : BaseEntity(){

    companion object {
        fun fixture(bannerId: Long, title: String): BannerEntity {
            return BannerEntity(
                bannerId = bannerId,
                title = title,
                notice = "notice",
                description = "description",
                gradient = "gradient",
                moveUrl = "moveUrl",
            )
        }
    }
}
package io.travia.storage.db.core.banner.repository

import io.travia.storage.db.core.banner.persistent.BannerContentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface JpaBannerContentEntityRepository : JpaRepository<BannerContentEntity, Long> {
}
package io.travia.storage.db.core.banner.repository

import io.travia.storage.db.core.banner.persistent.BannerContentImageEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface JpaBannerContentImageEntityRepository : JpaRepository<BannerContentImageEntity, Long> {
}
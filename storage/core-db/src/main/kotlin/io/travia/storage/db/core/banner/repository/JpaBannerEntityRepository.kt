package io.travia.storage.db.core.banner.repository

import io.travia.storage.db.core.banner.persistent.BannerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface JpaBannerEntityRepository : JpaRepository<BannerEntity, Long>{
}
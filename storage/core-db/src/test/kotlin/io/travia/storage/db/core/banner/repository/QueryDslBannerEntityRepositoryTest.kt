package io.travia.storage.db.core.banner.repository

import io.travia.storage.db.CoreDbContextTest
import io.travia.storage.db.core.banner.dto.BannerContentDto
import io.travia.storage.db.core.banner.persistent.BannerContentEntity
import io.travia.storage.db.core.banner.persistent.BannerContentImageEntity
import io.travia.storage.db.core.banner.persistent.BannerEntity
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


class QueryDslBannerEntityRepositoryTest(
    private val queryDslBannerEntityRepository: QueryDslBannerEntityRepository,
    private val jpaBannerEntityRepository: JpaBannerEntityRepository,
    private val jpaBannerContentEntityRepository: JpaBannerContentEntityRepository,
    private val jpaBannerContentImageEntityRepository: JpaBannerContentImageEntityRepository

) : CoreDbContextTest() {

    @Test
    @DisplayName("배너 전체 조회 테스트")
    fun readAll() {
        // given
        val bannerEntity = BannerEntity(
            title = "테스트 배너",
            description = "배너 설명",
            notice = "공지 사항",
            gradient = "blue",
            moveUrl = "https://example.com"
        )

        val bannerId = jpaBannerEntityRepository.save(bannerEntity).id

        val bannerContentEntity = BannerContentEntity(
            title = "title",
            description = "description",
            bannerId = bannerId
        )

        val bannerContentId = jpaBannerContentEntityRepository.save(bannerContentEntity).id

        jpaBannerContentImageEntityRepository.save(
            BannerContentImageEntity(
                imageUrl = "https://example.com",
                bannerContentId = bannerContentId,
                extension = "png",
                fileSize = 100L,
                originalName = "test.png",
                uuid = "uuid"
            )
        )

        val banners = queryDslBannerEntityRepository.readAll()

        assertThat(banners).hasSize(1)
            .extracting("id", "title", "description", "notice", "gradient", "moveUrl", "contents")
            .containsExactly(
                tuple(
                    1L,
                    "테스트 배너",
                    "배너 설명",
                    "공지 사항",
                    "blue",
                    "https://example.com",
                    listOf(
                        BannerContentDto(
                            id = 1,
                            title = "title",
                            description = "description",
                            imageUrl = "https://example.com"
                        )
                    )
                )
            )
    }

}
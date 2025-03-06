package io.travia.storage.db.core.banner.repository

import io.travia.storage.db.CoreDbContextTest
import io.travia.storage.db.core.banner.persistent.BannerContentEntity
import io.travia.storage.db.core.banner.persistent.BannerEntity
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


class BannerEntityRepositoryTest (
    private val bannerEntityRepository: BannerEntityRepository,
    private val jpaBannerEntityRepository: JpaBannerEntityRepository,
    private val jpaBannerContentEntityRepository: JpaBannerContentEntityRepository
) :CoreDbContextTest() {

        @Test
        @DisplayName("배너 정보와 배너 하위 정보를 모두 조회 한다")
        fun readAllTest() {
            val bannerId1: Long = createBanner(1L, "배너 테스트 1")
            createBannerContent(1L, bannerId1, "배너 하위 1", "배너 하위 1", "이미지 1")
            createBannerContent(2L, bannerId1, "배너 하위 2", "배너 하위 2", "이미지 2")
            createBannerContent(3L, bannerId1, "배너 하위 3", "배너 하위 3", "이미지 3")

            val bannerId2: Long = createBanner(2L, "배너 테스트 2")

            val result = bannerEntityRepository.readAll()

            assertThat(result).hasSize(2)
                .extracting("id", "title")
                .containsExactlyInAnyOrder(
                    tuple(bannerId1, "배너 테스트 1"),
                    tuple(bannerId2, "배너 테스트 2")
                )

            assertThat(result.find { it.id == bannerId1 }?.contents)
                .hasSize(3)
                .extracting("id", "title", "description", "imageUrl")
                .containsExactlyInAnyOrder(
                    tuple(1L, "배너 하위 1", "배너 하위 1", "이미지 1"),
                    tuple(2L, "배너 하위 2", "배너 하위 2", "이미지 2"),
                    tuple(3L, "배너 하위 3", "배너 하위 3", "이미지 3")
                )

            assertThat(result.find { it.id == bannerId2 }?.contents)
                .isEmpty()
        }



    private fun createBanner(id: Long, title: String): Long {
        val banner = BannerEntity(
            bannerId = id,
            title = title,
            description = "배너 설명 $id",
            notice = "공지 $id",
            gradient = "gradient$id",
            moveUrl = "http://move.url/$id"
        )
        return jpaBannerEntityRepository.save(banner).bannerId
    }

    private fun createBannerContent(
        id: Long, bannerId: Long, title: String, description: String, imageUrl: String
    ) {
        val bannerContent = BannerContentEntity(
            bannerContentId = id,
            bannerId = bannerId,
            title = title,
            description = description,
            imageUrl = imageUrl
        )
        jpaBannerContentEntityRepository.save(bannerContent)
    }


}
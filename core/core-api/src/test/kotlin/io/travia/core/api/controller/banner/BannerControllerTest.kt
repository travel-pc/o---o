package io.travia.core.api.controller.banner

import io.mockk.every
import io.mockk.mockk
import io.restassured.http.ContentType
import io.travia.banner.Banner
import io.travia.banner.BannerContent
import io.travia.banner.ReadBannerService
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import io.travia.test.api.RestDocsTest
import io.travia.test.api.RestDocsUtils.requestPreprocessor
import io.travia.test.api.RestDocsUtils.responsePreprocessor
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields


class BannerControllerTest : RestDocsTest() {
    private lateinit var readBannerService: ReadBannerService
    private lateinit var bannerController: BannerController

    @BeforeEach
    fun setUp() {
        readBannerService = mockk(relaxUnitFun = true)
        bannerController = BannerController(readBannerService)
        mockMvc = mockController(bannerController)
    }

    @Test
    fun should_banner_detail_list_when_all_200() {
        every {
            readBannerService.readAll()
        } returns listOf(
            Banner(
                id = 1L,
                title = "2025년 혜택 알아보기!",
                description = "조립 피씨 전문 트래블 피씨",
                notice = "다음 혜택은 특정 기간 조건에 따라 적용됩니다!",
                gradient = "linear-gradient(90deg,#FFD700 0%,#FF8C00 100%)",
                moveUrl = "https://example.com",
                contents = listOf(
                    BannerContent(
                        id = 1L,
                        title = "커피 기프티콘 증정",
                        description = "포토 리뷰 작성 시",
                        imageUrl = "https://img.danawa.com.image"
                    ),
                    BannerContent(
                        id = 2L,
                        title = "신학기 특별 세일",
                        description = "새로운 신학기 시즌을 응원해요",
                        imageUrl = "https://img.danawa.com.image"
                    )
                ),
            )
        )

        given()
            .contentType(ContentType.JSON)
            .`when`()
            .get("api/v1/banners")
            .then()
            .statusCode(HttpStatus.OK.value())
            .apply(
                document(
                    "read-banner-all",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    responseFields(
                        fieldWithPath("result").type(JsonFieldType.STRING).description("결과 상태"),
                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("배너 리스트"),

                        fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("배너 ID"),
                        fieldWithPath("data[].title").type(JsonFieldType.STRING).description("배너 제목"),
                        fieldWithPath("data[].description").type(JsonFieldType.STRING).description("배너 설명"),
                        fieldWithPath("data[].notice").type(JsonFieldType.STRING).description("배너 공지사항"),
                        fieldWithPath("data[].gradient").type(JsonFieldType.STRING).description("배경 그라디언트"),
                        fieldWithPath("data[].moveUrl").type(JsonFieldType.STRING).description("이동할 URL"),
                        fieldWithPath("data[].contents").type(JsonFieldType.ARRAY).description("배너 컨텐츠 리스트"),

                        fieldWithPath("data[].contents[].id").type(JsonFieldType.NUMBER).description("컨텐츠 ID"),
                        fieldWithPath("data[].contents[].title").type(JsonFieldType.STRING).description("컨텐츠 제목"),
                        fieldWithPath("data[].contents[].description").type(JsonFieldType.STRING).description("컨텐츠 설명"),
                        fieldWithPath("data[].contents[].imageUrl").type(JsonFieldType.STRING).description("컨텐츠 이미지 URL"),

                        fieldWithPath("error").type(JsonFieldType.NULL).description("에러 정보 (없을 경우 null)")

                    )
                )
            )
    }
}
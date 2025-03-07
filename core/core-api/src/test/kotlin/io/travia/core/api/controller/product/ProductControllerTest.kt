package io.travia.core.api.controller.product

import io.mockk.every
import io.mockk.mockk
import io.restassured.http.ContentType
import io.travia.PartsType
import io.travia.ProductType
import io.travia.StockStatus
import io.travia.core.api.controller.banner.BannerController
import io.travia.product.Parts
import io.travia.product.Product
import io.travia.product.ProductService
import io.travia.product.ShippingMethod
import io.travia.test.api.RestDocsTest
import io.travia.test.api.RestDocsUtils.requestPreprocessor
import io.travia.test.api.RestDocsUtils.responsePreprocessor
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName


class ProductControllerTest : RestDocsTest() {

    private lateinit var productController: ProductController
    private lateinit var productService: ProductService

    @BeforeEach
    fun setUp() {
        productService = mockk(relaxUnitFun = true)
        productController = ProductController(productService)
        mockMvc = mockController(productController)
    }

    @Test
    fun `should read product detail when status 200`() {
        every { productService.read(any()) } returns Product(
            productId = 1L,
            title = "고성능 게이밍 PC",
            discountRate = 10,
            price = 1500000,
            productType = ProductType.GAMING,
            manufacturer = "TechBrand",
            stockStatus = StockStatus.IN_STOCK,
            imageUrls = mutableListOf("https://example.com/product1.jpg"),
            detailImageUrls = mutableListOf("https://example.com/product_detail1.jpg"),
            parts = mutableListOf(
                Parts(
                    partsId = 1L,
                    name = "Intel Core i9",
                    price = 500000,
                    manufacturer = "Intel",
                    spec = "12세대 CPU",
                    isOption = false,
                    partsType = PartsType.CPU,
                    imageUrl = "https://example.com/cpu.jpg",
                    stockStatus = StockStatus.IN_STOCK
                ),
                Parts(
                    partsId = 2L,
                    name = "NVIDIA RTX 3080",
                    price = 700000,
                    manufacturer = "NVIDIA",
                    spec = "그래픽카드",
                    isOption = true,
                    partsType = PartsType.GRAPHICS_CARD,
                    imageUrl = "https://example.com/gpu.jpg",
                    stockStatus = StockStatus.IN_STOCK
                )
            ),
            shippingMethods = mutableListOf(
                ShippingMethod(
                    shippingMethodId = 1L,
                    additionalCost = 0,
                    name = "무료배송",
                    isFree = true
                )
            )
        )

        given()
            .contentType(ContentType.JSON)
            .`when`()
            .get("api/v1/products/{productId}/details", 1L)
            .then()
            .statusCode(HttpStatus.OK.value())
            .apply(
                document(
                    "get-product-details",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    RequestDocumentation.pathParameters(
                        parameterWithName("productId").description("상품 ID")
                    ),
                    responseFields(
                        fieldWithPath("result").type(JsonFieldType.STRING).description("응답 결과 타입"),
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("상품 상세 정보"),
                        fieldWithPath("data.productId").type(JsonFieldType.NUMBER).description("상품 ID"),
                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("상품명"),
                        fieldWithPath("data.price").type(JsonFieldType.NUMBER).description("기본 가격"),
                        fieldWithPath("data.discountPrice").type(JsonFieldType.NUMBER).description("할인 가격"),
                        fieldWithPath("data.discountRate").type(JsonFieldType.NUMBER).description("할인율"),
                        fieldWithPath("data.manufacturer").type(JsonFieldType.STRING).description("제조사"),
                        fieldWithPath("data.productType").type(JsonFieldType.STRING).description("상품 카테고리"),
                        fieldWithPath("data.stockStatus").type(JsonFieldType.STRING).description("상품 재고 상태"),
                        fieldWithPath("data.parts").type(JsonFieldType.ARRAY)
                            .description("상품 부품 목록")
                            .optional(),
                        fieldWithPath("data.parts[].partsId").type(JsonFieldType.NUMBER).description("부품 ID"),
                        fieldWithPath("data.parts[].name").type(JsonFieldType.STRING).description("부품명"),
                        fieldWithPath("data.parts[].price").type(JsonFieldType.NUMBER).description("부품 가격"),
                        fieldWithPath("data.parts[].partsType").type(JsonFieldType.STRING).description("부품 타입"),
                        fieldWithPath("data.parts[].isOption").type(JsonFieldType.BOOLEAN).description("선택 옵션 여부"),
                        fieldWithPath("data.parts[].stockStatus").type(JsonFieldType.STRING).description("부품 재고 상태"),
                        fieldWithPath("data.parts[].spec").type(JsonFieldType.STRING).description("부품 사양"),
                        fieldWithPath("data.parts[].manufacturer").type(JsonFieldType.STRING).description("부품 제조사"),
                        fieldWithPath("data.parts[].imageUrl").type(JsonFieldType.STRING).description("부품 이미지 URL"),
                        fieldWithPath("data.shippingMethods").type(JsonFieldType.ARRAY)
                            .description("배송 옵션 목록")
                            .optional(),
                        fieldWithPath("data.shippingMethods[].shippingMethodId").type(JsonFieldType.NUMBER).description("배송 방식 ID"),
                        fieldWithPath("data.shippingMethods[].additionalCost").type(JsonFieldType.NUMBER).description("배송 가격"),
                        fieldWithPath("data.shippingMethods[].isFree").type(JsonFieldType.BOOLEAN).description("무료 여부"),
                        fieldWithPath("data.shippingMethods[].name").type(JsonFieldType.STRING).description("배송 방식 이름"),
                        fieldWithPath("data.imageUrls").type(JsonFieldType.ARRAY)
                            .description("상품 이미지 URL 목록")
                            .optional(),
                        fieldWithPath("data.detailImageUrls").type(JsonFieldType.ARRAY)
                            .description("상품 상세 이미지 URL 목록")
                            .optional(),
                        fieldWithPath("error").type(JsonFieldType.NULL).ignored()
                    )
                )
            )
    }

    }
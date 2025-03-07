package io.travia.product

import io.travia.PartsType
import io.travia.ProductType
import io.travia.StockStatus
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat

class ProductTest {

    @Test
    @DisplayName("할인된 가격을 정상적으로 계산해야 한다")
    fun calculateDiscountPriceTest() {
        // Given
        val product1 = createProduct(price = 1000L, discountRate = 10) // 10% 할인
        val product2 = createProduct(price = 2000L, discountRate = 50) // 50% 할인
        val product3 = createProduct(price = 500L, discountRate = 100) // 100% 할인 (최소값 체크)
        val product4 = createProduct(price = 3000L, discountRate = 0)  // 할인 없음

        // When
        val discountedPrice1 = product1.calculateDiscountPrice()
        val discountedPrice2 = product2.calculateDiscountPrice()
        val discountedPrice3 = product3.calculateDiscountPrice()
        val discountedPrice4 = product4.calculateDiscountPrice()

        // Then
        assertThat(discountedPrice1).isEqualTo(900)
        assertThat(discountedPrice2).isEqualTo(1000)
        assertThat(discountedPrice3).isEqualTo(0)
        assertThat(discountedPrice4).isEqualTo(3000)
    }

    @Test
    @DisplayName("필수부품(DISCONTINUED, OUT_OF_STOCK)이 각각 동일 개수일 때 상태는 OUT_OF_STOCK")
    fun `determineStockStatus when same count of discontinued and out of stock`() {
        val product = createProduct(
            mutableListOf(
                createParts(1L, false, PartsType.CPU, StockStatus.DISCONTINUED),
                createParts(2L, false, PartsType.CASE, StockStatus.OUT_OF_STOCK),
                createParts(3L, true, PartsType.ETC, StockStatus.DISCONTINUED),
            )
        )

        product.decideStockStatus()

        assertThat(product.stockStatus).isEqualTo(StockStatus.OUT_OF_STOCK)
    }

    @Test
    @DisplayName("필수부품 중 DISCONTINUED가 더 많을 때 상태는 DISCONTINUED")
    fun `determineStockStatus when discontinued more than out of stock`() {
        val product = createProduct(
            mutableListOf(
                createParts(1L, false, PartsType.CPU, StockStatus.DISCONTINUED),
                createParts(2L, false, PartsType.CASE, StockStatus.DISCONTINUED),
                createParts(3L, false, PartsType.SSD, StockStatus.OUT_OF_STOCK),
            )
        )

        product.decideStockStatus()

        assertThat(product.stockStatus).isEqualTo(StockStatus.DISCONTINUED)
    }

    @Test
    @DisplayName("필수부품 중 OUT_OF_STOCK이 더 많을 때 상태는 OUT_OF_STOCK")
    fun `determineStockStatus when out of stock more than discontinued`() {
        val product = createProduct(
            mutableListOf(
                createParts(1L, false, PartsType.CPU, StockStatus.DISCONTINUED),
                createParts(2L, false, PartsType.SSD, StockStatus.OUT_OF_STOCK),
                createParts(3L, false, PartsType.SSD, StockStatus.OUT_OF_STOCK),
            )
        )

        product.decideStockStatus()

        assertThat(product.stockStatus).isEqualTo(StockStatus.OUT_OF_STOCK)
    }

    @Test
    @DisplayName("필수부품이 모두 IN_STOCK이면 상품 상태는 IN_STOCK")
    fun `determineStockStatus when all required parts are in stock`() {
        val product = createProduct(
            mutableListOf(
                createParts(1L, false, PartsType.CPU, StockStatus.IN_STOCK),
                createParts(2L, false, PartsType.CPU, StockStatus.IN_STOCK),
                createParts(3L, true, PartsType.ETC, StockStatus.DISCONTINUED),
            )
        )

        product.decideStockStatus()

        assertThat(product.stockStatus).isEqualTo(StockStatus.IN_STOCK)
    }

    @Test
    @DisplayName("옵션부품이 DISCONTINUED라도 필수부품이 모두 IN_STOCK이면 상품 상태는 IN_STOCK")
    fun `determineStockStatus when optional discontinued and required in stock`() {
        val product = createProduct(
            mutableListOf(
                createParts(1L, false, PartsType.CPU, StockStatus.IN_STOCK),
                createParts(2L, false, PartsType.CPU, StockStatus.IN_STOCK),
                createParts(3L, true, PartsType.CPU, StockStatus.DISCONTINUED),
            )
        )

        product.decideStockStatus()

        assertThat(product.stockStatus).isEqualTo(StockStatus.IN_STOCK)
    }

    @Test
    @DisplayName("부품을 타입별 order값으로 정렬한다")
    fun `sortByParts with parts type order`() {
        val product = createProduct(
            mutableListOf(
                createParts(1L, false, PartsType.SSD),
                createParts(2L, false, PartsType.CPU),
                createParts(3L, false, PartsType.MEMORY),
            )
        )

        product.sortByParts()

        assertThat(product.parts.map { it.partsType }).containsExactly(
            PartsType.CPU,
            PartsType.MEMORY,
            PartsType.SSD
        )
    }

    @Test
    @DisplayName("부품 order가 동일하면 필수부품(isOption=false)이 옵션보다 우선된다")
    fun `sortByParts with same order but different isOption`() {
        val product = createProduct(
            mutableListOf(
                createParts(1L, true, PartsType.CPU),
                createParts(2L, false, PartsType.CPU),
            )
        )

        product.sortByParts()

        assertThat(product.parts).extracting("isOption").containsExactly(
            false,
            true
        )
    }

    @Test
    @DisplayName("옵션부품이 동일 타입 필수부품 뒤로 정렬된다")
    fun `sortByParts with mixed option and required parts`() {
        val product = createProduct(
            mutableListOf(
                createParts(1L, false, PartsType.CPU),
                createParts(2L, true, PartsType.SSD),
                createParts(3L, true, PartsType.CPU),
                createParts(4L, false, PartsType.SSD),
            )
        )

        product.sortByParts()

        assertThat(product.parts.map { Pair(it.partsType, it.isOption) }).containsExactly(
            Pair(PartsType.CPU, false),
            Pair(PartsType.CPU, true),
            Pair(PartsType.SSD, false),
            Pair(PartsType.SSD, true),
        )
    }

    @Test
    @DisplayName("옵션여부와 타입 order 혼합된 경우 올바르게 정렬된다")
    fun `sortByParts complex sorting`() {
        val product = createProduct(
            mutableListOf(
                createParts(1L, true, PartsType.SSD),
                createParts(2L, false, PartsType.CPU),
                createParts(3L, false, PartsType.ETC),
                createParts(4L, true, PartsType.GRAPHICS_CARD),
            )
        )

        product.sortByParts()

        assertThat(product.parts.map { it.partsType }).containsExactly(
            PartsType.CPU,
            PartsType.SSD,
            PartsType.GRAPHICS_CARD,
            PartsType.ETC
        )
    }


    private fun createProduct(price: Long, discountRate: Long): Product {
        return Product(
            price = price,
            discountRate = discountRate,
            title = "Product",
            productId = 1L,
            productType = ProductType.GAMING,
            manufacturer = "Product",
            parts = mutableListOf(),
            shippingMethods = mutableListOf(),
            imageUrls = mutableListOf(),
            detailImageUrls = mutableListOf()
        )
    }

    private fun createProduct(parts: MutableList<Parts>): Product {
        return Product(
            price = 10000L,
            discountRate = 10L,
            title = "Product",
            productId = 1L,
            productType = ProductType.GAMING,
            manufacturer = "Product",
            parts = parts,
            shippingMethods = mutableListOf(),
            imageUrls = mutableListOf(),
            detailImageUrls = mutableListOf()
        )
    }

    private fun createParts(partsId: Long, isOption: Boolean, partsType: PartsType, stockStatus: StockStatus): Parts{
        return  Parts(
            partsId = partsId,
            name = "Part1",
            price = 1000L,
            manufacturer = "Part1",
            spec = "spec",
            isOption = isOption,
            partsType = partsType,
            imageUrl = "image/jpeg",
            stockStatus = stockStatus
        )
    }

    private fun createParts(partsId: Long, isOption: Boolean, partsType: PartsType): Parts{
        return  Parts(
            partsId = partsId,
            name = "Part1",
            price = 1000L,
            manufacturer = "Part1",
            spec = "spec",
            isOption = isOption,
            partsType = partsType,
            imageUrl = "image/jpeg",
            stockStatus = StockStatus.IN_STOCK
        )
    }
}
package io.travia.product

import io.travia.ProductType
import io.travia.StockStatus

data class Product(
    val productId: Long,
    val title: String,
    val discountRate: Long,
    val price: Long = 0L,
    val discountPrice: Long = 0L,
    val productType: ProductType,
    val manufacturer: String,
    var parts: MutableList<Parts>,
    var stockStatus: StockStatus = StockStatus.IN_STOCK,
    val shippingMethods: List<ShippingMethod>,
    val imageUrls: List<String>,
    val detailImageUrls: List<Any>,
){

    fun updateStockStatus() {
        this.stockStatus = determineStockStatus()
    }

    /**
     * 할인가격 계산
     */
    fun calculateDiscountPrice(): Long {
        return (this.price * (100 - this.discountRate) / 100).coerceAtLeast(0)
    }

    /**
     * 부품 정렬 함수
     * 1. Parts type order 번호가 가장 빠른 순
     * 2. isOption 이 false 인 순
     */
    fun sortByParts() {
        this.parts.sortWith(
            compareBy<Parts> { it.partsType.order }
                .thenBy { it.isOption }
        )
    }

    /**
     * 부품 단종, 재고없음이 많은 순으로 상품 상태 변경 함수
     */
    private fun determineStockStatus(): StockStatus {
        val statusCount = parts.groupingBy { it.stockStatus }.eachCount()

        val discontinuedCount = statusCount[StockStatus.DISCONTINUED] ?: 0
        val outOfStockCount = statusCount[StockStatus.OUT_OF_STOCK] ?: 0
        val inStockCount = statusCount[StockStatus.IN_STOCK] ?: 0

        val mostFrequentStatus = listOf(
            StockStatus.DISCONTINUED to discontinuedCount,
            StockStatus.OUT_OF_STOCK to outOfStockCount,
            StockStatus.IN_STOCK to inStockCount
        ).maxByOrNull { it.second }?.first ?: StockStatus.IN_STOCK

        return if (discontinuedCount == outOfStockCount) {
            StockStatus.OUT_OF_STOCK
        } else {
            mostFrequentStatus
        }
    }

}
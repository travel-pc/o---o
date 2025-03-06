package io.travia.product

import io.travia.ProductType
import io.travia.StockStatus

data class Product(
    val productId: Long,
    val title: String,
    val discountRate: Long,
    val price: Long = 0L,
    val productType: ProductType,
    val manufacturer: String,
    val parts: List<Parts>,
    val stockStatus: StockStatus = StockStatus.IN_STOCK,
    val shippingMethods: List<ShippingMethod>,
    val imageUrls: List<String>,
    val detailImageUrls: List<Any>,
){

}
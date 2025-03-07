package io.travia.core.api.controller.product.response

import io.travia.PartsType
import io.travia.ProductType
import io.travia.StockStatus

data class ReadProductDetailResponse (
    val productId: Long,
    val title: String,
    val discountRate: Long,
    val price: Long = 0L,
    val discountPrice: Long = 0L,
    val productType: ProductType,
    val manufacturer: String,
    var parts: MutableList<ReadPartsResponse>,
    var stockStatus: StockStatus = StockStatus.IN_STOCK,
    val shippingMethods: MutableList<ReadShippingMethodResponse>,
    val imageUrls: MutableList<String>,
    val detailImageUrls: MutableList<String>,
)

data class ReadPartsResponse(
    val partsId: Long,
    val name: String,
    val price: Long,
    val manufacturer: String,
    val spec: String,
    val isOption: Boolean,
    val partsType: PartsType,
    val imageUrl: String,
    val stockStatus: StockStatus,
)

data class ReadShippingMethodResponse(
    val shippingMethodId: Long,
    val name: String,
    val additionalCost: Long,
    val isFree: Boolean,
)
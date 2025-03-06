package io.travia.core.api.controller.product

import io.travia.core.api.controller.product.response.ReadPartsResponse
import io.travia.core.api.controller.product.response.ReadProductDetailResponse
import io.travia.core.api.controller.product.response.ReadShippingMethodResponse
import io.travia.core.support.response.ApiResponse
import io.travia.product.Product
import io.travia.product.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1")
class ProductController(
    private val productService: ProductService
) {

    @GetMapping("/products/{productId}/details")
    fun read(@PathVariable productId: Long): ResponseEntity<ApiResponse<ReadProductDetailResponse>> {
        val product = productService.read(productId)

        val response = ReadProductDetailResponse(
            productId = product.productId,
            title = product.title,
            price = product.price,
            productType = product.productType,
            manufacturer = product.manufacturer,
            discountRate = product.discountRate,
            imageUrls = product.imageUrls,
            detailImageUrls = product.detailImageUrls,
            parts = product.parts.map {
                ReadPartsResponse(
                    partsId = it.partsId,
                    name = it.name,
                    price = it.price,
                    manufacturer = it.manufacturer,
                    spec = it.spec,
                    isOption = it.isOption,
                    partsType = it.partsType,
                    imageUrl = it.imageUrl
                )
            }.toMutableList(),
            shippingMethods = product.shippingMethods.map {
                ReadShippingMethodResponse(
                    shippingMethodId = it.shippingMethodId,
                    additionalCost = it.additionalCost,
                    name = it.name,
                    isFree = it.isFree
                )
            }.toMutableList()
        )

        return ResponseEntity.ok().body(ApiResponse.success(response))
    }
}
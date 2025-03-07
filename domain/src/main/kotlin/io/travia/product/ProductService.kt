package io.travia.product

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


@Component
@Transactional(readOnly = true)
class ProductService (
    val productRepository: ProductRepository,
){
    fun read(productId: Long): Product {
        return productRepository.read(productId).apply {
            decideStockStatus()
            calculateDiscountPrice()
            sortByParts()
        }
    }
}
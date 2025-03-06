package io.travia.product

import org.springframework.stereotype.Component


@Component
class ProductReader(
    private val productRepository: ProductRepository
) {

    fun read(productId: Long): Product {
        return productRepository.read(productId)
    }

}
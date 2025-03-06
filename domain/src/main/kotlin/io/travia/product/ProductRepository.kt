package io.travia.product

interface ProductRepository {
    fun read(productId: Long): Product
}
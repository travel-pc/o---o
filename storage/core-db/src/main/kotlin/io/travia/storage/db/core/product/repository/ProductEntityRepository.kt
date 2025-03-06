package io.travia.storage.db.core.product.repository

import io.travia.product.Parts
import io.travia.product.Product
import io.travia.product.ProductRepository
import io.travia.product.ShippingMethod
import org.springframework.stereotype.Repository


@Repository
class ProductEntityRepository(

    private val queryDslProductEntityRepository: QueryDslProductEntityRepository,
    private val queryDslShippingMethodEntityRepository: QueryDslShippingMethodEntityRepository,
    private val queryDslProductPartsEntityRepository: QueryDslProductPartsEntityRepository,
    private val queryDslProductImageEntityRepository: QueryDslProductImageEntityRepository,

) : ProductRepository {


    override fun read(productId: Long): Product {
        val productEntity = queryDslProductEntityRepository.findById(productId)
        val shippingEntity = queryDslShippingMethodEntityRepository.findByProductId(productId)
        val partsEntity = queryDslProductPartsEntityRepository.findByProductId(productId)
        val productImageEntity = queryDslProductImageEntityRepository.findByProductId(productId)
        val productDetailImageEntity = queryDslProductImageEntityRepository.findByProductId(productId)

        return Product(
            productId = productId,
            title = productEntity.title,
            discountRate = productEntity.discountRate,
            manufacturer = productEntity.manufacturer,
            productType = productEntity.productType,

            parts = partsEntity.map {
                Parts(
                    partsId = it.partsEntity.partsId,
                    name = it.partsEntity.partsName,
                    price = it.partsEntity.price,
                    manufacturer = it.partsEntity.manufacturer,
                    spec = it.partsEntity.spec,
                    isOption = it.isOption,
                    imageUrl = it.partsEntity.imageUrl,
                    stockStatus = it.partsEntity.stockStatus,
                    partsType = it.partsEntity.partsType
                )
            }.toMutableList(),

            imageUrls = productImageEntity.map {
                it.imageUrl
            },

            shippingMethods = shippingEntity.map {
                ShippingMethod(
                    shippingMethodId = it.shippingMethodId,
                    name = it.shippingType.description,
                    additionalCost = it.additionalCost,
                    isFree = it.isFree
                )
            },

            detailImageUrls = productDetailImageEntity.map {
                it.imageUrl
            }

        )
    }


}
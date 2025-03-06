package io.travia.product



data class ShippingMethod (
    val shippingMethodId: Long,
    val name: String,
    val additionalCost: Long,
    val isFree: Boolean,
){

}
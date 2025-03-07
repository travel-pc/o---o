package io.travia.product

import io.travia.PartsType
import io.travia.StockStatus

data class Parts (
    val partsId: Long,
    val name: String,
    val price: Long,
    val manufacturer: String,
    val spec: String,
    val isOption: Boolean,
    val partsType: PartsType,
    val imageUrl: String,
    val stockStatus: StockStatus,
){

}
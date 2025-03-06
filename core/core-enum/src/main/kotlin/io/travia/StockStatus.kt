package io.travia

enum class StockStatus(
    val description: String,
) {
    IN_STOCK("재고 있음"),
    OUT_OF_STOCK("재고 없음"),
    DISCONTINUED("단종")
}
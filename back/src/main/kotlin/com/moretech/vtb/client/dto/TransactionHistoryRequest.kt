package com.moretech.vtb.client.dto

data class TransactionHistoryRequest(
    val page: Int,
    val offset: Int,
    val sort: Sort
)

enum class Sort() {
    asc, desc
}

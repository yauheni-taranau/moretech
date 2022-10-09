package com.moretech.vtb.controller.dto

data class NewsDto(
        var id: Long?,
        var title: String,
        var description: String,
        var likeCount: Int?
)
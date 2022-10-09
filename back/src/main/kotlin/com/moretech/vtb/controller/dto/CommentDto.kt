package com.moretech.vtb.controller.dto

data class CommentDto(
        var id: Long,
        var username: String,
        var text: String,
        var isLiked: Boolean,
        var likeCount: Long
)
package com.moretech.vtb.repository

import com.moretech.vtb.entity.news.CommentLike
import org.springframework.data.repository.CrudRepository

interface CommentLikeRepo : CrudRepository<CommentLike, Long> {
}
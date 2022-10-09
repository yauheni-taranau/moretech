package com.moretech.vtb.repository

import com.moretech.vtb.entity.news.Comment
import org.springframework.data.repository.CrudRepository

interface CommentRepo : CrudRepository<Comment, Long> {

    fun findAllByNewsId(newsId: Long): List<Comment>
}
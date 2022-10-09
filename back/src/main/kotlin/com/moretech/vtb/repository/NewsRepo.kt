package com.moretech.vtb.repository

import com.moretech.vtb.entity.news.News
import org.springframework.data.repository.CrudRepository

interface NewsRepo : CrudRepository<News, Long> {

    fun findAllByOrderByDateCreatedDesc(): List<News>

    fun findAllByOrderByLikeCountDesc(): List<News>
}
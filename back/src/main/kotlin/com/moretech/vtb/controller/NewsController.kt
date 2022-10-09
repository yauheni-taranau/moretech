package com.moretech.vtb.controller

import com.moretech.vtb.controller.AuthUtils.AUTH_HEADER
import com.moretech.vtb.controller.dto.CreateCommentDto
import com.moretech.vtb.controller.dto.NewsDto
import com.moretech.vtb.controller.dto.NewsSortDto
import com.moretech.vtb.service.news.NewsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/news")
class NewsController(
        private val newsService: NewsService
) {

    @PostMapping
    fun createNews(@RequestBody newsDto: NewsDto, @RequestHeader(name = AUTH_HEADER) token: String): NewsDto {
        return newsService.createNews(newsDto, token)
    }

    @GetMapping("/{id}")
    fun getNews(@PathVariable id: Long): NewsDto {
        return newsService.getNewsById(id)
    }

    @PostMapping("/{id}/like")
    fun like(@PathVariable id: Long) = newsService.like(id)

    @PostMapping("/{id}/comments")
    fun postComment(
            @RequestBody createCommentDto: CreateCommentDto,
            @PathVariable id: Long,
            @RequestHeader(name = AUTH_HEADER) token: String) = newsService.postComment(createCommentDto, id, token)
    @PostMapping("/{id}/comments/{commentId}/like")
    fun likeComment(
            @PathVariable id: Long,
            @PathVariable commentId: Long,
            @RequestHeader(name = AUTH_HEADER) token: String) = newsService.likeComment(id, commentId, token)


    @GetMapping("/{id}/comments")
    fun getAllComments(
            @PathVariable id: Long,
            @RequestHeader(name = AUTH_HEADER) token: String) = newsService.getAllComment(id, token)

    @GetMapping
    fun getNews(newsSortDto: NewsSortDto): List<NewsDto> {
        return newsService.getNews(newsSortDto)
    }
}
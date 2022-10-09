package com.moretech.vtb.service.news

import com.moretech.vtb.controller.dto.CommentDto
import com.moretech.vtb.controller.dto.CreateCommentDto
import com.moretech.vtb.controller.dto.NewsDto
import com.moretech.vtb.controller.dto.NewsSortDto
import com.moretech.vtb.entity.User
import com.moretech.vtb.entity.news.Comment
import com.moretech.vtb.entity.news.CommentLike
import com.moretech.vtb.entity.news.News
import com.moretech.vtb.repository.CommentLikeRepo
import com.moretech.vtb.repository.CommentRepo
import com.moretech.vtb.repository.NewsRepo
import com.moretech.vtb.service.UserService
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class NewsService(
        private val newsRepo: NewsRepo,
        private val userService: UserService,
        private val commentRepo: CommentRepo,
        private val commentLikeRepo: CommentLikeRepo
) {

    fun createNews(newsDto: NewsDto, token: String): NewsDto {
        val user = userService.decryptUser(token)
        val saved = newsRepo.save(News(
                title = newsDto.title,
                description = newsDto.description,
                author = user,
                dateCreated = LocalDateTime.now()
        ))
        return toDto(saved)
    }

    private fun toDto(saved: News) = NewsDto(
            saved.id,
            saved.title,
            saved.description,
            saved.likeCount
    )

    fun like(id: Long): NewsDto {
        val news = newsRepo.findById(id).get()
        news.likeCount++
        return toDto(newsRepo.save(news))
    }

    fun getNews(newsSortDto: NewsSortDto): List<NewsDto> {
        return if (newsSortDto.byLikes != null) {
            newsRepo.findAllByOrderByLikeCountDesc().map { el -> toDto(el) }
        } else if (newsSortDto.byDate != null) {
            newsRepo.findAllByOrderByDateCreatedDesc().map { el -> toDto(el) }
        } else {
            newsRepo.findAllByOrderByDateCreatedDesc().map { el -> toDto(el) }
        }
    }

    fun postComment(createCommentDto: CreateCommentDto, id: Long, token: String): CommentDto {
        val news = newsRepo.findById(id).get()
        val user = userService.decryptUser(token)
        return toCommentDto(commentRepo.save(Comment(
                user = user,
                text = createCommentDto.text,
                news = news
        )), user)
    }

    fun getAllComment(id: Long, token: String): List<CommentDto> {
        val user = userService.decryptUser(token)
        return commentRepo.findAllByNewsId(id)
                .map { toCommentDto(it, user) }
                .sortedByDescending { it.likeCount }
    }

    private fun toCommentDto(comment: Comment, user: User): CommentDto {
        return CommentDto(
                comment.id!!,
                comment.user.username,
                comment.text,
                comment.likes.any { like: CommentLike -> like.user.username == user.username },
                comment.likes.size.toLong()
        )
    }

    fun likeComment(id: Long, commentId: Long, token: String): CommentDto {
        val user = userService.decryptUser(token)
        val comment = commentRepo.findById(commentId).get()
        commentLikeRepo.save(CommentLike(
                user = user,
                comment = comment
        ))
        return toCommentDto(comment, user)
    }

    fun getNewsById(id: Long): NewsDto {
        return toDto(newsRepo.findById(id).get())
    }
}
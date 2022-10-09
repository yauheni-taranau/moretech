package com.moretech.vtb.entity.news

import com.moretech.vtb.entity.User
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class News(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,

        var title: String,
        var description: String,

        @ManyToOne
        @JoinColumn(name="author_id", nullable=false)
        var author: User,

        var dateCreated: LocalDateTime,

        var likeCount: Int = 0
)
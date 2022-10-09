package com.moretech.vtb.entity.news

import com.moretech.vtb.entity.User
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Entity
data class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @OneToOne
    @JoinColumn(name = "user_id")
    var user: User,

    var text: String,

    @ManyToOne
    @JoinColumn(name = "news_id")
    var news: News,

    @OneToMany(mappedBy = "comment")
    var likes: List<CommentLike> = ArrayList()
)
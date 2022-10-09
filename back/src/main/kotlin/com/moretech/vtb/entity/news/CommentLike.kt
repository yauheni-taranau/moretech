package com.moretech.vtb.entity.news

import com.moretech.vtb.entity.User
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

@Entity
data class CommentLike(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @OneToOne
    @JoinColumn(name = "like_by")
    var user: User,

    @ManyToOne
    @JoinColumn(name = "comment_id")
    var comment: Comment
)
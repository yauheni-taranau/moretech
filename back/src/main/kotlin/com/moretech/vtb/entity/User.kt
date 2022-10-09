package com.moretech.vtb.entity

import com.moretech.vtb.entity.courses.Course
import com.moretech.vtb.entity.news.News
import com.moretech.vtb.entity.news.UserCourses
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,

        var name: String,
        var surname: String,
        var username: String,
        var password: String,

        @OneToMany(fetch = FetchType.LAZY)
        val news: List<News> = ArrayList(),

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "unit_id")
        var unit: Unit? = null,

        @ElementCollection(targetClass = Role::class)
        @JoinTable(name = "user_roles", joinColumns = [JoinColumn(name = "userId")])
        @Column(name = "role", nullable = false)
        @Enumerated(EnumType.STRING)
        var roles: MutableList<Role> = ArrayList(),

        @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
        @JoinColumn(name = "nft_wallet_id")
        var nftWallet: NFTWallet? = null,

        @OneToMany
        var courses: MutableList<UserCourses> = ArrayList(),

        var exp: Long = 0
)
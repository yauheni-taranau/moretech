package com.moretech.vtb.entity.courses

import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne

@Entity
data class Course(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,

        var title: String,
        var description: String,
        var rewardAmount: BigDecimal,
        @OneToOne
        @JoinColumn(name = "test_id")
        var test: Test
)
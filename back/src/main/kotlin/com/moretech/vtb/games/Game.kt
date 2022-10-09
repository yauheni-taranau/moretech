package com.moretech.vtb.games

import com.moretech.vtb.entity.User
import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "tic_tac_toe")
data class Game(

    @Id
    @GeneratedValue
    var id: Long? = null,

    var bid: BigDecimal = BigDecimal.ZERO,
    var name: String,

    @ManyToOne
    @JoinColumn(name = "firstPlayerId")
    var firstPlayer: User,

    @ManyToOne
    @JoinColumn(name = "secondPlayerId")
    var secondPlayer: User? = null
)

package com.moretech.vtb.config.property

import org.springframework.boot.context.properties.ConfigurationProperties
import java.math.BigDecimal

@ConfigurationProperties(prefix = "reward")
data class RewardProperties(
    val mostLikedComments: Map<Int, BigDecimal>,
    val mostTTTWins: Map<Int, BigDecimal>,
    val mostLikedNews: Map<Int, BigDecimal>
)
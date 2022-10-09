package com.moretech.vtb

import com.moretech.vtb.config.property.RewardProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import reactivefeign.spring.config.EnableReactiveFeignClients

@SpringBootApplication
@EnableReactiveFeignClients
@EnableScheduling
@EnableConfigurationProperties(value = [RewardProperties::class])
class NftGamesApplication

fun main(args: Array<String>) {
	runApplication<NftGamesApplication>(*args)
}

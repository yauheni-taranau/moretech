package com.moretech.vtb.repository

import com.moretech.vtb.games.Game
import org.springframework.data.repository.CrudRepository

interface GameRepo : CrudRepository<Game, Long> {
}
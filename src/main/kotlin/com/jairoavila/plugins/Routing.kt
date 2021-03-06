package com.jairoavila.plugins

import com.jairoavila.routes.getAllHeroes
import com.jairoavila.routes.root
import com.jairoavila.routes.searchHeroes
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.routing.*

fun Application.configureRouting() {

    routing {
        root()
        static("/images") { resources("images") }
        getAllHeroes()
        searchHeroes()
    }
}

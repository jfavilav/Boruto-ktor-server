package com.jairoavila.plugins

import com.jairoavila.routes.root
import io.ktor.application.*
import io.ktor.routing.*

fun Application.configureRouting() {

    routing {
        root()
    }
}

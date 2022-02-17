package com.jairoavila

import io.ktor.application.*
import com.jairoavila.plugins.*
import com.jairoavila.plugins.di.configureDefaultHeader
import com.jairoavila.plugins.di.configureKoin

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureKoin()
    configureRouting()
    configureSerialization()
    configureMonitoring()
    configureDefaultHeader()
}

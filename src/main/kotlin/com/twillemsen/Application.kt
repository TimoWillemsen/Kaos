package com.twillemsen

import com.twillemsen.plugins.configureMonitoring
import com.twillemsen.plugins.configureRouting
import io.ktor.server.application.Application

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    configureMonitoring()
    configureRouting()
}

package com.twillemsen.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlin.random.Random

fun Application.configureRouting() {

    var reliability = environment.config.propertyOrNull("kaos.reliability")?.getString()?.toDoubleOrNull() ?: 100.0

    routing {
        get("/") {

            val reliabilityChance = Random.nextDouble() * 100
            val isSuccess = reliabilityChance <= reliability
            val statusCode = if (isSuccess) HttpStatusCode.OK else HttpStatusCode.InternalServerError
            call.respondText("reliability: $reliability, reliabilityChance: $reliabilityChance", status = statusCode)
        }
        get("/set-reliability/{reliability}") {
            val s = call.parameters["reliability"]?.toDoubleOrNull()
            if (s == null) {
                call.respond(HttpStatusCode.BadRequest)
            } else {
                reliability = s
                call.respond(HttpStatusCode.Accepted)
            }
        }
    }
}

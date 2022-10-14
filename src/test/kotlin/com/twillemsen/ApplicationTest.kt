package com.twillemsen

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.plugins.*
import io.micrometer.prometheus.*
import io.ktor.server.metrics.micrometer.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlin.test.*
import io.ktor.server.testing.*
import com.twillemsen.plugins.*
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.config.MapApplicationConfig

class ApplicationTest {
    @Test
    fun `Full smoke test`() = testApplication {
        application {
            configureRouting()
        }
        environment {
            config = MapApplicationConfig("kaos.reliability" to "100")
        }
        client.get("/reliability").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
        client.post("/reliability/0").apply {
            assertEquals(HttpStatusCode.Accepted, status)
        }
        client.get("/reliability").apply {
            assertEquals(HttpStatusCode.InternalServerError, status)
        }
        client.post("/reliability/100").apply {
            assertEquals(HttpStatusCode.Accepted, status)
        }
        client.get("/reliability").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }
}
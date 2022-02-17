package com.jairoavila.routes

import com.jairoavila.models.ApiResponse
import com.jairoavila.module
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.test.assertEquals

class SearchHeroesTest {

    @Test
    fun `access all heroes endpoint, assert correct information`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/boruto/heroes/search?name=sas").apply {
                // When
                val sut = Json.decodeFromString<ApiResponse>(response.content.toString()).heroes.size

                // Then
                assertEquals(
                    expected = HttpStatusCode.OK,
                    actual = response.status()
                )
                assertEquals(
                    expected = 1,
                    actual = sut
                )
            }
        }
    }

    @Test
    fun `access all heroes endpoint, assert multiple heroes result`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/boruto/heroes/search?name=sa").apply {
                // When
                val sut = Json.decodeFromString<ApiResponse>(response.content.toString()).heroes.size

                // Then
                assertEquals(
                    expected = HttpStatusCode.OK,
                    actual = response.status()
                )
                assertEquals(
                    expected = 3,
                    actual = sut
                )
            }
        }
    }

    @Test
    fun `access all heroes endpoint, query empty, assert empty list as a result`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/boruto/heroes/search?name=").apply {
                // When
                val sut = Json.decodeFromString<ApiResponse>(response.content.toString()).heroes

                // Then
                assertEquals(
                    expected = HttpStatusCode.OK,
                    actual = response.status()
                )
                assertEquals(
                    expected = emptyList(),
                    actual = sut
                )
            }
        }
    }

    @Test
    fun `access all heroes endpoint, query non exiting, assert empty list as a result`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/boruto/heroes/search?name=unknown").apply {
                // When
                val sut = Json.decodeFromString<ApiResponse>(response.content.toString()).heroes

                // Then
                assertEquals(
                    expected = HttpStatusCode.OK,
                    actual = response.status()
                )
                assertEquals(
                    expected = emptyList(),
                    actual = sut
                )
            }
        }
    }
}
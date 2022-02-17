package com.jairoavila.routes

import com.jairoavila.models.ApiResponse
import com.jairoavila.module
import com.jairoavila.repository.HeroRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test
import org.koin.java.KoinJavaComponent.inject
import kotlin.test.assertEquals

class AllHeroesTest {

    private val heroRepository: HeroRepository by inject(HeroRepository::class.java)

    @Test
    fun `access all heroes endpoint, assert correct information`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/boruto/heroes").apply {
                // Given
                val expected = ApiResponse(
                    success = true,
                    message = "OK",
                    prevPage = null,
                    nextPage = 2,
                    heroes = heroRepository.page1
                )

                // When
                val sut = Json.decodeFromString<ApiResponse>(response.content.toString())

                // Then
                assertEquals(
                    expected = HttpStatusCode.OK,
                    actual = response.status()
                )
                assertEquals(
                    expected = expected,
                    actual = sut
                )
            }
        }
    }

    @Test
    fun `access all heroes endpoint, query all pages, assert correct information`() {
        withTestApplication(moduleFunction = Application::module) {
            val pages = 1..5
            val heroes = listOf(
                heroRepository.page1,
                heroRepository.page2,
                heroRepository.page3,
                heroRepository.page4,
                heroRepository.page5,
            )
            pages.forEach { page ->
                handleRequest(HttpMethod.Get, "/boruto/heroes?page=$page").apply {
                    // Given
                    val expected = ApiResponse(
                        success = true,
                        message = "OK",
                        prevPage = calculatePage(page = page)[PREVIOUS_PAGE_KEY],
                        nextPage = calculatePage(page = page)[NEXT_PAGE_KEY],
                        heroes = heroes[page - 1]
                    )

                    // When
                    val sut = Json.decodeFromString<ApiResponse>(response.content.toString())

                    // Then
                    assertEquals(
                        expected = HttpStatusCode.OK,
                        actual = response.status()
                    )
                    assertEquals(
                        expected = expected,
                        actual = sut
                    )
                }
            }
        }
    }

    private fun calculatePage(page: Int): Map<String, Int?> {
        var prevPage: Int? = page
        var nextPage: Int? = page

        if (page in 1..4) nextPage = nextPage?.plus(1)
        if (page in 2..5) prevPage = prevPage?.minus(1)
        if (page == 1) prevPage = null
        if (page == 5) nextPage = null

        return mapOf(PREVIOUS_PAGE_KEY to prevPage, NEXT_PAGE_KEY to nextPage)
    }

    companion object {
        private const val PREVIOUS_PAGE_KEY = "prevPage"
        private const val NEXT_PAGE_KEY = "nextPage"
    }
}

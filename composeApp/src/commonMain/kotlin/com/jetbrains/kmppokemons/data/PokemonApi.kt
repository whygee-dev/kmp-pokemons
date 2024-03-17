package com.jetbrains.kmppokemons.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

interface PokemonApi {
    suspend fun getPokemonsList(page: Int): PokemonsList
    suspend fun getPokemons(page: Int): List<Pokemon>
}

class KtorPokemonApi(private val client: HttpClient) : PokemonApi {
    companion object {
        private const val API_URL =
            "https://pokeapi.co/api/v2/pokemon"

        private const val PAGE_SIZE = 5
    }

    override suspend fun getPokemonsList(page: Int): PokemonsList {
        val offset = page * PAGE_SIZE

        return client.get("$API_URL?limit=$PAGE_SIZE&offset=$offset").body()
    }

    private suspend fun getPokemon(url: String): Pokemon {
        return client.get(url).body()
    }

    override suspend fun getPokemons(page: Int): List<Pokemon> {
        return coroutineScope {
            getPokemonsList(page).results.map {
             async { getPokemon(it.url) }
            }.awaitAll()
        }
    }
}

package com.jetbrains.kmppokemons.di

import com.jetbrains.kmppokemons.data.PokemonStore
import com.jetbrains.kmppokemons.data.KtorPokemonApi
import com.jetbrains.kmppokemons.data.PokemonApi
import com.jetbrains.kmppokemons.data.PokemonRepository
import com.jetbrains.kmppokemons.data.PokemonStorage
import com.jetbrains.kmppokemons.screens.list.ListScreenModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModule = module {
    single {
        val json = Json { ignoreUnknownKeys = true }
        HttpClient {
            install(ContentNegotiation) {
                json(json, contentType = ContentType.Any)
            }
        }
    }

    single<PokemonApi> { KtorPokemonApi(get()) }
    single<PokemonStorage> { PokemonStore() }
    single {
        PokemonRepository(get(), get()).apply {
            initialize()
        }
    }
}

val screenModelsModule = module {
    factoryOf(::ListScreenModel)
}

fun initKoin() {
    startKoin {
        modules(
            dataModule,
            screenModelsModule,
        )
    }
}

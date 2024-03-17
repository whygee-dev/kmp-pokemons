package com.jetbrains.kmppokemons.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PokemonRepository(
    private val pokemonApi: PokemonApi,
    private val pokemonStorage: PokemonStorage,
) {
    private val scope = CoroutineScope(SupervisorJob())

    fun initialize() {
        scope.launch {
            initState()
        }
    }

    private suspend fun initState() {
        pokemonStorage.setPokemons(pokemonApi.getPokemons(0))
    }

    suspend fun paginate(page: Int) {
        pokemonStorage.appendPokemons(pokemonApi.getPokemons(page))
    }

    fun getPokemons(): Flow<List<Pokemon>> {
        return pokemonStorage.getPokemons()
    }
}

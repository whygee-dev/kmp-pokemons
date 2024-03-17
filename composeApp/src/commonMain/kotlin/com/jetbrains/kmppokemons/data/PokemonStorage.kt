package com.jetbrains.kmppokemons.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface PokemonStorage {
    fun setPokemons(pokemons: List<Pokemon>)
    fun appendPokemons(pokemons: List<Pokemon>)
    fun getPokemons(): Flow<List<Pokemon>>
}

class PokemonStore : PokemonStorage {
    private val _pokemons = MutableStateFlow(emptyList<Pokemon>())

    override fun setPokemons(pokemons: List<Pokemon>) {
        _pokemons.value = pokemons
    }

    override fun appendPokemons(pokemons: List<Pokemon>) {
        _pokemons.value += pokemons
    }

    override fun getPokemons(): Flow<List<Pokemon>> = _pokemons
}

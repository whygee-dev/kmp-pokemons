package com.jetbrains.kmppokemons.screens.list

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.jetbrains.kmppokemons.data.Pokemon
import com.jetbrains.kmppokemons.data.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ListScreenModel(private val pokemonRepository: PokemonRepository) : ScreenModel {
    val pokemons: StateFlow<List<Pokemon>> =
        pokemonRepository.getPokemons()
            .stateIn(screenModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val page = MutableStateFlow(0)

    suspend fun paginate(page: Int) {
        return pokemonRepository.paginate(page)
    }

}

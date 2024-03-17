package com.jetbrains.kmppokemons.data

import kotlinx.serialization.Serializable

@Serializable
data class PokemonsList (
    val results: List<PokemonListItem>
)

@Serializable
data class PokemonListItem (
    val name: String,
    val url: String
)

@Serializable
data class Pokemon (
    val name: String,
    val sprites: PokemonSprites
)

@Serializable
data class PokemonSprites (
    val other: PokemonSpritesOther
)

@Serializable
data class PokemonSpritesOther (
    val dream_world: PokemonSpritesOtherDreamWorld
)

@Serializable
data class PokemonSpritesOtherDreamWorld (
    val front_default: String
)
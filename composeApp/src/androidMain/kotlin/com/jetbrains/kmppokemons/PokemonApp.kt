package com.jetbrains.kmppokemons

import android.app.Application
import com.jetbrains.kmppokemons.di.initKoin

class PokemonApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}

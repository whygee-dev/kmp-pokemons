package com.jetbrains.kmppokemons.screens.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.jetbrains.kmppokemons.data.Pokemon
import com.jetbrains.kmppokemons.screens.EmptyScreenContent
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.launch

data object ListScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel: ListScreenModel = getScreenModel()
        val pokemons by screenModel.pokemons.collectAsState()
        val coroutineScope = rememberCoroutineScope()

        AnimatedContent(pokemons.isNotEmpty()) { objectsAvailable ->
            if (objectsAvailable) {
                PokemonList(
                    pokemons = pokemons,
                    onReachEnd = {
                        coroutineScope.launch {
                            screenModel.paginate(++screenModel.page.value)
                        }
                    }
                )
            } else {
                EmptyScreenContent(Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun PokemonList(
    pokemons: List<Pokemon>,
    onReachEnd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier
            = modifier
                .fillMaxSize()
                .background(Color.Black),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(pokemons, key = { it.name }) { obj ->
            ObjectFrame(
                obj = obj,
            )
        }
        item {
            Spacer(Modifier.height(50.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.height(200.dp)
                )
            }
        }
        item {
            LaunchedEffect(true) {
                onReachEnd()
            }
        }
    }
}

@Composable
private fun ObjectFrame(
    obj: Pokemon,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .padding(28.dp),
        verticalArrangement = Arrangement.Center
    ) {
        KamelImage(
            resource = asyncPainterResource(data = obj.sprites.other.dream_world.front_default),
            contentDescription = obj.name,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )


        Spacer(Modifier.height(8.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                obj.name,
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 30.sp
                ),
            )
        }
    }
}

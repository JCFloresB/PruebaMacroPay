package com.juan.carlos.flores.bastida.movies.pruebamacropay.presentation.movies.moviedetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.juan.carlos.flores.bastida.movies.pruebamacropay.BuildConfig
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.dto.moviedetail.GetMovieDetailResponse
import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.data.Result

@Composable
fun MovieDetailScreen(
    snackbarHostState: SnackbarHostState
) {
    val viewModel = hiltViewModel<MovieDetailViewModel>()
    val movieDetail by viewModel.movieDetail.collectAsStateWithLifecycle()

    if (movieDetail is Result.Error) {
        LaunchedEffect(key1 = snackbarHostState) {
            snackbarHostState.showSnackbar((movieDetail as Result.Error).error ?: "Error")
        }
    }

    PokemonDetailContent(pokemonResponse = movieDetail)
}

@Composable
fun PokemonDetailContent(
    pokemonResponse: Result<GetMovieDetailResponse>,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (pokemonResponse.data != null) {
            val movie = pokemonResponse.data!!

            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Header(movieDetail = movie)
                Spacer(modifier = Modifier.width(20.dp))
                Footer(movieDetail = movie)

            }
        }
        if (pokemonResponse is Result.Loading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun Header(movieDetail: GetMovieDetailResponse) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(BuildConfig.BASE_URL_IMAGE + movieDetail.posterPath ?: "")
            .size(Size.ORIGINAL)
            .build()
    )
    val imageSize = 250.dp

    Box(
        modifier = Modifier
            .size(imageSize)
    ) {
        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is AsyncImagePainter.State.Error -> {
                Text(
                    text = movieDetail.title ?: "No title",
                    fontSize = 50.sp,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }

            else -> {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun Footer(
    movieDetail: GetMovieDetailResponse
) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            movieDetail.title ?: "No title",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "Duración: ${(movieDetail.runtime)} minutos")
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "Fecha de streno: ${movieDetail.releaseDate}")
        Spacer(modifier = Modifier.height(12.dp))
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Generos: ")
            movieDetail.genres.mapIndexed { index, type ->
                Text(type.name)
                if (index < movieDetail.genres.lastIndex) Text("•")
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "Descripción: ${movieDetail.overview}")
    }
}
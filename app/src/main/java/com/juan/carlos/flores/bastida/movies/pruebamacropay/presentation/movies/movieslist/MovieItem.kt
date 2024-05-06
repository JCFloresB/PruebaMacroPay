package com.juan.carlos.flores.bastida.movies.pruebamacropay.presentation.movies.movieslist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.juan.carlos.flores.bastida.movies.pruebamacropay.BuildConfig
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.dto.movies.Movie
import coil.size.Size
import com.juan.carlos.flores.bastida.movies.pruebamacropay.R

@Composable
fun MovieItem(
    movie: Movie,
    onClickItem: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .clickable { onClickItem() }
        .padding(all = 4.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MovieImage(movie = movie)

        Column {
            Text(
                text = movie.title,
                Modifier.padding(4.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Calificación: ${movie.voteAverage ?: "0"}",
                Modifier.padding(4.dp),
                fontSize = 16.sp
            )

        }
    }
}

@Composable
fun MovieImage(movie: Movie) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(BuildConfig.BASE_URL_IMAGE + movie.posterPath)
            .size(Size.ORIGINAL)
            .placeholder(R.drawable.image_placeholder_icon)
            .build()
    )

    Box(
        modifier = Modifier
            .background(Color(0xFF8998BA))
    ) {
        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(26.dp)
                )
            }

            is AsyncImagePainter.State.Error -> {
                Text(
                    text = movie.title,
                    fontSize = 50.sp,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(24.dp)
                )
            }

            else -> {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(2.dp)
                )
            }
        }
    }
}
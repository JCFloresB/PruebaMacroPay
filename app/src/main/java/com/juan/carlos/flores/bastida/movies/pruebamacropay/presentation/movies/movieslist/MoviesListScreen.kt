package com.juan.carlos.flores.bastida.movies.pruebamacropay.presentation.movies.movieslist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.dto.movies.Movie
import timber.log.Timber

@Composable
fun MoviesListScreen(
    navigateToDetail: (Int) -> Unit,
    snackbarHostState: SnackbarHostState,
    navController: NavController,
) {
    val viewModel = hiltViewModel<MoviesListViewModel>()
    val moviePagingItems = viewModel.moviePagingDataFlow.collectAsLazyPagingItems()
// Obtener el BackStackEntry actual
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    // Obtener los argumentos enviados desde la pantalla anterior
    val arguments = navBackStackEntry?.arguments

    // Obtener el valor del argumento "username"
    val name = arguments?.getString("name") ?: "Nobre Genérico"

    Timber.d("Nombre que llega: $name")
    if (moviePagingItems.loadState.refresh is LoadState.Error) {
        LaunchedEffect(key1 = snackbarHostState) {
            snackbarHostState.showSnackbar(
                (moviePagingItems.loadState.refresh as LoadState.Error).error.message ?: ""
            )
        }
    }
    
    MovieListContent(
        moviePagingItems = moviePagingItems,
        navigateToDetail = navigateToDetail,
        name
    )
}

@Composable
fun MovieListContent(
    moviePagingItems: LazyPagingItems<Movie>,
    navigateToDetail: (Int) -> Unit,
    name: String = "Nobre Genérico"
) {

    Box(modifier = Modifier.fillMaxSize()) {
        if (moviePagingItems.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            Column(modifier = Modifier.fillMaxWidth()){
                Text("Bienvenido $name")
                Spacer(modifier = Modifier.height(10.dp))
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(2)
                ) {
                    items(
                        count = moviePagingItems.itemCount,
                        key = moviePagingItems.itemKey() { it.id },
                    ) { index ->
                        val movie = moviePagingItems[index]
                        if (movie != null) {
                            MovieItem(
                                movie,
                                onClickItem = {
                                    Timber.d("Click en item de movie: ${movie.title}")
                                    navigateToDetail(movie.id)
                                },
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }
                    item {
                        if (moviePagingItems.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                        }
                    }
                }
            }
        }
    }
}

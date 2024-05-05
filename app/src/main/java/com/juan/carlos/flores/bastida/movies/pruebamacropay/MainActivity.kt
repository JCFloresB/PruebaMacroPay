package com.juan.carlos.flores.bastida.movies.pruebamacropay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.get
import androidx.navigation.navArgument
import com.juan.carlos.flores.bastida.movies.pruebamacropay.presentation.components.TopBar
import com.juan.carlos.flores.bastida.movies.pruebamacropay.presentation.login.LoginScreen
import com.juan.carlos.flores.bastida.movies.pruebamacropay.presentation.movies.moviedetail.MovieDetailScreen
import com.juan.carlos.flores.bastida.movies.pruebamacropay.presentation.movies.movieslist.MoviesListScreen
import com.juan.carlos.flores.bastida.movies.pruebamacropay.ui.theme.PruebaMacroPayTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("Valor de url: " + BuildConfig.BASE_URL)
        Timber.i("Valor de token: " + BuildConfig.API_READ_TOKEN)
        setContent {
            PruebaMacroPayTheme {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                    topBar = { TopBar(navController = navController) },
                ) { padding ->
                    NavHost(
                        modifier = Modifier.padding(padding),
                        navController = navController,
                        startDestination = "login",
                    ) {
                        composable(
                            route = "login",
                            label = "Login"
                        ) {
                            LoginScreen(
                                navigateToMovies = { name ->
                                    navController.navigate("list_movies/$name")
                                },
                                snackbarHostState = snackbarHostState
                            )
                        }
                        composable(
                            route = "list_movies/{name}",
                            label = "Movies",
                            arguments = listOf(navArgument("name") { type = NavType.StringType })
                        ) {
                            MoviesListScreen(
                                navigateToDetail = { movieId ->
                                    navController.navigate("detail_movie/$movieId")
                                },
                                snackbarHostState = snackbarHostState
                            )
                        }
                        composable(
                            route = "detail_movie/{movieId}",
                            label = "Movie Detail",
                            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
                        ) {
                            MovieDetailScreen(snackbarHostState = snackbarHostState)
                        }
                    }
                }
            }
        }
    }

    fun NavGraphBuilder.composable(
        route: String,
        label: String,
        arguments: List<NamedNavArgument> = emptyList(),
        deepLinks: List<NavDeepLink> = emptyList(),
        content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
    ) {
        addDestination(
            ComposeNavigator.Destination(
                provider[ComposeNavigator::class],
                content
            ).apply {
                this.route = route
                this.label = label // SET LABEL
                arguments.forEach { (argumentName, argument) ->
                    addArgument(argumentName, argument)
                }
                deepLinks.forEach { deepLink ->
                    addDeepLink(deepLink)
                }
            }
        )
    }
}


/*
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PruebaMacroPayTheme {
        Greeting("Android")
    }
}*/

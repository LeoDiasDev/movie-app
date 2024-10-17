package br.com.movieapp.core.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.movieapp.core.presentation.navigation.BottomNavitationBar
import br.com.movieapp.core.presentation.navigation.DetailScreenNav
import br.com.movieapp.core.presentation.navigation.NavigationGraph
import br.com.movieapp.core.presentation.navigation.currentRoute

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController
) {
    Scaffold(
        bottomBar = {
            if(currentRoute(navController = navController) != DetailScreenNav.DetailScreen.route){
                BottomNavitationBar(navController = navController)
            }
        },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)){
                NavigationGraph(navController = navController)
            }
        }
    )
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(navController = rememberNavController())
}
package com.lagniappe.beginningflows

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lagniappe.beginningflows.ui.HelloScreen
import com.lagniappe.beginningflows.ui.RickAndMortyScreen
import com.lagniappe.beginningflows.ui.theme.BeginningFlowsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BeginningFlowsTheme {
                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp),
                    contentWindowInsets = WindowInsets.statusBars
                ) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// TODO: move these out to their own files
@Serializable
object HomeDestination
@Serializable
object RickAndMortyDestination

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = HomeDestination) {
        composable<HomeDestination> {
            HelloScreen(onButtonClick = {
                navController.navigate(RickAndMortyDestination) {
                    popUpTo(HomeDestination) { inclusive = true }
                }
            })
        }
        composable<RickAndMortyDestination> { RickAndMortyScreen() }
        // Add more destinations similarly.
    }

 //   navController.navigate(route = HomeDestination)
    //HelloScreen(modifier)

//    println("Where does this print")
//    runBlocking {
//        flowOf(1, 2, 3).collect { value ->
//            println("$value")
//        }
//        delay(3000)
//        val myFlow = flow<String> {
//            emit("deezNuts")
//        }
//        myFlow.collect{ println("collect $it")}
//
//    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    BeginningFlowsTheme {
//        HelloScreen()
//    }
//}
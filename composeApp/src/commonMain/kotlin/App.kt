import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import di.AppModule
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(
    appModule: AppModule,
) {
    MaterialTheme {
        val viewModel = getViewModel(
            key = "main-screen-vm",
            factory = viewModelFactory {
                MainViewModel(
                    repository = appModule.mainRepository,
                    databaseRepository = appModule.databaseRepository,
                )
            }
        )
        val uiState by viewModel.uiState.collectAsState()
        var greetingText by remember { mutableStateOf("Hello World!") }
        var showImage by remember { mutableStateOf(false) }

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(uiState.time.toString())
            Text(uiState.players.size.toString())
            Button(onClick = {
                viewModel.addPlayer()
            }) {
                Text("add")
            }
            Button(onClick = {
                viewModel.removeAllPlayers()
            }) {
                Text("remove all")
            }
            Button(onClick = {
                greetingText = "Compose: ${Greeting().greet()}"
                showImage = !showImage
            }) {
                Text(greetingText)
            }
            AnimatedVisibility(showImage) {
                Image(
                    painterResource("compose-multiplatform.xml"),
                    null
                )
            }
        }
        LazyColumn {
            items(uiState.players) { player ->
                Row {
                    Text(player.id.toString())
                    Text(player.player_number.toString())
                    Text(player.full_name)
                }
            }
        }
    }
}
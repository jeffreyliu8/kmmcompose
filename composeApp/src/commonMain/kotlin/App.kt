import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import di.AppModule
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import model.QueryItem
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
                    webRepository = appModule.webRepository,
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
            uiState.queryResult?.items?.let {qItems->
                LazyColumn {
                    items(qItems) { qItem ->
                        ComposeQueryResult(qItem)
                    }
                }
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


@Composable
fun ComposeQueryResult(queryItem: QueryItem) {
    Surface(
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            KamelImage(
                contentScale = ContentScale.Fit,
                resource = asyncPainterResource(queryItem.owner.avatarUrl!!),
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(2.dp)),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(16.dp))
            ComposeListItemRightSide(
                title = queryItem.fullName,
                queryItem.description,
                queryItem.stargazersCount
            )
        }
    }
}

@Composable
fun ComposeListItemRightSide(title: String, description: String?, star: Int) {
    Column {
        Row {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp)
                    .wrapContentWidth(Alignment.Start)
            )
            Text(
                text = "★$star",
                maxLines = 1,
                modifier = Modifier
                    .wrapContentWidth(Alignment.End)
            )
        }
        description?.let {
            Divider(
                modifier = Modifier
                    .fillMaxSize()
                    .width(1.dp)
                    .padding(vertical = 4.dp),
                color = Color.Black
            )
            Text(text = description)
        }
    }
}
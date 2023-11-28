import com.example.database.HockeyPlayer
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MainRepository,
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    init {
        updateClock()
        observeFlowFromRepo()
        observePlayersFromDb()
    }

    fun addPlayer() {
        viewModelScope.launch {
            val player = HockeyPlayer(id = 1, player_number = 1, full_name = "Jeffrey Liu")
            databaseRepository.addPlayer(player)
        }
    }

    fun removeAllPlayers() {
        viewModelScope.launch {
            databaseRepository.removeAll()
        }
    }

    private fun observePlayersFromDb() {
        databaseRepository.getPlayers()
            .onEach { results ->
                _uiState.update {
                    it.copy(
                        players = results,
                    )
                }
            }.catch { println("error $it") }
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)
    }

    private fun updateClock() {
        viewModelScope.launch(Dispatchers.Default) {
            while (true) {
                delay(1000)
                val t = _uiState.value.time + 1
                _uiState.update {
                    it.copy(
                        time = t,
                    )
                }
            }
        }
    }

    private fun observeFlowFromRepo() {
        repository.getSomeFlow()
            .onEach { value ->
                _uiState.update {
                    it.copy(
                        fromRepoValue = value,
                    )
                }
            }
            .catch { println("error $it") }
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)
    }
}

data class MainUiState(
    val time: Int = 0,
    val fromRepoValue: Int = 0,
    val players: List<HockeyPlayer> = emptyList(),
)
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
//    private val repository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    init {
        updateClock()
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
}

data class MainUiState(
    val time: Int = 0,
)
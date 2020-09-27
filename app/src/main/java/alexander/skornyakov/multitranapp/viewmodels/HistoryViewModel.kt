package alexander.skornyakov.multitranapp.viewmodels

import alexander.skornyakov.multitranapp.data.HistoryItem
import alexander.skornyakov.multitranapp.data.HistoryRoomDatabase
import alexander.skornyakov.multitranapp.repository.HistoryRepository
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HistoryViewModel
@ViewModelInject constructor(val historyRepository: HistoryRepository) : ViewModel() {

    private val _history = MutableLiveData<List<HistoryItem>>()
    val history: LiveData<List<HistoryItem>>
        get() {
            if (_history.value == null) {
                CoroutineScope(Dispatchers.IO).launch {
                    val historyList = historyRepository.getAll()
                    withContext(Dispatchers.Main) {
                        _history.value = historyList
                    }
                }
            }
            return _history
        }

}
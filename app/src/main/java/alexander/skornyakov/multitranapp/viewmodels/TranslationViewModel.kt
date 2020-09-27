package alexander.skornyakov.multitranapp.viewmodels

import alexander.skornyakov.multitranapp.data.Language
import alexander.skornyakov.multitranapp.data.Word
import alexander.skornyakov.multitranapp.repository.HistoryRepository
import alexander.skornyakov.multitranapp.repository.TranslationRepository
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class TranslationViewModel
@ViewModelInject constructor(val translationRepository: TranslationRepository) : ViewModel() {

    private var translationJob = Job()

    private val _word = MutableLiveData<Word?>()
    val word: LiveData<Word?>
        get() = _word

    var fromLanguage = Language.ENGLISH
    var toLanguage = Language.RUSSIAN

    fun translate(text: String, from: Language, to: Language) {
        translationJob.cancel()
        translationJob = Job()
        CoroutineScope(Dispatchers.IO + translationJob).launch {
            val word = translationRepository.translate(text, from, to)
            withContext(Dispatchers.Main){
                _word.value = word
            }
        }
    }


}
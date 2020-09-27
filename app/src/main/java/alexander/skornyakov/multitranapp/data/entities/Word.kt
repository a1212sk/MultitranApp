package alexander.skornyakov.multitranapp.data.entities

import alexander.skornyakov.multitranapp.data.entities.Language
import alexander.skornyakov.multitranapp.data.entities.Meaning

data class Word(
    val from: Language = Language.RUSSIAN,
    val to: Language = Language.ENGLISH,
    val text: String = "",
    val meanings : List<Meaning>?
)
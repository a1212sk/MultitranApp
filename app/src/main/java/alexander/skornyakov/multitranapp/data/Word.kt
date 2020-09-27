package alexander.skornyakov.multitranapp.data

data class Word(
    val from: Language = Language.RUSSIAN,
    val to: Language = Language.ENGLISH,
    val text: String = "",
    val meanings : List<Meaning>?
)
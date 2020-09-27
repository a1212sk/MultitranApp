package alexander.skornyakov.multitranapp.repository

import alexander.skornyakov.multitranapp.data.entities.Language
import alexander.skornyakov.multitranapp.data.entities.Word
import alexander.skornyakov.multitranapp.helpers.Constants
import alexander.skornyakov.multitranapp.helpers.HtmlHelper
import org.jsoup.Jsoup

class TranslationRepository() {

    suspend fun translate(text: String, from: Language, to: Language): Word? {
        val connectionString = "${Constants.MULTITRAN_URL}" +
                "l1=${from.id}&l2=${to.id}&s=$text"
        val connection = Jsoup.connect(connectionString)
        connection.data("l1", from.id.toString())
        connection.data("l2", to.id.toString())
        connection.data("s", text)
        val doc = connection.get()
        val meanings = HtmlHelper.getMeaningsFromDocument(doc)
        val word = Word(
            from,
            to,
            text,
            meanings
        )
        return word
    }

}
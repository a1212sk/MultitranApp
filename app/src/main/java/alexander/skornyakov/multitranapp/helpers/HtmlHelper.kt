package alexander.skornyakov.multitranapp.helpers

import alexander.skornyakov.multitranapp.data.Meaning
import org.jsoup.nodes.Document

class HtmlHelper {
    companion object {
        fun getMeaningsFromDocument(doc: Document): List<Meaning>? {
            val meanings = mutableListOf<Meaning>()
            var tableRows = doc.select("table[width*=100%] > tbody > tr")
            if (tableRows.size > 2) { //first two are garbage
                var counter = 2
                while (counter < tableRows.size
                    && tableRows[counter].selectFirst("td > b") == null //cut thesaurus and others
                ) {
                    if (tableRows[counter].select("td.trans > a").size > 0) {
                        val subj = tableRows[counter].select("td.subj > a").text()
                        val meaningElements = tableRows[counter].select("td.trans > a")
                        val meaningStr = StringBuilder()
                        meaningElements.forEach {
                            meaningStr.append(it.text()).append("; ")
                        }
                        val meaning = Meaning(meaningStr.toString(), subj)
                        meanings.add(meaning)
                    }
                    counter++
                }
            }

            return meanings
        }

    }
}
package alexander.skornyakov.multitranapp.ui.adapters

import alexander.skornyakov.multitranapp.R
import alexander.skornyakov.multitranapp.data.entities.HistoryItem
import alexander.skornyakov.multitranapp.data.entities.Language
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class HistoryListAdapter(context: Context, items: List<HistoryItem>) :
    ArrayAdapter<HistoryItem>(context, R.layout.item_list_history, items) {

    var itemClickListener : (HistoryItem)->Unit = {}

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var v = convertView
        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.item_list_history, null)
        }
        val item = getItem(position)
        if (item != null) {
            v?.setOnClickListener { itemClickListener(item) }
            v?.findViewById<TextView>(R.id.tvWord)?.text = item.word
            v?.findViewById<TextView>(R.id.tvFrom)?.text =
                Language.values().find { it.id == item.langFrom }?.langName
            v?.findViewById<TextView>(R.id.tvTo)?.text =
                Language.values().find { it.id == item.langTo }?.langName
        }
        v?.let {
            return it
        }
        return super.getView(position, convertView, parent)
    }
}
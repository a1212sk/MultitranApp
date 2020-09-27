package alexander.skornyakov.multitranapp.ui.adapters

import alexander.skornyakov.multitranapp.R
import alexander.skornyakov.multitranapp.data.Meaning
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recyclerview_meaning.view.*

class MeaningsRVAdapter : RecyclerView.Adapter<MeaningsRVAdapter.MeaningViewHolder>() {

    inner class MeaningViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        return MeaningViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_recyclerview_meaning, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun clear(){
        clear()
    }

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
        val meaning = differ.currentList[position]
        holder.itemView.apply {
            this.tvMeaning.text = meaning.text
            this.tvSubj.text = "${position+1}. ${meaning.subject}"
        }
    }

    val diffCallback = object : DiffUtil.ItemCallback<Meaning>() {
        override fun areItemsTheSame(oldItem: Meaning, newItem: Meaning): Boolean {
            return oldItem.text == newItem.text
        }

        override fun areContentsTheSame(oldItem: Meaning, newItem: Meaning): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    val differ = AsyncListDiffer<Meaning>(this, diffCallback)

    fun submitList(list: List<Meaning>) = differ.submitList(list)

}
package alexander.skornyakov.multitranapp.ui.fragments

import alexander.skornyakov.multitranapp.R
import alexander.skornyakov.multitranapp.viewmodels.HistoryViewModel
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_history.*


@AndroidEntryPoint
class HistoryFragment : Fragment(R.layout.fragment_history){

    val historyViewModel: HistoryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1)
        lstHistory.adapter = adapter

        historyViewModel.history.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                for(item in it){
                    adapter.add(item.word)
                }
            }
        })

    }
}
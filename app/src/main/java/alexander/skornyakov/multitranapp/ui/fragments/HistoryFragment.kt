package alexander.skornyakov.multitranapp.ui.fragments

import alexander.skornyakov.multitranapp.R
import alexander.skornyakov.multitranapp.ui.adapters.HistoryListAdapter
import alexander.skornyakov.multitranapp.viewmodels.HistoryViewModel
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_history.*


@AndroidEntryPoint
class HistoryFragment : Fragment(R.layout.fragment_history) {

    val historyViewModel: HistoryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        historyViewModel.history.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                var adapter = HistoryListAdapter(requireContext(), it)
                adapter.itemClickListener = {
                    var action = HistoryFragmentDirections.actionHistoryFragmentToTranslationFragment(
                        it.word,
                        it.langFrom,
                        it.langTo
                    )
                    activity?.findNavController(R.id.navHost)?.navigate(action)
                }
                lstHistory.adapter = adapter
            }
        })

    }
}
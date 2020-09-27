package alexander.skornyakov.multitranapp.ui.fragments

import alexander.skornyakov.multitranapp.R
import alexander.skornyakov.multitranapp.data.Language
import alexander.skornyakov.multitranapp.data.Meaning
import alexander.skornyakov.multitranapp.ui.adapters.MeaningsRVAdapter
import alexander.skornyakov.multitranapp.viewmodels.TranslationViewModel
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_translation.*

@AndroidEntryPoint
class TranslationFragment : Fragment(R.layout.fragment_translation) {

    private val translationViewModel: TranslationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter =
            MeaningsRVAdapter()
        rvMeanings.layoutManager = LinearLayoutManager(requireContext())
        rvMeanings.adapter = adapter

        translationViewModel.word.observe(viewLifecycleOwner, Observer { word ->
            word?.let {
                it.meanings?.let { meanings ->
                    adapter.submitList(meanings)
                }
            }
        })

        langFromSpinner.setSelection(1) //

        teText.doOnTextChanged { text, start, before, count ->
            text?.let {
               translate()
            }
        }

        langFromSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val lang = Language.values().find { it.langName == langFromSpinner.selectedItem.toString() }
                lang?.let {
                    translationViewModel.fromLanguage = it
                    translate()
                }
            }

        }

        langToSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val lang = Language.values().find { it.langName == langToSpinner.selectedItem.toString() }
                lang?.let {
                    translationViewModel.toLanguage = it
                    translate()
                }
            }

        }

    }

    private fun translate(){
        translationViewModel.translate(
            teText.text.toString(),
            translationViewModel.fromLanguage,
            translationViewModel.toLanguage
        )
    }
}
package alexander.skornyakov.multitranapp.ui.fragments

import alexander.skornyakov.multitranapp.R
import alexander.skornyakov.multitranapp.data.entities.HistoryItem
import alexander.skornyakov.multitranapp.data.entities.Language
import alexander.skornyakov.multitranapp.helpers.ConnectionHelper
import alexander.skornyakov.multitranapp.ui.adapters.MeaningsRVAdapter
import alexander.skornyakov.multitranapp.viewmodels.TranslationViewModel
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_translation.*
import java.util.*


@AndroidEntryPoint
class TranslationFragment : Fragment(R.layout.fragment_translation) {

    private val translationViewModel: TranslationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MeaningsRVAdapter()
        rvMeanings.layoutManager = LinearLayoutManager(requireContext())
        rvMeanings.adapter = adapter

        translationViewModel.word.observe(viewLifecycleOwner, Observer { word ->
            word?.let {
                it.meanings?.let { meanings ->
                    adapter.submitList(meanings)
                }
            }
        })

        langFromSpinner.setSelection(1) //rus

        var timer = Timer()
        teText.doOnTextChanged { text, start, before, count ->
            timer.cancel()
            timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    text?.let {
                        translate()
                    }
                }
            }, 500)
        }

        subscribeSpinnersListeners()

        arguments?.let { arguments ->
            val word = arguments.getString("word")
            word?.let {

                teText.setText(word)

                val fromLang = arguments.getInt("langFrom")
                fromLang?.let {
                    val langName = Language.values().find { it.id == fromLang }?.langName
                    val fromPosition = (langFromSpinner.adapter as ArrayAdapter<String>)
                        .getPosition(langName)
                    langFromSpinner.setSelection(fromPosition)
                }

                val toLang = arguments.getInt("langTo")
                toLang?.let {
                    val langName = Language.values().find { it.id == toLang }?.langName
                    val toPosition = (langToSpinner.adapter as ArrayAdapter<String>)
                        .getPosition(langName)
                    langToSpinner.setSelection(toPosition)
                }
            }


        }

    }

    private fun translate() {
        if (!ConnectionHelper.internetAvailable(requireContext())) {
            activity?.runOnUiThread {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.InternetConnectionRequired),
                    Toast.LENGTH_LONG
                ).show()
            }
            return
        }
        tvLang.text = translationViewModel.fromLanguage.code
        val text = teText.text.toString()
        translationViewModel.translate(
            text,
            translationViewModel.fromLanguage,
            translationViewModel.toLanguage
        )
        if (text.isNotEmpty()) {
            val historyItem =
                HistoryItem(
                    0,
                    System.currentTimeMillis(),
                    translationViewModel.fromLanguage.id,
                    translationViewModel.toLanguage.id,
                    teText.text.toString()
                )
            translationViewModel.addHistoryItem(historyItem)
        }
    }

    private fun subscribeSpinnersListeners() {
        langFromSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val lang = Language.values()
                    .find { it.langName == langFromSpinner.selectedItem.toString() }
                lang?.let {
                    translationViewModel.fromLanguage = it
                    tvLang.text = it.code
                    teText.text?.let {
                        if (it.toString().isNotEmpty()) {
                            translate()
                        }
                    }
                }
            }

        }

        langToSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val lang =
                    Language.values().find { it.langName == langToSpinner.selectedItem.toString() }
                lang?.let {
                    translationViewModel.toLanguage = it
                    teText.text?.let {
                        if (it.toString().isNotEmpty()) {
                            translate()
                        }
                    }
                }
            }

        }
    }
}
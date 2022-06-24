package com.windson.vamosrachar2.view

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.windson.vamosrachar2.databinding.FragmentMainBinding
import com.windson.vamosrachar2.viewmodel.MainViewModel

/*
 *      MainFragment
 *      - shows the UI
 *      - listens to viewModel for updates on UI
 */
class MainFragment: Fragment() {

    // View Binding
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    // Create a viewModel
    private val viewModel: MainViewModel by activityViewModels()

    var tts : TextToSpeech? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // This is needed for view binding
        viewModel.fragment = this
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
        setupChangeListeners()
        fragmentTextUpdateObserver()

        binding.share.setOnClickListener(){
            val myIntent = Intent(Intent.ACTION_SEND)
            myIntent.type = "type/palin"
            val shareBody = "Resultado VamosRachar"
            val shareSub = "A divisão de pagamento resultou em: " + viewModel.getUpdatedText() + " para cada pessoa."
            myIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
            myIntent.putExtra(Intent.EXTRA_TEXT, shareSub)
            startActivity(Intent.createChooser(myIntent, "Compartilhe a Conta"))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Setup the button in our fragment to call getUpdatedText method in viewModel
    private fun setupClickListeners() {
        binding.listen.setOnClickListener { viewModel.getUpdatedText() }
    }

    private fun setupChangeListeners(){
        binding.money.addTextChangedListener{ viewModel.updateValue(binding.money,
            binding.peopleCount) }
        binding.peopleCount.addTextChangedListener{ viewModel.updateValue(binding.money,
            binding.peopleCount)
        }
    }

    // Observer is waiting for viewModel to update our UI
    private fun fragmentTextUpdateObserver() {
        viewModel.uiTextLiveData.observe(viewLifecycleOwner) { updatedText ->
            binding.result.text = updatedText
        }
    }
}
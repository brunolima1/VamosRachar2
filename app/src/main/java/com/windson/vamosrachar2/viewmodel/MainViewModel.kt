package com.windson.vamosrachar2.viewmodel

import android.content.Intent
import android.speech.tts.TextToSpeech
import android.widget.EditText
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.windson.vamosrachar2.view.MainFragment
import com.windson.vamosrachar2.model.DataModel
import java.util.*

/*
 *      MainViewModel
 *      - viewModel that updates the MainFragment (the visible UI)
 *      - gets the data from model
 */
class MainViewModel: ViewModel() {
    var fragment : MainFragment? = null
    private val model = DataModel(result = "0.00 $")

    // Create MutableLiveData which MainFragment can subscribe to
    // When this data changes, it triggers the UI to do an update
    val uiTextLiveData = MutableLiveData<String>()

    // Get the updated text from our model and post the value to MainFragment
    fun getUpdatedText() {
        val updatedText = model.result
        uiTextLiveData.postValue(updatedText)
    }

    fun updateValue(money: EditText, people: EditText){
        var valorStr = money.text.toString()
        var peopleStr = people.text.toString()

        var valorT : Double = 0.0
        var pessoasT : Double = 0.0

        var result: String? = null

        if(money.text.isNotEmpty()){
            valorT = valorStr.toDouble()
        }
        if(people.text.isNotEmpty()){
            pessoasT = peopleStr.toDouble()
        }

        if(money.text.isNotEmpty() and people.text.isNotEmpty()){
            val valorF: Double = valorT/pessoasT
            var valorFstr : String = valorF.toString()

            if(valorFstr == "Infinity"){
                valorFstr = "0.0"
            }
            if(valorFstr.length > 4){
                valorFstr = valorFstr.removeRange(5, valorFstr.length)
            }
            if(Locale.getDefault().displayLanguage == "português"){
                result = ("${valorFstr} R$ para cada")
            }
            else{
                result = ("${valorFstr} $ for each")
            }
        }

        if(money.text.isEmpty() or people.text.isEmpty()){
            result = ("R$: 0.00")
            if(Locale.getDefault().displayLanguage == "português"){
                result = ("0.00 R$")
            }
            else{
                result = ("0.00 $")
            }
        }
        uiTextLiveData.postValue(result)
    }
}
package com.example.epsiloncmptestapp

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.conversantmedia.gdprcmp.ConversantCmp

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

private const val TC_STRING = "IABTCF_TCString"

class FirstFragment : Fragment() {
    private var cmp: ConversantCmp? = null
    private var prefs: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Set a generic config and context for the cmp
        cmp = context?.let { ConversantCmp(it) }
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
        cmp?.setConfig(
            "{\"countryCode\":\"US\"\n" +
                    ",\"gdprAppliesGlobally\":true,\n" +
                    "\"policyUrl\":\"http://www.adtech123.com/privacy/\"\n" +
                    ",\"version\":\"1\"\n" +
                    ",\"id\":\"com.conversant.cmp-test-app\"\n" +
                    "}"
        )

        return inflater.inflate(R.layout.fragment_first, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //call the cmp on view creation
        activity?.let { it1 -> cmp?.presentCMPWidget(it1, requestCode = 100) }

        // Show present the CMP on button press, will not display cmp if consent is not required
        view.findViewById<Button>(R.id.summon_button).setOnClickListener {
            activity?.let { it1 ->
                cmp?.presentCMPWidget(it1, requestCode = 100)
            }
        }

        // Show CMP with stored values in able to edit or withdraw consent
        view.findViewById<Button>(R.id.modify_button).setOnClickListener {
            activity?.let { it1 ->
                cmp?.modifyConsent(it1, requestCode = 100)
            }
        }

        // Show present the CMP on button press, will not display cmp if consent is not required
        view.findViewById<Button>(R.id.clear_button).setOnClickListener {
            prefs?.edit()?.remove(TC_STRING)?.apply()
        }
    }
}
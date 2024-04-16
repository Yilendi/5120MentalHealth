package com.example.cis5120mentalhealth

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel

class SymptomsViewModel : ViewModel() {
    // Maps of symptoms and their checked states
    val symptomsGroup1 = mutableStateListOf("Fatigue", "Mood Change", "Sleep Change")
    val checkedStatesGroup1 = mutableStateMapOf<String, Boolean>().apply {
        symptomsGroup1.forEach { put(it, false) }
    }

    val symptomsGroup2 = mutableStateListOf("Chills", "Appetite Changes", "Headache", "Sweating")
    val checkedStatesGroup2 = mutableStateMapOf<String, Boolean>().apply {
        symptomsGroup2.forEach { put(it, false) }
    }
}

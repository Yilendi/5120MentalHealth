package com.example.mentalhealthapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    // Function to check if any symptoms are checked
    fun isAnySymptomChecked(): Boolean {
        return checkedStatesGroup1.values.any { it } || checkedStatesGroup2.values.any { it }
    }

    val showCard = mutableStateOf(false)

    fun setShowCard(value: Boolean) {
        showCard.value = value
    }

    val emojiList = listOf(
        "\uD83D\uDE42" to "Happy",
        "\uD83D\uDE03" to "Calm",
        "\uD83D\uDE10" to "Moody",
        "\uD83D\uDE28" to "Dizzy",
        "\uD83D\uDE29" to "Weary",
        "\uD83D\uDE41" to "Disturbed"

    )

    var selectedEmoji by mutableStateOf<String?>(null)

    fun reset() {
        checkedStatesGroup1.keys.forEach { checkedStatesGroup1[it] = false }
        checkedStatesGroup2.keys.forEach { checkedStatesGroup2[it] = false }
        selectedEmoji = null
        setShowCard(false)  // Optionally reset other states as needed
    }
}

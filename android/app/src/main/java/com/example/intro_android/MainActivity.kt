package com.example.intro_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                var text by remember { mutableStateOf("") }
                Column(Modifier.fillMaxSize().padding(16.dp)) {
                    InputField(text) { text = it }
                    Spacer(Modifier.weight(1f))
                    CustomKeyboard(
                        onKey = { text += it },
                        onDelete = { text = text.dropLast(1) }
                    )
                }
            }
        }
    }
}

@Composable
fun InputField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        readOnly = true,
        modifier = Modifier.fillMaxWidth().focusProperties { canFocus = false }
    )
}

@Composable
fun KeyButton(label: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(onClick, modifier, contentPadding = PaddingValues(0.dp)) { Text(label) }
}

@Composable
fun CustomKeyboard(onKey: (String) -> Unit, onDelete: () -> Unit) {
    var shift by remember { mutableStateOf(false) }
    val rows = listOf("azertyuiop", "qsdfghjklm", "wxcvbn")
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        rows.forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                row.forEach { c ->
                    val letter = if (shift) c.uppercase() else c.toString()
                    KeyButton(letter, Modifier.weight(1f)) {
                        onKey(letter)
                        shift = false
                    }
                }
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            KeyButton("/", Modifier.weight(1f)) { shift = !shift }
            KeyButton("Espace", Modifier.weight(3f)) { onKey(" ") }
            KeyButton("⌫", Modifier.weight(1f), onDelete)
        }
    }
}


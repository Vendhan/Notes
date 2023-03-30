@file:OptIn(ExperimentalFoundationApi::class)

package com.vendhan.notes.presentation.notesaddedit.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun HintTextField(
    modifier: Modifier = Modifier,
    value: String,
    onChange: (String) -> Unit,
    hint: String,
    textStyle: TextStyle = TextStyle(),
    bringIntoViewRequester: BringIntoViewRequester,
) {
    Box(modifier = modifier) {
        if (value.isEmpty()) {
            Text(
                text = hint,
                style = textStyle.copy(
                    color = MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.5F,
                    ),
                ),
            )
        }
        MaterialTheme(
            colorScheme = darkColorScheme(primary = MaterialTheme.colorScheme.onBackground),
        ) {
            BasicTextField(
                modifier = modifier.fillMaxWidth(),
                value = value,
                onValueChange = onChange,
                textStyle = textStyle,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Sentences,
                ),
                cursorBrush = SolidColor(value = MaterialTheme.colorScheme.onBackground),
            )
        }
    }
}

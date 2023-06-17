package com.example.notes.ui.core

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle

@Composable
fun TransparentTextField(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    hint: String,
    textColor: Color = Color.Black,
    backgroundColor: Color = Color.Transparent,
    shape: Shape = MaterialTheme.shapes.small.copy(
        bottomEnd = ZeroCornerSize,
        bottomStart = ZeroCornerSize
    ),
    trailingIcon: @Composable (() -> Unit)? = null,
    readOnly: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    singleLine: Boolean = false
) {
    TextField(
        value = text,
        onValueChange = onValueChange,
        placeholder = { Text(text = hint, style = textStyle) },
        singleLine = singleLine,
        textStyle = textStyle,
        shape = shape,
        trailingIcon = trailingIcon,
        readOnly = readOnly,
        modifier = modifier.fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            disabledTextColor = Color.Transparent,
            backgroundColor = backgroundColor,
            textColor = textColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}
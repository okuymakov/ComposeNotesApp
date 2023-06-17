package com.example.notes.ui.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.notes.R

@Composable
fun CollapsableLazyColumn(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable () -> Unit
) {
    var collapsed by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { collapsed = !collapsed }
    ) {
        Icon(
            Icons.Default.run {
                if (collapsed)
                    KeyboardArrowDown
                else
                    KeyboardArrowUp
            },
            contentDescription = stringResource(R.string.cd_collapse),
        )
        Text(
            title,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .weight(1f)
        )
    }
    Divider()
    if (!collapsed) {
        content()
    }
}
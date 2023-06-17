package com.example.notes.ui.core

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.notes.domain.models.Tag

@Composable
fun TagItem(
    modifier: Modifier = Modifier,
    name: String,
    onClick: () -> Unit = {},
    icon: @Composable () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .background(
                color = Color(0xffebebeb),//Color(0xffebebeb),
                shape = RoundedCornerShape(15.dp)
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.body2,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        icon()
    }
}
package com.example.notes.ui.tags

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.notes.R
import com.example.notes.domain.models.Tag
import com.example.notes.ui.core.TagItem
import com.example.notes.ui.core.TransparentTextField
import com.example.notes.ui.theme.LightBlue
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import kotlinx.collections.immutable.ImmutableList
import org.koin.androidx.compose.koinViewModel

@Composable
fun TagsScreen(viewModel: TagsViewModel = koinViewModel()) {
    val state = viewModel.state
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TransparentTextField(
            text = state.tagName,
            onValueChange = { viewModel.onAction(TagsAction.UpdateTagName(it)) },
            hint = stringResource(R.string.placeholder_tag_name),
            backgroundColor = LightBlue,
            shape = CircleShape,
            trailingIcon = {
                IconButton(onClick = { viewModel.onAction(TagsAction.SaveTag) }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.cd_add_tag)
                    )
                }
            },
        )
        Spacer(modifier = Modifier.height(32.dp))
        Tags(tags = state.tags, onDelete = { id ->
            viewModel.onAction(TagsAction.DeleteTag(id))
        })
    }

    if (state.openDialog) {
        AlertDialog(
            onDismissRequest = {
                viewModel.onAction(TagsAction.CloseDialog)
            },
            title = { Text(text = stringResource(R.string.title_create_tag)) },
            text = {
                TextField(
                    value = state.tagName,
                    onValueChange = { viewModel.onAction(TagsAction.UpdateTagName(it)) },
                    placeholder = { Text(text = stringResource(R.string.placeholder_tag_name)) }
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.run {
                            onAction(TagsAction.SaveTag)
                            onAction(TagsAction.CloseDialog)
                        }
                    }
                ) { Text(stringResource(R.string.btn_save_tag)) }
            },
            dismissButton = {
                Button(onClick = { viewModel.onAction(TagsAction.CloseDialog) }) {
                    Text(
                        stringResource(R.string.btn_cancel)
                    )
                }
            }
        )
    }
}

@Composable
fun Tags(
    modifier: Modifier = Modifier,
    tags: ImmutableList<Tag>,
    onDelete: (Long) -> Unit
) {
    FlowRow(
        mainAxisAlignment = FlowMainAxisAlignment.Center,
        mainAxisSize = SizeMode.Expand,
        crossAxisSpacing = 8.dp,
        mainAxisSpacing = 8.dp
    ) {
        tags.forEach { tag ->
            key(tag.id) {
                TagItem(
                    name = tag.name,
                    icon = {
                        Icon(
                            modifier = Modifier.size(18.dp),
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(R.string.cd_delete_tag),
                            tint = MaterialTheme.colors.onSurface
                        )
                    },
                    onClick = {
                        onDelete(tag.id)
                    }
                )
            }
        }
    }
}
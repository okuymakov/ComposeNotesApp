package com.example.notes.ui.notedetails


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.notes.R
import com.example.notes.domain.models.Tag
import com.example.notes.ui.core.CollapsableLazyColumn
import com.example.notes.ui.core.TagItem
import com.example.notes.ui.core.TransparentTextField
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.collections.immutable.ImmutableList
import org.koin.androidx.compose.koinViewModel

@Composable
fun NoteDetailsScreen(
    noteId: Long,
    noteColor: Int,
    navigateBack: () -> Unit,
    viewModel: NoteDetailsViewModel = koinViewModel()
) {
    val isNewNote by remember { mutableStateOf(noteId == -1L) }
    var isEditable by remember { mutableStateOf(noteId == -1L) }
    val systemUiController = rememberSystemUiController()
    val state = viewModel.state
    val color = remember { if (isNewNote) Color(state.note.color) else Color(noteColor) }

    SideEffect {
        systemUiController.systemBarsDarkContentEnabled = true
    }
    Scaffold(
        backgroundColor = color,
        contentColor = Color.Black,
        topBar = {
            TopAppBar(
                title = {
                    TransparentTextField(
                        text = state.note.title,
                        hint = stringResource(R.string.placeholder_note_title),
                        readOnly = !isEditable,
                        onValueChange = { input ->
                            viewModel.onAction(NoteDetailsAction.EditTitle(input))
                        },
                        textStyle = MaterialTheme.typography.h6,
                        backgroundColor = color
                    )
                },
                modifier = Modifier.statusBarsPadding(),
                elevation = 0.dp,
                backgroundColor = color,
                navigationIcon = {
                    if (isEditable) {
                        IconButton(onClick = {
                            isEditable = !isEditable
                            if (isNewNote) navigateBack()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = stringResource(R.string.cd_close)
                            )
                        }
                    } else {
                        IconButton(onClick = { navigateBack() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.cd_navigate_back)
                            )
                        }
                    }
                },
                actions = {
                    if (isEditable) {
                        IconButton(onClick = {
                            viewModel.onAction(NoteDetailsAction.SaveNote)
                            isEditable = !isEditable
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = stringResource(R.string.cd_save_note)
                            )
                        }
                    } else {
                        if (!isNewNote) {
                            IconButton(onClick = { viewModel.onAction(NoteDetailsAction.DeleteNote) }) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = stringResource(R.string.cd_delete_note)
                                )
                            }
                        }
                        IconButton(onClick = { isEditable = !isEditable }) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = stringResource(R.string.cd_edit_note)
                            )
                        }
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
                .navigationBarsPadding()
        ) {
            TransparentTextField(
                modifier = Modifier.fillMaxWidth(),
                text = state.note.content,
                hint = stringResource(R.string.placeholder_note_content),
                readOnly = !isEditable,
                onValueChange = { input -> viewModel.onAction(NoteDetailsAction.EditContent(input)) },
                backgroundColor = color
            )
            CollapsableLazyColumn(
                title = stringResource(R.string.title_tags)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Tags(
                    tags = state.note.tags,
                    isEditable = isEditable,
                    onDelete = { tag -> viewModel.onAction(NoteDetailsAction.DeleteTag(tag)) })
                if (isEditable) {
                    Spacer(modifier = Modifier.weight(1f))
                    AllTags(
                        tags = state.allTags,
                        onAdd = { tag -> viewModel.onAction(NoteDetailsAction.AddTag(tag)) },
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun Tags(
    modifier: Modifier = Modifier,
    tags: ImmutableList<Tag>,
    isEditable: Boolean,
    onDelete: (Tag) -> Unit = {},
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
                        if (isEditable) {
                            Icon(
                                modifier = Modifier.size(18.dp),
                                imageVector = Icons.Filled.Close,
                                contentDescription = stringResource(R.string.cd_delete_tag_from_note),
                                tint = MaterialTheme.colors.onSurface
                            )
                        }
                    },
                    onClick = { onDelete(tag) }
                )
            }
        }
    }
}

@Composable
fun AllTags(
    modifier: Modifier = Modifier,
    tags: ImmutableList<Tag>,
    onAdd: (Tag) -> Unit = {},
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
                            imageVector = Icons.Filled.Add,
                            contentDescription = stringResource(R.string.cd_add_tag_to_note),
                            tint = MaterialTheme.colors.onSurface
                        )
                    },
                    onClick = { onAdd(tag) }
                )
            }
        }
    }
}



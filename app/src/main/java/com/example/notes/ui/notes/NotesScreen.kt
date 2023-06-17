package com.example.notes.ui.notes


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.notes.R
import com.example.notes.domain.models.Note
import com.example.notes.ui.core.EmptyView
import kotlinx.collections.immutable.ImmutableList
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotesScreen(
    navigateToNote: (Long?, Int?) -> Unit,
    viewModel: NotesViewModel = koinViewModel()
) {
    val state = viewModel.state
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToNote(null, null) },
                modifier = Modifier.navigationBarsPadding()
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.cd_add_note)
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            if (state.notes.isEmpty()) {
                EmptyView(text = stringResource(R.string.msg_empty_notes))
            } else {
                NotesList(
                    notes = state.notes,
                    onClick = navigateToNote,
                    onDelete = { id -> viewModel.onAction(NotesAction.DeleteNote(id)) }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesList(
    modifier: Modifier = Modifier,
    notes: ImmutableList<Note>,
    onClick: (Long, Int) -> Unit,
    onDelete: (Long) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items = notes, key = { note -> note.id }) { note ->
            NoteItem(
                note = note,
                modifier = Modifier
                    .animateItemPlacement()
                    .fillMaxWidth()
                    .height(250.dp)
                    .clickable { onClick(note.id, note.color) },
                onDeleteClick = { onDelete(note.id) }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
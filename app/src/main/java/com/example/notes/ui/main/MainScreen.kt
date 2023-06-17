package com.example.notes.ui.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.notes.ui.notes.NotesScreen
import com.example.notes.ui.tags.TagsScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    navigateToNote: (Long?, Int?) -> Unit,
) {
    val tabs = remember { listOf("Notes", "Tags") }
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val tabIndex = pagerState.currentPage
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.systemBarsDarkContentEnabled = false
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        TopAppBar(
            modifier = Modifier.statusBarsPadding(),
            backgroundColor = MaterialTheme.colors.primary,
            title = { Text(text = "Notes", style = MaterialTheme.typography.h4) }
        )
        TabRow(selectedTabIndex = tabIndex, backgroundColor = MaterialTheme.colors.primary) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }
        HorizontalPager(
            pageCount = tabs.count(),
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { index ->
            when (index) {
                0 -> NotesScreen(navigateToNote)
                1 -> TagsScreen()
            }
        }
    }
}
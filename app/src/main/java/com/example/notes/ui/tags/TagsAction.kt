package com.example.notes.ui.tags

sealed class TagsAction {
    object OpenDialog : TagsAction()
    object CloseDialog : TagsAction()
    data class UpdateTagName(val name: String) : TagsAction()
    data class DeleteTag(val id: Long) : TagsAction()
    object SaveTag : TagsAction()
}
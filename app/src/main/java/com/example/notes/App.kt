package com.example.notes

import android.app.Application
import com.example.notes.data.models.NoteRealm
import com.example.notes.data.models.TagRealm
import com.example.notes.data.repositories.NotesRepositoryImpl
import com.example.notes.data.repositories.TagsRepositoryImpl
import com.example.notes.domain.repositories.NotesRepository
import com.example.notes.domain.repositories.TagsRepository
import com.example.notes.ui.notedetails.NoteDetailsViewModel
import com.example.notes.ui.notes.NotesViewModel
import com.example.notes.ui.tags.TagsViewModel
import io.realm.kotlin.Configuration
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.migration.AutomaticSchemaMigration
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.util.*

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}

private const val realmVersion = 0L

private val appModule = module {
    single<Configuration> {
        RealmConfiguration.Builder(schema = setOf(NoteRealm::class, TagRealm::class))
            .schemaVersion(realmVersion)
            .build()
    }
    single<NotesRepository> { NotesRepositoryImpl(get()) }
    single<TagsRepository> { TagsRepositoryImpl(get()) }
    viewModel { NotesViewModel(get()) }
    viewModel { NoteDetailsViewModel(get(), get(), get()) }
    viewModel { TagsViewModel(get()) }
}
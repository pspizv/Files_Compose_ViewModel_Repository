package org.izv.dam.psp.filescomposableviewmodelrepository.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.izv.dam.psp.filescomposableviewmodelrepository.model.repository.FileRepository

class FileViewModel(private val repository: FileRepository): ViewModel()  {

    private val _content = MutableStateFlow<List<String>>(emptyList())
    val content = _content.asStateFlow()

    fun readLines() {
        viewModelScope.launch {
            _content.value = repository.readContent()
        }
    }

    fun writeLine(line: String) {
        viewModelScope.launch {
            repository.writeLine(line)
        }
    }
}
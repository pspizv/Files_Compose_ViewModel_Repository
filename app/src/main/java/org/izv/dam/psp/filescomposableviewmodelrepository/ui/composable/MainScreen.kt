package org.izv.dam.psp.filescomposableviewmodelrepository.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import org.izv.dam.psp.filescomposableviewmodelrepository.R
import org.izv.dam.psp.filescomposableviewmodelrepository.model.repository.FileRepository
import org.izv.dam.psp.filescomposableviewmodelrepository.ui.theme.FilesComposableViewModelRepositoryTheme
import org.izv.dam.psp.filescomposableviewmodelrepository.ui.viewmodel.FileViewModel

@Composable
fun MainScreen(innerPadding: PaddingValues) {

    val context = LocalContext.current
    val fileRepository = FileRepository(context = context)
    val fileViewModel: FileViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FileViewModel(fileRepository) as T
            }
        }
    )

    val content by fileViewModel.content.collectAsState()

    LaunchedEffect(Unit) {
        fileViewModel.readLines()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(innerPadding)
    ) {
        DataContent(fileViewModel, content)
    }
}

@Composable
fun DataContent(viewModel: FileViewModel, lines: List<String>) {
    var textFieldValue by remember { mutableStateOf("") }

    TextField(
        value = textFieldValue,
        onValueChange = { textFieldValue = it },
        label = { Text(stringResource(R.string.introduce_entrada)) },
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        onClick = {
            if (textFieldValue.isNotBlank()) {
                viewModel.writeLine(textFieldValue)
                textFieldValue = ""
            }
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(stringResource(R.string.escribir_archivo))
    }
    Button(onClick = {
        viewModel.readLines()
    }) {
        Text(stringResource(R.string.leer_archivo))
    }
    LazyColumn {
        items(lines) { line ->
            Text(line)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    FilesComposableViewModelRepositoryTheme {
        MainScreen(PaddingValues(24.dp))
    }
}
<h2>Integrando Compose, ViewModel y Repositorio. Uso de corrutinas.</h2>

En JetPack Compose se define una variable que observa el contenido de la variable que se expone en el ViewModel.

<pre>
@Composable
fun ArchivoScreen(viewModel: MainViewModel) {
   val contenido by viewModel.content.collectAsState()
   Column {
       Button(onClick = { viewModel.readData() }) {
           Text("Leer archivo")
       }
       LazyColumn {
           items(contenido) { linea ->
               Text(linea)
           }
       }
   }
}
</pre>

En el ViewModel se define como privada la variable que va a recibir el estado de la ejecución de las tareas del repositorio y se
define como público e inmutable la variable que se expone.

Las operaciones del repositorio se lanzan mediante corrutinas. Se usa el ámbito del ViewModel para asegurar que la corrutina convive con el ViewModel.

<pre>
private val _content = MutableStateFlow<List<String>>(emptyList())
val content: StateFlow<List<String>> = _content

fun readData() {
   viewModelScope.launch {
       _content.value = repository.readFile("/data/data/datos.txt")
   }
}
</pre>

En el repositorio se define como una función de suspensión la función que se va a lanzar como corrutina desde el ViewModel. Una función de suspensión no bloquea el hilo principal.

Las operaciones de entrada y salida se ejecutan en el pool de hilos IO.

<pre>
suspend fun readFile(filePath: String): List<String> {
   return withContext(Dispatchers.IO) {
       val file = File(filePath)
       if (file.exists()) {
           file.readLines()
       } else {
           emptyList()
       }
   }
}
</pre>


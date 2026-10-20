package co.edu.ucaldas.mvvm

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroesScreen(
    viewModel: HeroesViewModel = viewModel()
) {
    val elementos by viewModel.elementos.collectAsState()
    val cargando by viewModel.cargando.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Lista con MVVM") })
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (cargando) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(elementos, key = { it.id }) { item ->
                        HeroeItem(
                            elemento = item,
                            onFavoritoClick = { viewModel.marcarFavorito(item.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HeroeItem(
    elemento: Nombres,
    onFavoritoClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = elemento.nombre,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = elemento.descripcion,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            TextButton(onClick = onFavoritoClick) {
                Text(
                    text = if (elemento.esFavorito) "❤️ Favorito" else "🤍 Agregar",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
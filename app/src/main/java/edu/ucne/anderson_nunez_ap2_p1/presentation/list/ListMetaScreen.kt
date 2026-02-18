package edu.ucne.anderson_nunez_ap2_p1.presentation.metas.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.anderson_nunez_ap2_p1.domain.metas.model.Meta

@Composable
fun ListMetaScreen(
    viewModel: ListMetaViewModel = hiltViewModel(),
    onAddMeta: () -> Unit,
    onEditMeta: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ListMetaBody(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onAddMeta = onAddMeta,
        onEditMeta = onEditMeta
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListMetaBody(
    uiState: ListMetaUiState,
    onEvent: (ListMetaUiEvent) -> Unit,
    onAddMeta: () -> Unit,
    onEditMeta: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Metas") },
                actions = {
                    if (uiState.descripcionFilter.isNotBlank() ||
                        uiState.observacionesFilter.isNotBlank()) {
                        IconButton(onClick = { onEvent(ListMetaUiEvent.ClearFilters) }) {
                            Icon(Icons.Default.Clear, contentDescription = "Limpiar filtros")
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddMeta) {
                Icon(Icons.Default.Add, contentDescription = "Agregar meta")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Filtros",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = uiState.descripcionFilter,
                onValueChange = {
                    onEvent(ListMetaUiEvent.DescripcionFilterChanged(it))
                },
                label = { Text("Filtrar por descripción") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = uiState.observacionesFilter,
                onValueChange = {
                    onEvent(ListMetaUiEvent.ObservacionesFilterChanged(it))
                },
                label = { Text("Filtrar por observaciones") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(16.dp))

            Divider()

            Spacer(Modifier.height(16.dp))

            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (uiState.metas.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (uiState.descripcionFilter.isBlank() &&
                            uiState.observacionesFilter.isBlank()) {
                            "No hay metas registradas"
                        } else {
                            "No se encontraron metas con esos filtros"
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.metas, key = { it.idMeta }) { meta ->
                        MetaCard(
                            meta = meta,
                            onClick = { onEditMeta(meta.idMeta) }
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Resumen",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    Spacer(Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total de metas:",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "${uiState.totalMetas}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Suma total:",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "$${String.format("%.2f", uiState.sumaTotal)}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MetaCard(
    meta: Meta,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = meta.descripcion,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = meta.observaciones,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
            }

            Spacer(Modifier.width(16.dp))

            Text(
                text = "$${String.format("%.2f", meta.monto)}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
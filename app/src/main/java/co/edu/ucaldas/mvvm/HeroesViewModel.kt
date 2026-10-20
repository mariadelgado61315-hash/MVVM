package co.edu.ucaldas.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HeroesViewModel : ViewModel() {

    private val _elementos = MutableStateFlow<List<Nombres>>(emptyList())
    val elementos: StateFlow<List<Nombres>> = _elementos.asStateFlow()

    private val _cargando = MutableStateFlow(true)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    // Variable para la prueba del BUG
    private var elementosCache: List<Nombres> = emptyList()

    // Función con el BUG a propósito
    fun marcarFavoritoConBug(id: Int) {
        if (elementosCache.isEmpty()) {
            elementosCache = _elementos.value
        }
        elementosCache = elementosCache.map { elemento ->
            if (elemento.id == id) {
                elemento.copy(esFavorito = !elemento.esFavorito)
            } else {
                elemento
            }
        }
        // ATENCIÓN: Falta la línea `_elementos.value = elementosCache`
    }

    // Función SOLUCIÓN CORRECTA
    fun marcarFavorito(id: Int) {
        _elementos.value = _elementos.value.map { elemento ->
            if (elemento.id == id) {
                elemento.copy(esFavorito = !elemento.esFavorito)
            } else {
                elemento
            }
        }
    }

    init {
        cargarElementos()
    }

    fun cargarElementos() {
        viewModelScope.launch {
            _cargando.value = true
            delay(1000L)
            _elementos.value = listaNombres
            _cargando.value = false
        }
    }
}
##1. Si tu ViewModel guarda la lista en una variable normal (var cursosCache = listOf(...))
además de en el MutableStateFlow, y actualizas solo la variable pero no el StateFlow,
¿la UI reflejará el cambio?  Justifica tu respuesta.


#predicción
 La UI, NO reflejará ningún cambio.
 Si solo cambias la variable normal y no el StateFlow, la app no se entera de nada. Asi que el
 Jetpack Compose no está 'escuchando' las variables comunes, por esta razón. La pantalla no se va a
  refrescar ni vas a ver ningún cambio en la interfaz hasta que no actualices el StateFlow.


-2. ¿Qué pasó al probar marcarFavoritoConBug?
 Al darle clic al botón para marcar un favorito usando marcarFavoritoConBug, en la pantalla no pasó
 absolutamente nada, la UI se quedó congelada sin actualizar el ícono y en la consola de Logcat
 no salió ningún error ni la app se cayó, simplemente la interfaz ignoró el cambio.

 Explicación de por qué falló (y cómo se arregla)
 El StateFlow nunca se dio cuenta, Jetpack Compose se da cuenta de que algo cambió gracias al StateFlow. 
 Si no actualizamos directamente su propiedad. value, Compose no recibe el "aviso" para volver a 
 dibujar la pantalla.

 La variable elementosCache trabaja a escondidas para modificar una variable local o en caché no le dice
 nada a la interfaz gráfica. Para Compose esa variable es invisible.

 Finalmente, se soluciona asi:En la función marcarFavorito, le pasamos una nueva lista directamente
 a _elementos.value. Al reasignar el valor del StateFlow, Compose reacciona al instante y actualiza
 el corazón de la pantalla.
 
-3. Reflexión: Cómo el ViewModel nos salvó al girar la pantalla
¿Qué pasa cuando rotas el celular?

Se marcan varios héroes como favoritos y se gira la pantalla para ver qué pasaba

-- Usando ViewModel + StateFlow: Todo se quedó tal cual. Los favoritos marcados siguieron allí intactos.
Esto pasa porque Android destruye y vuelve a crear la Activity al rotar, pero el ViewModel se queda
vivo en memoria guardando toda la información sin modificar nada.

¿Y si hubiéramos usado solo remember en el Composable?
Si la lista de favoritos dependiera únicamente de un remember { mutableStateOf(...) } que esta 
metido dentro de la pantalla, al girar el teléfono la vista se reiniciaría por completo y el usuario 
perdería toda la información que selecciono. El ViewModel es el arma mágica para evitar estos errores!
# CODIGO EN LA RAMA MASTER (cambiar de rama)
# Mapa-Subte-BsAs
Trabajo ISFPP del curso Programacion Orientada a Objetos.

## De que trata?
Es un programa en donde el usuario puede seleccionar dos estaciones de la red de subtes de Buenos Aires y como resultado, en forma de texto, se le devolverá el camino mas corto, el de menos congestión de gente o el de menos trasbordos entre las dos estaciones seleccionadas. Las opciones las puede elegir el usuario.

## Como esta hecho?
Hace el uso de grafos. Como nodos tenemos las Estaciones y los arcos contienen el peso del viaje y congestión de personas. 
Para crear el resultado utiliza el algoritmo de Dijkstra.
Tambien hace el uso de Mapas, guardando ahi las estaciones, teniendo la posibilidad de buscarlas por su clave.

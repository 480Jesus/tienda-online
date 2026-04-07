?# Sistema de Procesamiento de Pedidos

Aplicacion Java por consola para crear y confirmar pedidos.

## Inicio rapido

1. Abre una terminal en la raiz del proyecto:

```powershell
cd c:\Clean_code\Tienda-online
```

2. Compila el proyecto:

```powershell
New-Item -ItemType Directory -Force out | Out-Null
javac -d out (Get-ChildItem -Recurse -Filter *.java src\main\java | ForEach-Object { $_.FullName })
```

3. Ejecuta la aplicacion:

```powershell
java -cp .\out com.tienda.pedidos.Main
```

## Que hace

Con el menu puedes:

- crear un pedido
- aplicar descuento
- aplicar impuesto
- aplicar gastos de envio
- aplicar recargo
- ver el resumen
- confirmar el pedido

Cuando confirmas un pedido:

- se notifica por consola
- se muestra un mensaje interno
- se guarda en `pedidos.txt`

## Menu de la aplicacion

```text
1. Crear pedido
2. Aplicar descuento
3. Aplicar impuesto
4. Aplicar gastos de envio
5. Aplicar recargo
6. Ver resumen
7. Confirmar pedido
8. Salir
```

## Que introducir en cada opcion

### 1. Crear pedido
La aplicacion pide dos datos:

- `identificador del pedido`
  - Es un texto libre para identificar el pedido.
  - Ejemplos: `PED-001`, `pedido-juan`, `A123`
  - No debe estar vacio.

- `importe base`
  - Es el precio inicial del pedido antes de aplicar operaciones.
  - Debe ser un numero mayor o igual que `0`.
  - Puedes escribirlo con punto o coma decimal.
  - Ejemplos validos: `100`, `49.99`, `75,50`

### 2. Aplicar descuento
La aplicacion pide:

- `porcentaje de descuento`
  - Se aplica sobre el total actual del pedido.
  - Debe estar entre `0` y `100`.
  - Ejemplos: `10`, `15.5`, `20`

Ejemplo:
- Si el pedido vale `100` y aplicas un `10`, el total pasa a `90`

### 3. Aplicar impuesto
La aplicacion pide:

- `porcentaje de impuesto`
  - Se aplica sobre el total actual del pedido.
  - Debe estar entre `0` y `100`.
  - Ejemplos: `21`, `4`, `10.5`

Ejemplo:
- Si el total actual es `90` y aplicas un `21`, el total pasa a `108.90`

### 4. Aplicar gastos de envio
La aplicacion pide:

- `importe del gasto de envio`
  - Es una cantidad fija que se suma al pedido.
  - Debe ser mayor o igual que `0`.
  - Ejemplos: `5`, `7.99`, `3,50`

### 5. Aplicar recargo
La aplicacion pide:

- `importe del recargo`
  - Es una cantidad fija adicional.
  - Debe ser mayor o igual que `0`.
  - Ejemplos: `2`, `1.25`, `4,75`

### 6. Ver resumen
Muestra por consola:

- id del pedido
- importe base
- operaciones aplicadas en orden
- total final
- si el pedido esta confirmado o no

### 7. Confirmar pedido
Al confirmar el pedido, el sistema hace tres cosas:

- notifica al cliente por consola
- muestra un mensaje interno por consola
- guarda el pedido en el fichero `pedidos.txt`

Despues de confirmar, el pedido actual se cierra y debes crear otro si quieres seguir trabajando.

### 8. Salir
Cierra la aplicacion.

## Orden recomendado de uso

1. Crear pedido
2. Aplicar cero o mas operaciones
3. Ver resumen
4. Confirmar pedido
5. Salir o crear otro pedido

## Ejemplo completo de prueba

Prueba este flujo:

1. Opcion `1`
2. Id: `PED-001`
3. Importe base: `100`
4. Opcion `2`
5. Descuento: `10`
6. Opcion `3`
7. Impuesto: `21`
8. Opcion `4`
9. Gasto de envio: `5`
10. Opcion `5`
11. Recargo: `2.5`
12. Opcion `6`
13. Opcion `7`

Resultado esperado del resumen:

- Importe base: `100.00`
- Operaciones:
  - `Descuento 10.00%`
  - `Impuesto 21.00%`
  - `Gasto de envio 5.00`
  - `Recargo 2.50`
- Total final: `116.40`

## Validaciones incluidas

La aplicacion controla estos casos:

- no se puede crear un pedido con importe base negativo
- no se puede crear un pedido sin identificador
- no se puede aplicar una operacion si no existe un pedido creado
- descuento e impuesto deben estar entre `0` y `100`
- gasto de envio y recargo no pueden ser negativos
- no se puede confirmar un pedido inexistente
- no se puede volver a modificar un pedido ya confirmado

## Fichero de salida

Cada vez que confirmas un pedido, se anade una nueva entrada en:

```text
pedidos.txt
```

El fichero no borra pedidos anteriores. Va acumulando el historial de pedidos confirmados.

## Arquitectura

El proyecto sigue una arquitectura hexagonal simplificada:

- `domain`: reglas de negocio y modelo del pedido
- `application`: coordinacion de casos de uso
- `infrastructure`: entrada por CLI y salidas a consola y fichero

El calculo de operaciones usa el patron `Decorator`.

## Notas utiles

- Si introduces texto donde se espera un numero, la aplicacion te lo volvera a pedir.
- Puedes usar `,` o `.` para decimales.
- La aplicacion esta pensada para manejar un pedido activo cada vez.

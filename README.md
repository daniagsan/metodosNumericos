# Métodos Numéricos

Aplicación de escritorio en **Java Swing + FlatLaf** para la resolución iterativa de ecuaciones no lineales mediante métodos numéricos clásicos.

## Métodos implementados

| Método | Descripción |
|---|---|
| **Bisección** | División sucesiva del intervalo hasta encontrar la raíz |
| **Falsa Posición** | Interpolación lineal dentro del intervalo |
| **Newton-Raphson** | Aproximación tangente con derivada numérica (requiere `x0`) |

## Arquitectura (MVC)

```
src/
├── metodosNumericos/       # Entry point
│   ├── Main.java           # Punto de entrada + setup FlatLaf
│   └── Ventana.java        # Ventana principal con menú (JFrame)
├── controladores/          # Lógica de negocio
│   ├── Evaluador.java      # Parser de expresiones matemáticas
│   ├── MyMenuListener.java # Manejo del menú de selección
│   └── procesoInterno.java # Control de iteraciones por método
└── visual/
    └── VistaDefault.java   # Panel con tabla, inputs y botones
```

## Expresiones matemáticas

El parser propio en `Evaluador.java` soporta:

- **Operadores:** `+`, `-`, `*`, `/`, `^`
- **Variable:** `x`
- **Constantes:** `e`, `pi`
- **Funciones:** `sin`, `cos`, `tan`, `sqrt`, `log`, `ln`, `abs`, `exp`, `asin`, `acos`, `atan`

Ejemplo: `x^3 - 3*x + 1`

## Uso

1. Seleccionar método en el menú **Opciones > Cambiar método**
2. Ingresar valores de `a`, `b`, `x0` (solo Newton-Raphson), `f(x)` y tolerancia
3. Click en **Aplicar método** para configurar la tabla
4. Click en **Iniciar** iterativamente hasta obtener el error deseado
5. **Limpiar** para reiniciar la tabla (Ctrl+L)
6. **Exportar CSV** para guardar los resultados (Ctrl+E)
7. **Enter** desde cualquier campo ejecuta Iniciar

## Compilación y ejecución

```bash
# Opción 1: build.bat
build.bat

# Opción 2: manual
javac -d bin -cp lib/flatlaf-3.5.1.jar src/**/*.java
java -cp bin;lib/flatlaf-3.5.1.jar metodosNumericos.Main
```

## Requisitos

- Java 8+
- Dependencia: [FlatLaf 3.5.1](https://central.sonatype.com/artifact/com.formdev/flatlaf/3.5.1) (incluido en `lib/`)
class Alumno(
    var nombre: String,
    var edad: Int,
    var notas: Array<Double>
) {

    var correo: String? = null

    init {
        if (nombre.isBlank()) {
            println("El nombre no puede estar vacío")
        }

        if (edad <= 0) {
            println("La edad no es válida")
        }
    }

    constructor(
        nombre: String,
        edad: Int,
        notas: Array<Double>,
        correo: String
    ) : this(nombre, edad, notas) {
        this.correo = correo
    }

    fun calcularPromedio(): Double {
        var suma = 0.0

        for (nota in notas) {
            suma += nota
        }
        return suma / notas.size
    }

    fun obtenerEstado(): String {
        val promedio = calcularPromedio()

        return if (promedio >= 12) {
            "Aprobado"
        } else {
            "Desaprobado"
        }
    }

    fun mostrarDatos() {
        println("Nombre: $nombre")
        println("Edad: $edad")
        println("Promedio: ${calcularPromedio()}")
        println("Estado: ${obtenerEstado()}")
        println("Correo: ${correo ?: "Sin correo"}")
    }

    fun mostrarDatos(mostrarNotas: Boolean) {
        mostrarDatos()

        if (mostrarNotas) {
            println("Notas:")

            for (i in notas.indices) {
                println("Nota ${i + 1}: ${notas[i]}")
            }
        }
    }
}

fun leerEdad(): Int {
    var edad: Int?

    do {
        println("Ingrese la edad: ")
        edad = readln().toIntOrNull()

        if (edad == null || edad <= 0) {
            println("Edad invalida")
        }

    } while (edad == null || edad <= 0)

    return edad
}

fun leerNota(numero: Int): Double {
    var nota: Double?

    do {
        println("Ingrese nota $numero:")
        nota = readln().toDoubleOrNull()

        if (nota == null || nota !in 0.0..20.0) {
            println("Nota inválida")
        }

    } while (nota == null || nota !in 0.0..20.0)

    return nota
}

fun leerNotas(): Array<Double> {
    val notas = Array(3) { 0.0 }

    for (i in notas.indices) {
        notas[i] = leerNota(i + 1)
    }

    return notas
}

fun main() {

    val alumnos = mutableListOf<Alumno>()
    val nombresRegistrados = mutableSetOf<String>()
    val promedios = mutableMapOf<String, Double>()

    val opciones = listOf(
        "1. Registrar alumno",
        "2. Registrar alumno con correo",
        "3. Mostrar alumnos",
        "4. Buscar alumno",
        "5. Mostrar estadísticas",
        "6. Salir"
    )

    var opcion: Int

    do {
        println()
        println(" SISTEMA DE ALUMNOS ")

        for (op in opciones) {
            println(op)
        }

        println("Ingrese una opción:")
        opcion = readln().toIntOrNull() ?: 0

        when (opcion) {

            1, 2 -> {

                println("Ingrese el nombre:")
                val nombre = readln()

                if (nombre in nombresRegistrados) {
                    println("El alumno ya está registrado")
                    continue
                }

                val edad = leerEdad()
                val notas = leerNotas()

                val alumno: Alumno

                if (opcion == 1) {

                    alumno = Alumno(
                        nombre,
                        edad,
                        notas
                    )

                } else {

                    println("Ingrese el correo:")
                    val correo = readln()

                    alumno = Alumno(
                        nombre,
                        edad,
                        notas,
                        correo
                    )
                }

                alumnos.add(alumno)
                nombresRegistrados.add(nombre)
                promedios[nombre] = alumno.calcularPromedio()

                println("Alumno registrado correctamente")
            }

            3 -> {

                if (alumnos.size == 0) {
                    println("No hay alumnos registrados")
                } else {

                    println("=== ALUMNOS ===")

                    for (alumno in alumnos) {
                        alumno.mostrarDatos()
                        println()
                    }
                }
            }

            4 -> {

                println("Ingrese el nombre a buscar:")
                val nombreBuscar = readln()

                var encontrado: Alumno? = null

                for (alumno in alumnos) {

                    if (alumno.nombre == nombreBuscar) {
                        encontrado = alumno
                        break
                    }
                }

                if (encontrado != null) {
                    encontrado.mostrarDatos(true)
                } else {
                    println("Alumno no encontrado")
                }
            }

            5 -> {

                if (alumnos.size == 0) {

                    println("No hay alumnos registrados")

                } else {

                    var sumaPromedios = 0.0
                    var mejorPromedio = -1.0
                    var mejorAlumno = ""

                    for ((nombre, promedio) in promedios) {

                        sumaPromedios += promedio

                        if (promedio > mejorPromedio) {
                            mejorPromedio = promedio
                            mejorAlumno = nombre
                        }
                    }

                    val promedioGeneral =
                        sumaPromedios / promedios.size

                    println("Cantidad de alumnos: ${alumnos.size}")
                    println("Nombres únicos: ${nombresRegistrados.size}")
                    println("Promedio general: $promedioGeneral")
                    println("Mejor alumno: $mejorAlumno")
                    println("Mejor promedio: $mejorPromedio")
                }
            }

            6 -> {
                println("Programa finalizado")
            }

            else -> {
                println("Opción inválida")
            }
        }

    } while (opcion != 6)
}
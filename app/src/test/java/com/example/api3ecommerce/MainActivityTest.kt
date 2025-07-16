package com.example.api3ecommerce

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    private val activity = MainActivity()

    @Test
    fun `Dado un listado de productos, validar que la información se puede mostrar en pantalla` () {
        // preparar datos de preuba
        val productos = listOf(
            Product(
                id = 1,
                name = "Producto A",
                description = "Producto de prueba",
                price = 10.99,
                currency = "USD",
                inStock = true
            ),
            Product(
                id = 2,
                name = "Producto B",
                description = "Producto de prueba con una descripcíon mas larga",
                price = 10030.20,
                currency = "ARS",
                inStock = false
            ),
        )
        val resultado = activity.obtenerListadoProductos(productos)
        assertEquals("""
            ID: 1, Nombre: Producto A
            ID: 2, Nombre: Producto B
            """.trimIndent(),resultado)
    }

    @Test
    fun `Cuando el listado esta vacio, validar que no se pueda mostrar información en pantalla`(){
        val productos = emptyList<Product>()

        val resultado = activity.obtenerListadoProductos(productos)

        assertEquals("No hay productos disponibles", resultado)
    }
}
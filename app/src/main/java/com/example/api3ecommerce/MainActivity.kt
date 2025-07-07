package com.example.api3ecommerce

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        obtenerProductos()
    }

    private fun obtenerProductos(){
        var listado = findViewById<LinearLayout>(R.id.productContainer)

        val client = Retrofit.Builder()
            .baseUrl("https://www.jsonkeeper.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        try {
            val service =  client.create(ProductService::class.java)
            val call = service.getProducts( )
            call.enqueue(object : Callback<ProductResponse>{
                override fun onResponse(
                    call: Call<ProductResponse>,
                    response: Response<ProductResponse>
                ) {

                    if(response.isSuccessful){
                        val products = response.body()?.products
                        products?.forEach { product ->

                            listado.addView(crearProductoView(product))

                            Log.d("MainActivity", "Producto: ${product.name} precio: ${product.price}")
                        }
                    } else {
                        Log.d("MainActivity", "API error ${response.code()}")

                    }

                }

                override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                    Log.d("MainActivity", "Error ${t.message}")

                }
            })
        } catch (e: Exception) {
            println("Error al realizar la solicitud: ${e.message}")
        }
    }

    private fun crearProductoView(product: Product): LinearLayout {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.parseColor("#EEEEEE"))
            setPadding(24, 24, 24, 24)
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 0, 0, 24)
            layoutParams = params
        }

        val nameText = TextView(this).apply {
            text = product.name
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
            setTextColor(Color.BLACK)
        }

        val priceText = TextView(this).apply {
            text = "Precio: ${product.price}"
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            setTextColor(Color.DKGRAY)
        }

        layout.addView(nameText)
        layout.addView(priceText)

        return layout
    }
}


package com.example.api3ecommerce

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val currency: String,
    val inStock: Boolean,
)

data class ProductResponse(
    val products: List<Product>
)


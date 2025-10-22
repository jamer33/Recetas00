package com.example.recetas00.data
//hay que declarar la dependencia de Retrofit en el fichero gradle para utilizar el SerializedName
//implementation("com.squareup.retrofit2:converter-gson:$retrofit")

import com.google.gson.annotations.SerializedName

data class Recipe (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("ingredients") val ingredients: List<String>,
    @SerializedName("instructions") val instructions: List<String>,
    @SerializedName("prepTimeMinutes") val prepTimeMinutes: Int,
    @SerializedName("cookTimeMinutes") val cookTimeMinutes: Int,
    @SerializedName("servings") val servings: Int,
    @SerializedName("difficulty") val difficulty: String,
    @SerializedName("cuisine") val cuisine: String,
    @SerializedName("tags") val tags: List<String>,
    @SerializedName("image") val image: String,
    @SerializedName("mealType") val mealType: List<String>
)
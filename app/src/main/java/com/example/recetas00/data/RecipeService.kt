package com.example.recetas00.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface RecipeService {

    @GET("recipes")
    suspend fun getAllRecipes(): List<Recipe>

    @GET("recipe")
    suspend fun getRecipeById(@Query("id") id: Int): Recipe

    companion object {
        fun getInstance(): RecipeService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://dummyjson.com/recipes/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(RecipeService::class.java)
        }
    }
}
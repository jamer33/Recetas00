package com.example.recetas00.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RecipeService {

    @GET("recipes")
    suspend fun getAllRecipes(@Query("limit") limit: Int = 0): RecipeResponse

    @GET("recipes/search")
    suspend fun searchRecipes(@Query("q") query: String = "", @Query("limit") limit: Int = 0): RecipeResponse

    @GET("recipe/{id}")
    suspend fun getRecipeById(@Path("id") id: Int): Recipe

    @GET("recipes/tags")
    suspend fun getAllTags(): List<String>

    companion object {
        fun getInstance(): RecipeService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://dummyjson.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(RecipeService::class.java)
        }
    }
}
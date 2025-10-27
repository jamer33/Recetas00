package com.example.recetas00.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log


class SessionManager(context: Context) {

    val sharedPreferences: SharedPreferences = context.getSharedPreferences("recipe_session", Context.MODE_PRIVATE)


    fun setFavorite(recipeId: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("FAVORITE_RECIPE_ID", recipeId)
        editor.apply()
        Log.i("Favorite", "Grabado -> " + recipeId.toString() + " | " + getFavorite().toString())

    }

    fun getFavorite(): Int {
        return sharedPreferences.getInt("FAVORITE_RECIPE_ID", -1)!!
    }

    fun isFavorite(recipeId: Int): Boolean {
        Log.i("Favorite", "Comprobando si es favorita -> " + recipeId.toString() + " == " + getFavorite().toString())
        return recipeId == getFavorite()
    }
}
package com.example.recetas00.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class SessionManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("recipe_session2", Context.MODE_PRIVATE)

    // --- Versión con Set<String> (la que ya funciona) ---
    private val favoriteStringKey = "FAVORITE_RECIPE_STRING_LIST"

    fun getFavoriteStrings(): Set<String> {
        val favorites = sharedPreferences.getStringSet(favoriteStringKey, setOf()) ?: setOf()
        Log.i("SessionManager", "Leyendo favoritos (String): $favorites")
        return favorites
    }

    fun addFavoriteString(recipeId: String) {
        val currentFavorites = getFavoriteStrings().toMutableSet()
        currentFavorites.add(recipeId)
        sharedPreferences.edit().putStringSet(favoriteStringKey, currentFavorites).apply()
        Log.i("SessionManager", "Favorito añadido (String). Nueva lista: $currentFavorites")
    }

    // etc. para remove e isFavorite...

    // --- NUEVA VERSIÓN: con List<Int> ---
    private val favoriteIntKey = "FAVORITE_RECIPE_INT_LIST"
    private val separator = ","

    /**
     * Obtiene la lista de favoritos como una lista de enteros.
     */
    fun getFavoriteInts(): List<Int> {
        // 1. Leemos la cadena guardada. Si no existe, es una cadena vacía.
        val favoriteString = sharedPreferences.getString(favoriteIntKey, "")
        Log.i("SessionManager", "Leyendo cadena de Ints: '$favoriteString'")

        // 2. Si la cadena no está vacía, la separamos y convertimos cada parte a Int.
        return if (favoriteString.isNullOrEmpty()) {
            emptyList() // Devolvemos una lista vacía si no hay nada guardado
        } else {
            favoriteString.split(separator).map { it.toInt() }
        }
    }

    /**
     * Añade un nuevo ID (entero) a la lista de favoritos.
     */
    fun addFavoriteInt(recipeId: Int) {
        // 1. Obtenemos la lista actual de enteros como una lista mutable.
        val currentFavorites = getFavoriteInts().toMutableList()
        // 2. Añadimos el nuevo ID.
        currentFavorites.add(recipeId)
        // 3. Convertimos la lista de Ints a una única cadena de texto.
        val favoriteString = currentFavorites.joinToString(separator)
        // 4. Guardamos la cadena en SharedPreferences.
        sharedPreferences.edit().putString(favoriteIntKey, favoriteString).apply()
        Log.i("SessionManager", "Favorito añadido (Int). Nueva cadena: '$favoriteString'")
    }

    /**
     * Elimina un ID (entero) de la lista de favoritos.
     */
    fun removeFavoriteInt(recipeId: Int) {
        val currentFavorites = getFavoriteInts().toMutableList()
        currentFavorites.remove(recipeId)
        val favoriteString = currentFavorites.joinToString(separator)
        sharedPreferences.edit().putString(favoriteIntKey, favoriteString).apply()
        Log.i("SessionManager", "Favorito eliminado (Int). Nueva cadena: '$favoriteString'")
    }

    /**
     * Comprueba si un ID (entero) está en la lista de favoritos.
     */
    fun isFavoriteInt(recipeId: Int): Boolean {
        val isFav = getFavoriteInts().contains(recipeId)
        Log.i("SessionManager", "Comprobando si '$recipeId' es favorito (Int): $isFav")
        return isFav
    }
}


//
//package com.example.recetas00.utils
//
//import android.content.Context
//import android.content.SharedPreferences
//import android.util.Log
//
//
//class SessionManager(context: Context) {
//
//    val sharedPreferences: SharedPreferences = context.getSharedPreferences("recipe_session", Context.MODE_PRIVATE)
//
//
//    fun setFavorite(recipeId: Int) {
//        val editor = sharedPreferences.edit()
//        editor.putInt("FAVORITE_RECIPE_ID", recipeId)
//        editor.apply()
//        Log.i("Favorite", "Grabado -> " + recipeId.toString() + " | " + getFavorite().toString())
//
//    }
//
//    fun getFavorite(): Int {
//        return sharedPreferences.getInt("FAVORITE_RECIPE_ID", -1)!!
//    }
//
//    fun isFavorite(recipeId: Int): Boolean {
//        Log.i("Favorite", "Comprobando si es favorita -> " + recipeId.toString() + " == " + getFavorite().toString())
//        return recipeId == getFavorite()
//    }
//}
//

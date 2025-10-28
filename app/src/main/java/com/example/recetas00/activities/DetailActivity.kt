package com.example.recetas00.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recetas00.R
import com.example.recetas00.data.Recipe
import com.example.recetas00.data.RecipeService
import com.example.recetas00.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.core.content.ContextCompat
import com.example.recetas00.databinding.ItemMealTypeBinding
import com.example.recetas00.utils.SessionManager

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_RECIPE_ID = "RECIPE_ID"
    }
    lateinit var binding: ActivityDetailBinding
    lateinit var session: SessionManager

    lateinit var recipe: Recipe

    var showMoreEnabled = false
    var showMore2Enabled = false

    var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val id=intent.getIntExtra(EXTRA_RECIPE_ID,-1)
        getRecipe(id)
    }

    fun getRecipe(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = RecipeService.getInstance()
                recipe = service.getRecipeById(id)
                CoroutineScope(Dispatchers.Main).launch {
                    loadData()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadData() {
        // Images
        Picasso.get().load(recipe.image).into(binding.thumbnailImageView)

        // Basic info
        supportActionBar?.title = recipe.name
        supportActionBar?.subtitle = recipe.cuisine
        binding.titleTextView.text = recipe.name
        binding.cuisineTextView.text = recipe.cuisine
        binding.difficulty.text = recipe.difficulty

        binding.cookTimeMinutes.text = " " + (recipe.cookTimeMinutes).toString() + " + " + (recipe.prepTimeMinutes).toString() + " + " + (recipe.servings).toString() + " min"
        binding.ingredientsTextView.text = recipe.ingredients.joinToString("\n") { "● ${it}" }
        binding.showMoreTextView.setOnClickListener {
            if (showMoreEnabled) {
                binding.ingredientsTextView.maxLines = 2
                binding.showMoreTextView.text = "Ver más..."
            } else {
                binding.ingredientsTextView.maxLines = Int.MAX_VALUE
                binding.showMoreTextView.text = "Ver menos..."
            }
            showMoreEnabled = !showMoreEnabled
        }
        binding.instructionsTextView.text = recipe.instructions.mapIndexed { index, instruction -> "${index + 1}) $instruction" }.joinToString("\n\n")

        binding.cardViewInstrucciones.setOnClickListener {
            if (showMore2Enabled) {
                binding.instructionsTextView.maxLines = 5
            } else {
                binding.instructionsTextView.maxLines = Int.MAX_VALUE
            }
            showMore2Enabled = !showMore2Enabled
        }
        binding.caloriesPerServingTextView.text = recipe.caloriesPerServing.toString() + " Kcal"
        binding.tagsTextView.text = recipe.tags.joinToString(", ")
        //binding.mealTypeTextView.text = recipe.mealType.joinToString(" | ")

        recipe.mealType.forEach { mealType ->
            val chipBinding = ItemMealTypeBinding.inflate(layoutInflater, binding.mealTypeContainer, false)
            chipBinding.mealTypeChip.text = mealType
            chipBinding.mealTypeChip.setOnClickListener {
                Toast.makeText(this, "Has pulsado ${mealType}", Toast.LENGTH_SHORT).show()
            }
            binding.mealTypeContainer.addView(chipBinding.root)
        }



        binding.shareButton.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.setAction(Intent.ACTION_SEND)
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Mira qué podemos comer hoy: ${recipe.image}")
            sendIntent.setType("text/plain")

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        session = SessionManager(this)
        isFavorite = session.isFavoriteInt(recipe.id)
        setFavoriteIcon()


        binding.favoriteButton.setOnClickListener {
            checkFavorite()
            isFavorite = !isFavorite
            setFavoriteIcon()

        }

    }

    fun checkFavorite() {
        if (!isFavorite) {
            session.addFavoriteInt(recipe.id)
            Log.i("Favorite", "Marcado como favorita")
        } else {
            session.removeFavoriteInt(recipe.id)
            Log.i("Favorite", "Eliminado como favorita")
        }
    }


    fun setFavoriteIcon() {
        if (isFavorite) {
            binding.favoriteButton.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_selected))
            Log.i("Favorite", "Es favorita: " + isFavorite.toString())
        } else {
            binding.favoriteButton.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite))
            Log.i("Favorite", "No es favorita: " + isFavorite.toString())
        }
    }



}
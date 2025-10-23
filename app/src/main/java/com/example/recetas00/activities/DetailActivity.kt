package com.example.recetas00.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
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

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_RECIPE_ID = "RECIPE_ID"
    }
    lateinit var binding: ActivityDetailBinding

    lateinit var recipe: Recipe

    var showMoreEnabled = false

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
        // Basic info
        // supportActionBar?.title = recipe.title
        // supportActionBar?.subtitle = recipe.developer
        binding.titleTextView.text = recipe.name
        binding.cuisineTextView.text = recipe.cuisine
        binding.showMoreTextView.setOnClickListener {
            if (showMoreEnabled) {
                binding.ingredientsTextView.maxLines = 5
                binding.showMoreTextView.text = "Ver más..."
            } else {
                binding.ingredientsTextView.maxLines = Int.MAX_VALUE
                binding.showMoreTextView.text = "Ver menos..."
            }
            showMoreEnabled = !showMoreEnabled
        }

        // Additional info
//        binding.genreChip.text = recipe.genre
//        when (recipe.platform) {
//            "PC (Windows)" -> binding.platformButton.setIconResource(R.drawable.ic_desktop_windows)
//            "Web Browser" -> binding.platformButton.setIconResource(R.drawable.ic_web)
//        }

        // Video
/*        binding.trailerVideoView.setVideoURI("https://www.freetogame.com/g/${game.id}/videoplayback.webm".toUri())
        binding.trailerVideoView.setOnPreparedListener {
            CoroutineScope(Dispatchers.IO).launch {
                delay(3000)
                CoroutineScope(Dispatchers.Main).launch {
                    binding.thumbnailImageView.visibility = View.GONE
                    binding.trailerVideoView.start()
                }
            }
        }

        binding.trailerVideoView.setOnCompletionListener {
            binding.thumbnailImageView.visibility = View.VISIBLE
        }
*/
        // Images
        Picasso.get().load(recipe.image).into(binding.thumbnailImageView)
/*
        val adapter = GalleryAdapter(recipe.screenshots, -1) { position ->
            val intent = Intent(this, GalleryActivity::class.java)
            intent.putExtra(GalleryActivity.EXTRA_SCREENSHOT_INDEX, position)
            intent.putExtra(GalleryActivity.EXTRA_SCREENSHOTS_ARRAY, recipe.screenshots.map { it.image }.toTypedArray())
            startActivity(intent)
        }
        binding.screenshotsRecyclerView.adapter = adapter
        binding.screenshotsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
*/
        // Buttons
//        binding.playButton.setOnClickListener {
//            val browserIntent = Intent(Intent.ACTION_VIEW, recipe.recipeUrl.toUri())
//            startActivity(browserIntent)
//        }
        binding.shareButton.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.setAction(Intent.ACTION_SEND)
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Mira qué podemos comer hoy: ${recipe.image}")
            sendIntent.setType("text/plain")

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        // Min system requirements
//        binding.osTextView.text = recipe.minSystemRequirements?.os ?: "-----"
//        binding.processorTextView.text = recipe.minSystemRequirements?.processor ?: "-----"
//        binding.memoryTextView.text = recipe.minSystemRequirements?.memory ?: "-----"
//        binding.graphicsTextView.text = recipe.minSystemRequirements?.graphics ?: "-----"
//        binding.storageTextView.text = recipe.minSystemRequirements?.storage ?: "-----"
    }


}
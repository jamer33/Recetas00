package com.example.recetas00.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recetas00.R
import com.example.recetas00.adapters.RecipeAdapter
import com.example.recetas00.data.Recipe
import com.example.recetas00.data.RecipeService
import com.example.recetas00.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var adapter: RecipeAdapter

    var filteredRecipeList: List<Recipe> = emptyList()
    var originalRecipeList: List<Recipe> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = RecipeAdapter(emptyList()) { position ->
            val recipe = adapter.items[position]
//            Intent(this, DetailActivity::class.java)
//            intent.putExtra(DetailActivity.EXTRA_RECIPE_ID, recipe.id)
//            startActivity(intent)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 1)

    }

    fun getRecipeList() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = RecipeService.getInstance()
                originalRecipeList = service.getAllRecipes()
                filteredRecipeList = originalRecipeList
                CoroutineScope(Dispatchers.Main).launch {
                    adapter.updateItems(filteredRecipeList)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
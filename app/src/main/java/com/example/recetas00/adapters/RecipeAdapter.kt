package com.example.recetas00.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recetas00.data.Recipe
import com.example.recetas00.databinding.ItemRecipeBinding

class RecipeAdapter(
    var items: List<Recipe>,
    val onClickListener: (Int) -> Unit
) : RecyclerView.Adapter<RecipeViewHolder>() {

    // Cual es la vista para los elementos
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRecipeBinding.inflate(layoutInflater, parent, false)
        return RecipeViewHolder(binding)
    }

    // Cuales son los datos para el elemento
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = items[position]
        holder.render(item)
        holder.itemView.setOnClickListener {
            onClickListener(position)
        }
    }

    // Cuantos elementos se quieren listar?
    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(items: List<Recipe>) {
        this.items = items
        notifyDataSetChanged()
    }
}

class RecipeViewHolder(val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {

    fun render(recipe: Recipe) {
        binding.nameTextView.text = recipe.name
        binding.difficulty.text = recipe.difficulty
        binding.cuisineTextView.text = recipe.cuisine
//        when (recipe.platform) {
//            "PC (Windows)" -> binding.platformButton.setIconResource(R.drawable.ic_desktop_windows)
//            "Web Browser" -> binding.platformButton.setIconResource(R.drawable.ic_web)
//        }
//        Picasso.get().load(recipe.thumbnail).placeholder(R.drawable.bg_image_placeholder).into(binding.thumbnailImageView)
    }
}
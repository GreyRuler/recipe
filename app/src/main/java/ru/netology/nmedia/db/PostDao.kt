package ru.netology.nmedia.db

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.netology.nmedia.db.entity.CookingStageEntity
import ru.netology.nmedia.db.entity.RecipeEntity
import ru.netology.nmedia.db.entity.RecipeWithCookingStagesEntity


@Dao
interface PostDao {
    @Transaction
    @Query("SELECT * FROM recipes")
    fun getRecipeWithCookingStages(): LiveData<List<RecipeWithCookingStagesEntity>>

    @Transaction
    fun insert(recipeWithCookingStagesEntity: RecipeWithCookingStagesEntity) {
        val insertedRecipeId = insert(recipeWithCookingStagesEntity.recipe)
        recipeWithCookingStagesEntity.cookingStages.forEach {
            insert(it.copy(recipeId = insertedRecipeId))
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: RecipeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cookingStages: CookingStageEntity): Long

    @Query(
        """
        UPDATE recipes SET
        favorite = CASE WHEN favorite THEN 0 ELSE 1 END
        WHERE id = :recipesId
        """
    )
    fun likeById(recipesId: Long)

    @Query("DELETE FROM recipes WHERE id = :id")
    fun removeById(id: Long)

    @Query("SELECT id FROM cookingStages ORDER BY id DESC LIMIT 1")
    fun getMaxIdCookingStage(): Long

    @Query("SELECT id FROM recipes ORDER BY id DESC LIMIT 1")
    fun getMaxIdRecipe(): Long
}
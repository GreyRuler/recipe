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
    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    fun getRecipe(recipeId: Long): RecipeWithCookingStagesEntity

//    @Query("SELECT * FROM recipes WHERE recipeId = :recipeId")
//    fun getCookingStages(recipeId: Long): LiveData<List<CookingStageEntity>>

//    @Insert
//    fun insert(recipeWithCookingStagesEntity: RecipeWithCookingStagesEntity)

    @Transaction
    fun insert(recipeWithCookingStagesEntity: RecipeWithCookingStagesEntity) {
        insert(recipeWithCookingStagesEntity.recipe)
        recipeWithCookingStagesEntity.cookingStages.forEach {
            insert(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(recipe: RecipeEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cookingStage: CookingStageEntity): Long

    @Query("UPDATE recipes SET author = :author, nameRecipe = :nameRecipe, category = :category WHERE id = :id")
    fun updateContentById(id: Long, author: String, nameRecipe: String, category: String)

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
}
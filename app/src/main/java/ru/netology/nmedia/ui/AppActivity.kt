package ru.netology.nmedia.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import ru.netology.nmedia.adapter.AppViewPagerAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding


class AppActivity : AppCompatActivity() {

    private val fragments = listOf(
        NavigationHostFragment.newInstance(),
        RecipeFragment.newInstance()
    )
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = AppViewPagerAdapter(this, fragments)
        binding.viewPagerApp.adapter = adapter
        TabLayoutMediator(binding.tabLayoutApp, binding.viewPagerApp) { tab, position ->
            tab.text = when(position) {
                0 -> "Первый"
                1 -> "Второй"
                else -> throw IllegalStateException()
            }
        }.attach()
    }


}
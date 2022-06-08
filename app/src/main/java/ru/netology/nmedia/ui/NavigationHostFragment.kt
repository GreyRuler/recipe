package ru.netology.nmedia.ui

import androidx.fragment.app.Fragment
import ru.netology.nmedia.R

class NavigationHostFragment : Fragment(R.layout.navigation_host_fragment) {

    companion object {
        @JvmStatic
        fun newInstance() = NavigationHostFragment()
    }
}
package ru.netology.nmedia.data

import kotlinx.serialization.Serializable

@Serializable
enum class Categories(val title: String) {
    European("Европейская")
}
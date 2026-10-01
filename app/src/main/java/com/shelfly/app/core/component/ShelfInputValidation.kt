package com.shelfly.app.core.component

object ShelfInputValidation {
    fun isValidName(name: String): Boolean = name.trim().isNotEmpty()
}

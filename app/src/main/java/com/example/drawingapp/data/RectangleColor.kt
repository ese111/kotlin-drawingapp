package com.example.drawingapp.data

import kotlin.random.Random

class RectangleColor(
    val red: Int = Random.nextInt(255),
    val green: Int = Random.nextInt(255),
    val blue: Int = Random.nextInt(255)
)
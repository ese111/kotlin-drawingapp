package com.example.drawingapp

import android.graphics.Rect

interface InputFactory {
    val count: Int
    val randomId: String
    val pointX: Int
    val pointY: Int
    val colorR: Int
    val colorG: Int
    val colorB: Int
    val alpha: Int

    fun getRect(): Rect
}
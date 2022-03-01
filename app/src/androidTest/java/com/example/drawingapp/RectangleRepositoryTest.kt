package com.example.drawingapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.drawingapp.data.*
import com.example.drawingapp.util.generateRandom
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RectangleRepositoryTest {
    @Test
    fun getString(){
        val rectangleRepository = RectangleRepository()
        val rectangleCount = rectangleRepository.count +1
        val randomId = "fxd-0fz-4b9-fadfafs"
        rectangleRepository.putId(randomId)
        val id = rectangleRepository.getId()
        val point = RectanglePoint(10, 200)
        val size = RectangleSize()
        val color = RectangleColor(245,0,245)
        val alpha = RectangleAlpha(9).alpha
        val rectangle = Rectangle(rectangleCount, id, point, size, color, alpha)
        Assert.assertEquals("Rect1 (fxd-0fz-4b9), X:10,Y:200, W150, H120, R:245, G:0, B:245, Alpha: 9",
        rectangle.toString()
            )
    }
}
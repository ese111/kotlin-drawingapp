package com.example.drawingapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.drawingapp.data.RectangleRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

class TextInput(
    override val count: Int = 1,
    override val randomId: String = "fxd-0fz-4b9-fadfafs",
    override val pointX: Int = 10,
    override val pointY: Int = 200,
    override val colorR: Int = 245,
    override val colorG: Int = 0,
    override val colorB: Int = 245,
    override val alpha: Int = 9
): InputFactory

@RunWith(AndroidJUnit4::class)
class RectangleRepositoryTest {

    @Test
    fun getString(){
        val rectangleRepository = RectangleRepository()
        val testInput = TextInput()
        assertEquals("Rect1 (fxd-0fz-4b9), X:10,Y:200, W150, H120, R:245, G:0, B:245, Alpha: 9",
            rectangleRepository.getRectangleLog(rectangleRepository.getRectangle(testInput)))
    }

}
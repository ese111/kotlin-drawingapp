package com.example.drawingapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.drawingapp.data.attribute.Id
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IdListTest {
    @Test
    fun makeRandomId_테스트_맞는_값() {
        val rect = Id()
        val randomId = "550e8400-e29b-41d4-a716-446655440000"
        Assert.assertEquals("550-e84-00e", rect.makeRandomId(randomId))
    }

    @Test
    fun checkId_중복아닐때() {
        val rect = Id()
        Assert.assertEquals(true, rect.checkId("rr"))
    }

    @Test
    fun checkId_중복일때() {
        val rect = Id()
        rect.putId("rr")
        Assert.assertEquals(false, rect.checkId("rr"))
    }

    @Test
    fun getId_있을때() {
        val rect = Id()
        rect.putId("rr")
        Assert.assertEquals("rr", rect.getId())
    }

    @Test
    fun getId_없을때() {
        val rect = Id()
        Assert.assertEquals("사각형이 없습니다.", rect.getId())
    }

}
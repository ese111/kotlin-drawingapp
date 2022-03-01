package com.example.drawingapp.data

import com.example.drawingapp.util.generateRandom

class RectangleRepository {
    val count = 0
    private val rectangleId: RectangleId = RectangleId()

    fun getId() = rectangleId.getId()

    fun putId(randomId: String = generateRandom()) {
        while (true) {
            val id = rectangleId.makeRandomId(randomId)
            if(rectangleId.checkId(id)) {
                rectangleId.putId(id)
                return
            }
        }
    }

}
package com.example.drawingapp.data

import java.util.*

class RectangleRepository : RectangleData {
    override fun setId(): String {
        val id = UUID.randomUUID().toString()
        if(RectangleId().checkId(id)) {
            RectangleId().putId(id)
            return id
        }
        return "id 생성 실패"
    }

    override fun setPoint() {
        TODO("Not yet implemented")
    }

    override fun setColor() {
        TODO("Not yet implemented")
    }

    override fun setSize() {
        TODO("Not yet implemented")
    }

    override fun getRectangleInfo() {
        TODO("Not yet implemented")
    }
}
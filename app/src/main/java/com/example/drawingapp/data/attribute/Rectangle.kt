package com.example.drawingapp.data.attribute

import android.graphics.Point
import android.graphics.Rect
import com.example.drawingapp.data.Type
import com.example.drawingapp.data.input.InputType
import com.example.drawingapp.util.generateRandom
import kotlin.random.Random

class Rectangle(
    override val number: Int,
    private val rectangleId: String,
    private val rectanglePoint: Point,
    val color: Color,
    override var alpha: Int,
    override val size: Size,
    override val rect: Rect,
    override val type: InputType = InputType.RECTANGLE,
    override var click: Boolean = false,
    override val point: Point = Point(rect.left, rect.bottom)
) : Type {

    override fun deepCopy(): Type {
        val num: Int = number
        val id: String = rectangleId
        val rectPoint = Point(rectanglePoint.x, rectanglePoint.y)
        val rectColor = Color(this.color.red, this.color.green, this.color.blue)
        val alpha: Int = this.alpha
        val size = Size(this.size.width, this.size.height)
        val rect = Rect(this.rect.left, this.rect.top, this.rect.right, this.rect.bottom)
        val type: InputType = InputType.RECTANGLE
        val click = false
        val point = Point(this.rect.left, this.rect.bottom)
        return Rectangle(num, id, rectPoint, rectColor, alpha, size, rect, type, click, point)
    }

    override fun toString() =
        "Rect${number} (${rectangleId}), X:${rectanglePoint.x},Y:${rectanglePoint.y}, W${size.width}, H${size.height}, " +
                "R:${color.red}, G:${color.green}, B:${color.blue}, Alpha: $alpha"

    companion object Factory{
        fun make(count: Int, id: Id) : Rectangle {
            putId(id)
            val point = Point(Random.nextInt(2000), Random.nextInt(1000))
            return Rectangle(
                count,
                id.getId(),
                point,
                Color(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255)),
                Random.nextInt(10),
                Size(),
                getRect(point))	// companion object의 bar() 메서드를 통해 private 생성자 접근 가능
        }

        private fun putId(id: Id) {
            while (true) {
                val picId = id.makeRandomId(generateRandom())
                if (id.checkId(picId)) {
                    id.putId(picId)
                    return
                }
            }
        }

        private fun getRect(point: Point): Rect {
            return Rect(point.x, point.y + Size().height, point.x + Size().width, point.y)
        }
    }
}
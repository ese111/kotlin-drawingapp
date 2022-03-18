package com.example.drawingapp.data.attribute

import android.graphics.*
import com.example.drawingapp.data.Type
import com.example.drawingapp.data.input.InputType
import com.example.drawingapp.util.generateRandom
import kotlin.random.Random

class Picture(
    override val number: Int,
    private val id: String,
    var bitmap: Bitmap,
    override var point: Point,
    override var alpha: Int,
    override val size: Size,
    override val rect: Rect,
    override val type: InputType = InputType.PICTURE,
    override var click: Boolean = false
) : Type {

    companion object Factory{
        fun make(count: Int, id: Id, bitmap: Bitmap) : Picture {
            putId(id)
            val point = Point(Random.nextInt(2000), Random.nextInt(1000))
            return Picture(
                count,
                id.getId(),
                bitmap,
                point,
                Random.nextInt(10),
                Size(),
                getRect(point))
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

    override fun deepCopy(): Type {
        val number: Int = this.number
        val id: String = this.id
        val bitmap: Bitmap = this.bitmap
        val point = Point(this.point.x, this.point.y)
        val alpha: Int = this.alpha
        val size = this.size.copy()
        val rect = Rect(this.rect.left, this.rect.top, this.rect.right, this.rect.bottom)
        val type: InputType = this.type
        val click = this.click
        return Picture(number, id, bitmap, point, alpha, size, rect, type, click)
    }

    override fun toString() =
        "Rect${number} (${id}), X:${rect.top},Y:${rect.bottom}, W${rect.right}, H${rect.left}, " +
                "bitmap:${bitmap}, Alpha: $alpha"

}
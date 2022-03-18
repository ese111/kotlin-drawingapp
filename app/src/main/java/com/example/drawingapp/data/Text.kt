package com.example.drawingapp.data

import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.util.Log
import com.example.drawingapp.data.attribute.Color
import com.example.drawingapp.data.attribute.Id
import com.example.drawingapp.data.attribute.Size
import com.example.drawingapp.data.input.InputType
import com.example.drawingapp.util.generateRandom
import kotlin.random.Random

class Text(
    override val number: Int,
    private val id: String,
    val color: Color,
    val value: String,
    var textSize: Float,
    override val rect: Rect,
    override val point: Point,
    override val size: Size,
    override val type: InputType = InputType.TEXT,
    override var click: Boolean = false,
    override var alpha: Int = Random.nextInt(1,11)
) : Type {
    companion object Factory {

        fun make(count: Int, id: Id): Text {
            putId(id)
            val point = Point(Random.nextInt(100, 1000), Random.nextInt(100, 1500))
            val text = createText()
            val size = getSize(text, 120F)
            val rect = getRect(point, size)
            return Text(
                count,
                id.getId(),
                Color(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255)),
                text,
                120F,
                rect,
                point,
                size,
            )
        }

        private val text = "Lorem ipsum dolor sit amet, " +
                "consectetur adipiscing elit, " +
                "sed do eiusmod tempor incididunt ut " +
                "labore et dolore magna aliqua. Egestas " +
                "tellus rutrum tellus pellentesque eu. Viverra " +
                "justo nec ultrices dui sapien eget mi proin sed. " +
                "Vel pretium lectus quam id leo. Molestie at elementum " +
                "eu facilisis sed odio morbi quis commodo. Risus at ultrices" +
                " mi tempus imperdiet nulla malesuada. In est ante in nibh mauris " +
                "cursus mattis molestie a. Venenatis urna cursus eget nunc. Eget velit " +
                "aliquet sagittis id consectetur purus ut. Amet consectetur " +
                "adipiscing elit pellentesque habitant morbi tristique senectus. " +
                "Consequat nisl vel pretium lectus quam id. Nisl vel pretium " +
                "lectus quam id leo in vitae turpis. Purus faucibus ornare " +
                "suspendisse sed. Amet mauris commodo quis imperdiet."

        private val textList = text.split(" ")

        private fun createText(): String {
            val range = textList.size
            val startNumber = Random.nextInt(range)
            var textToBeCreated = ""
            for (i in 0 until 5) {
                textToBeCreated += textList[startNumber + i]
                textToBeCreated += " "
            }
            return textToBeCreated
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

        fun getRect(point: Point, size: Size) = Rect(
            point.x,
            point.y - size.height,
            point.x + size.width,
            point.y
        )

        fun getSize(text: String, size: Float): Size {
            val rect = Rect()
            val paint = Paint().apply {
                textSize = size
            }
            paint.getTextBounds(text, 0, text.length, rect)
            return Size(rect.width(), rect.height())
        }
    }


    override fun deepCopy(): Type {
        val number = this.number
        val id = this.id
        val backGround = this.color
        val text = this.value
        val rect = this.rect
        val point = Point(this.point.x, this.point.y)
        val size = Size(this.size.width, this.size.height)
        val type: InputType = this.type
        val click: Boolean = this.click
        val alpha: Int = this.alpha
        return Text(number, id, backGround, text, 120F,rect, point, size, type, click, alpha)
    }

}
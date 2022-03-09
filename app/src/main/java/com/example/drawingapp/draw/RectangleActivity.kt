package com.example.drawingapp.draw

import android.annotation.SuppressLint
import android.graphics.*
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.MotionEvent
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.drawingapp.Contract
import com.example.drawingapp.R
import com.example.drawingapp.data.RectangleRepository
import com.example.drawingapp.data.Type
import com.example.drawingapp.data.attribute.Picture
import com.example.drawingapp.data.attribute.Rectangle
import com.example.drawingapp.data.attribute.RectangleColor
import com.example.drawingapp.util.showSnackBar
import com.google.android.material.slider.Slider
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger


class RectangleActivity : AppCompatActivity(), Contract.View {

    private lateinit var presenter: Contract.Presenter
    private lateinit var draw: RectangleDraw
    private lateinit var slider: Slider
    private var choiceRect = -1
    private val rect = ListLiveData<Rect>()
    private val paints = ListLiveData<Paint>()
    private val pictures = ListLiveData<Picture>()
    private val rectangleColor = mutableListOf<String>()

    @SuppressLint("Range")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Logger.addLogAdapter(AndroidLogAdapter())
        draw = findViewById(R.id.draw_rectangle)
        presenter = RectanglePresenter(this, RectangleRepository())
//        presenter.onClickLog()
//        presenter.onClickLog()
//        presenter.onClickLog()
//        presenter.onClickLog()
        val getContent = selectPicture()
        val drawButton: Button = findViewById(R.id.btn_make_rectangle)
        val picButton: Button = findViewById(R.id.add_pic_btn)
        picButton.text = "사진 추가"
        drawButton.text = "사각형"

        picButton.setOnClickListener {
            getContent.launch("image/*")
        }

        drawButton.setOnClickListener() {
//            presenter.onClickLog()
            presenter.setPlane()
            presenter.getDrawRectangle()
        }

        slider = findViewById(R.id.slider_invisible)

        slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            @SuppressLint("RestrictedApi")
            override fun onStartTrackingTouch(slider: Slider) {

            }

            @SuppressLint("RestrictedApi")
            override fun onStopTrackingTouch(slider: Slider) {
                if (slider.value < 10F) {
                    slider.value = 10F
                }
                if (choiceRect == -1) {
                    findViewById<ConstraintLayout>(R.id.container).showSnackBar("선택된 사각형이 없습니다.")
                    slider.value = 1F
                    return
                }
                var alpha = slider.value / 10
                setAlpha(choiceRect, alpha.toInt())
                presenter.getDrawRectangle(choiceRect)
            }
        })

        rect.observe(this) {
            draw.invalidate()
        }

        if (choiceRect != -1) {
            paints.observe(this) {
                draw.invalidate()
            }
        }

        pictures.observe(this) {
            draw.invalidate()
        }
    }

    private fun selectPicture() = registerForActivityResult(ActivityResultContracts.GetContent()) {
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, it))
        } else {
            MediaStore.Images.Media.getBitmap(contentResolver, it)
        }
        val scaleBitmap = Bitmap.createScaledBitmap(bitmap, 150, 120, true)
        presenter.setPlane(scaleBitmap)
        presenter.getDrawPicture()
    }

    override fun changeAlpha(type: Type, index: Int) {
        draw.changeAlpha(type, index)
        paints.getList()?.get(index)?.alpha = type.getAlpha() * 25
    }

    override fun setAlpha(index: Int, value: Int) {
        presenter.setAlpha(index, value)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val count = draw.getClickRectangle()
        setColorText(count)
        val pointF = PointF(event!!.x, event!!.y - 176F)
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                onTouchRectangle(pointF)
            }
            MotionEvent.ACTION_MOVE -> {
                onTouchRectangle(pointF)
            }
            else -> {
                draw.performClick()
            }
        }
        draw.invalidate()
        return true
    }

    override fun onTouchRectangle(pointF: PointF) {
        val count = draw.findRectangle(pointF)

        if (count == -1) {
            draw.strokeRectReset()
            return
        }
        choiceRect = draw.getClickRectangle()
        slider.value = presenter.getAlpha(choiceRect) * 10F
    }

    @SuppressLint("SetTextI18n")
    override fun setColorText(count: Int) = when (count != -1) {
        true -> {
            val color: Button = findViewById(R.id.tv_background_color)
            color.text = "#${rectangleColor[count]}"
        }

        false -> {
            val color: Button = findViewById(R.id.tv_background_color)
            color.text = ""
        }
    }

    override fun getDrawMessage(message: String) {
        Logger.i(message)
    }

    override fun drawRectangle(rectangle: Rectangle) {
        rect.add(rectangle.rect)
        draw.setDrawType(rectangle)
        setPaints(rectangle)
        rectangleColor.add(setColor(rectangle.rectangleColor))
    }

    override fun drawPicture(picture: Picture) {
        pictures.add(picture)
        rect.add(picture.rect)
        draw.setDrawType(picture)
        setPaints(picture)
        rectangleColor.add("image")

    }

    private fun setPaints(rectangle: Rectangle) {
        val paint = Paint()

        paint.color = Color.argb(
            rectangle.getAlpha() * 25,
            rectangle.rectangleColor.red,
            rectangle.rectangleColor.green,
            rectangle.rectangleColor.blue
        )

        paint.style = Paint.Style.FILL
        paints.add(paint)
        draw.setPaints(paint)
    }

    private fun setPaints(picture: Picture) {
        val paint = Paint()

        paint.alpha = picture.getAlpha() * 25

        paints.add(paint)
        draw.setPaints(paint)
    }

    private fun setColor(rectangleColor: RectangleColor) = rectangleColor.red.toString(16) +
            rectangleColor.blue.toString(16) +
            rectangleColor.green.toString(16)

}

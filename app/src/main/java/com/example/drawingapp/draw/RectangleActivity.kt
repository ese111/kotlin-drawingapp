package com.example.drawingapp.draw

import android.graphics.*
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.drawingapp.Contract
import com.example.drawingapp.R
import com.example.drawingapp.data.RectangleRepository
import com.example.drawingapp.data.attribute.Picture
import com.example.drawingapp.data.attribute.Rectangle
import com.example.drawingapp.util.showSnackBar
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kotlin.Exception


class RectangleActivity : AppCompatActivity(), Contract.View, View.OnClickListener {

    private lateinit var presenter: Contract.Presenter

    private lateinit var main: ConstraintLayout

    private lateinit var draw: RectangleDraw

    private lateinit var slider: SeekBar

    private val rectangleColor = mutableListOf<String>()

    private var currentRect: Int = -1

    private lateinit var downPointF: PointF

    private lateinit var upPointF: PointF

    private lateinit var addRectButton: Button

    private lateinit var addPictureButton: Button

    private lateinit var positionXValue: TextView
    private lateinit var positionXUp: ImageButton
    private lateinit var positionXDown: ImageButton

    private lateinit var positionYValue: TextView
    private lateinit var positionYUp: ImageButton
    private lateinit var positionYDown: ImageButton

    private lateinit var widthValue: TextView
    private lateinit var widthUp: ImageButton
    private lateinit var widthDown: ImageButton

    private lateinit var heightValue: TextView
    private lateinit var heightUp: ImageButton
    private lateinit var heightDown: ImageButton

    private lateinit var getContent: ActivityResultLauncher<String?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Logger.addLogAdapter(AndroidLogAdapter())

        main = findViewById(R.id.container)
        draw = findViewById(R.id.draw_rectangle)
        presenter = RectanglePresenter(this, RectangleRepository())

        draw.initView(main)
//        presenter.onClickLog()
//        presenter.onClickLog()
//        presenter.onClickLog()
//        presenter.onClickLog()

        getContent = selectPicture()

        addRectButton = findViewById(R.id.btn_make_rectangle)
        addPictureButton = findViewById(R.id.add_pic_btn)

        addRectButton.setOnClickListener(this)
        addPictureButton.setOnClickListener(this)

        positionXValue = findViewById(R.id.x_position)
        positionXUp = findViewById(R.id.x_position_up)
        positionXDown = findViewById(R.id.x_position_down)

        positionXUp.setOnClickListener(this)
        positionXDown.setOnClickListener(this)

        positionYValue = findViewById(R.id.y_position)
        positionYUp = findViewById(R.id.y_position_up)
        positionYDown = findViewById(R.id.y_position_down)

        positionYUp.setOnClickListener(this)
        positionYDown.setOnClickListener(this)

        widthValue = findViewById(R.id.width_value)
        widthUp = findViewById(R.id.width_up)
        widthDown = findViewById(R.id.width_down)

        widthUp.setOnClickListener(this)
        widthDown.setOnClickListener(this)

        heightValue = findViewById(R.id.height_value)
        heightUp = findViewById(R.id.height_up)
        heightDown = findViewById(R.id.height_down)

        heightUp.setOnClickListener(this)
        heightDown.setOnClickListener(this)

        slider = findViewById(R.id.slider_invisible)

        slider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

            }

            override fun onStartTrackingTouch(slider: SeekBar) {

            }

            override fun onStopTrackingTouch(slider: SeekBar) {
                if (slider.progress < 10) {
                    slider.progress = 10
                }
                if (currentRect == -1) {
                    main.showSnackBar("선택된 사각형이 없습니다.")
                    slider.progress = 1
                    return
                }
                val alpha = slider.progress / 10
                changeAlpha(currentRect, alpha)
                setAlpha(currentRect, alpha)
            }
        })

        val plane = presenter.plane()
        plane.list.observe(this) {
            draw.invalidate()
        }
    }

    override fun onClick(view: View?) = when (view?.id) {
        R.id.btn_make_rectangle -> {
//            presenter.onClickLog()
            presenter.setRectangleInPlane()
            presenter.drawRectangle()
        }
        R.id.add_pic_btn -> {
            getContent.launch("image/*")
        }
        R.id.x_position_up -> {
            try {
                changeUpX(1, 0)
            } catch (e: Exception) {
                main.showSnackBar("좌표조정에 실패하였습니다.")
            }
        }
        R.id.x_position_down -> {
            try {
                changeDownX(-1, 0)
            } catch (e: Exception) {
                main.showSnackBar("좌표조정에 실패하였습니다.")
            }
        }
        R.id.y_position_up -> {
            try {
                changeUpY(0, -1)
            } catch (e: Exception) {
                main.showSnackBar("좌표조정에 실패하였습니다.")
            }
        }
        R.id.y_position_down -> {
            try {
                changeDownY(0, 1)
            } catch (e: Exception) {
                main.showSnackBar("좌표조정에 실패하였습니다.")
            }
        }
        R.id.width_up -> {
            try {
                changeUpWidth(1, 0)
            } catch (e: Exception) {
                main.showSnackBar("넓이조정에 실패하였습니다.")
            }
        }
        R.id.width_down -> {
            try {
                changeDownWidth(-1, 0)
            } catch (e: Exception) {
                main.showSnackBar("넓이조정에 실패하였습니다.")
            }
        }
        R.id.height_up -> {
            try {
                changeUpHeight(0, 1)
            } catch (e: Exception) {
                main.showSnackBar("높이조정에 실패하였습니다.")
            }
        }
        R.id.height_down -> {
            try {
                changeDownHeight(0, -1)
            } catch (e: Exception) {
                main.showSnackBar("높이조정에 실패하였습니다.")
            }
        }
        else -> throw IllegalArgumentException("stub!")
    }

    private fun changeUpX(x: Int, y: Int) {
        val typeList = draw.setUpX(x, y)
        draw.resetTemp()
        presenter.setPlaneXY(typeList)
        setSideBar()
        draw.invalidate()
    }

    private fun changeDownX(x: Int, y: Int) {
        val typeList = draw.setDownX(x, y)
        draw.resetTemp()
        presenter.setPlaneXY(typeList)
        setSideBar()
        draw.invalidate()
    }

    private fun changeUpY(x: Int, y: Int) {
        val typeList = draw.setUpY(x, y)
        draw.resetTemp()
        presenter.setPlaneXY(typeList)
        setSideBar()
        draw.invalidate()
    }

    private fun changeDownY(x: Int, y: Int) {
        val typeList = draw.setDownY(x, y)
        draw.resetTemp()
        presenter.setPlaneXY(typeList)
        setSideBar()
        draw.invalidate()
    }

    private fun changeUpWidth(x: Int, y: Int) {
        val typeList = draw.setUpWidth(x, y)
        draw.resetTemp()
        presenter.setPlaneXY(typeList)
        setSideBar()
        draw.invalidate()
    }

    private fun changeDownWidth(x: Int, y: Int) {
        val typeList = draw.setDownWidth(x, y)
        draw.resetTemp()
        presenter.setPlaneXY(typeList)
        setSideBar()
        draw.invalidate()
    }

    private fun changeUpHeight(x: Int, y: Int) {
        val typeList = draw.setUpHeight(x, y)
        draw.resetTemp()
        presenter.setPlaneXY(typeList)
        setSideBar()
        draw.invalidate()
    }

    private fun changeDownHeight(x: Int, y: Int) {
        val typeList = draw.setDownHeight(x, y)
        draw.resetTemp()
        presenter.setPlaneXY(typeList)
        setSideBar()
        draw.invalidate()
    }

    private fun selectPicture() = registerForActivityResult(ActivityResultContracts.GetContent()) {
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, it))
        } else {
            MediaStore.Images.Media.getBitmap(contentResolver, it)
        }
        val scaleBitmap = Bitmap.createScaledBitmap(bitmap, 150, 120, true)
        presenter.setPictureInPlane(scaleBitmap)
        presenter.drawPicture()
    }

    override fun changeAlpha(index: Int, alpha: Int) {
        draw.changeAlpha(index, alpha)
    }

    override fun setAlpha(index: Int, value: Int) {
        presenter.setAlpha(index, value)
    }

    override fun onTouchRectangle(pointF: PointF) {
        currentRect = draw.findRectangle(pointF)
        if (currentRect == -1) {
            draw.setStrokeClean()
            presenter.resetClick()
            draw.resetTemp()
            return
        }
        presenter.setClick(currentRect)
        slider.progress =
            presenter.getAlpha(currentRect)?.times(10)
                ?: throw IllegalArgumentException("stub!")
    }

    private fun setTempSideBar(count: Int) {
        setColorText(count)
        draw.setTempPositionValue(positionXValue, positionYValue)
        draw.setTempSizeValue(widthValue, heightValue)
    }

    override fun setSideBar() {
        setColorText(currentRect)
        draw.setPositionValue(currentRect, positionXValue, positionYValue)
        draw.setSizeValue(currentRect, widthValue, heightValue)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val pointF = PointF(event!!.x, event.y - 176F)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downPointF = PointF(event.x, event.y - 176F)
                onTouchRectangle(downPointF)
                try {
                    setSideBar()
                } catch (e: Exception) {
                    main.showSnackBar("그림을 선택하세요.")
                }
                draw.invalidate()
            }

            MotionEvent.ACTION_MOVE -> {
                val length = event.historySize

                if (length != 0 && currentRect != -1) {
                    val x = (pointF.x - event.getHistoricalX(0)) * 1.5
                    val y = (pointF.y - (event.getHistoricalY(0) - 176)) * 1.5
                    draw.setTempXY(x.toInt(), y.toInt())
                    setTempSideBar(currentRect)
                    draw.invalidate()
                }
            }

            MotionEvent.ACTION_UP -> {
                upPointF = PointF(event.x, event.y - 176F)
                val x = upPointF.x - downPointF.x
                val y = upPointF.y - downPointF.y
                try {
                    changePoint(x.toInt(), y.toInt())
                } catch (e: Exception) {
                    main.showSnackBar("선택 취소")
                }
            }
        }
        return true
    }

    private fun changePoint(x: Int, y: Int) {
        val typeList = draw.setXY(x, y)
        draw.resetTemp()
        presenter.setPlaneXY(typeList)
        setSideBar()
        draw.invalidate()
    }

    override fun drawMessage(message: String) {
        Logger.i(message)
    }

    override fun drawRectangle(rectangle: Rectangle) {
        draw.setDrawType(rectangle)
        setPaints(rectangle)
        rectangleColor.add(setColor(rectangle.color))
    }

    override fun drawPicture(picture: Picture) {
        draw.setDrawType(picture)
        setPaints(picture)
        rectangleColor.add("image")
    }

    private fun setPaints(rectangle: Rectangle) {
        val paint = Paint().apply {
            color = Color.argb(
                rectangle.alpha * 25,
                rectangle.color.red,
                rectangle.color.green,
                rectangle.color.blue
            )
            style = Paint.Style.FILL
        }

        draw.setPaints(paint)
    }

    private fun setPaints(picture: Picture) {
        val paint = Paint().apply { alpha = picture.alpha * 25 }
        draw.setPaints(paint)
    }

    private fun setColor(color: com.example.drawingapp.data.attribute.Color) =
        color.red.toString(16) +
                color.blue.toString(16) +
                color.green.toString(16)

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

}

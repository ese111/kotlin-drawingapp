package com.example.drawingapp.draw

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.drawingapp.Contract
import com.example.drawingapp.R
import com.example.drawingapp.data.RectangleRepository
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger


class RectangleActivity : AppCompatActivity(), Contract.View {

    private lateinit var presenter: Contract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Logger.addLogAdapter(AndroidLogAdapter())
        presenter = RectanglePresenter(this, RectangleRepository())
        val button: Button = findViewById(R.id.btn_make_rectangle)
        presenter.onClickLog()
        presenter.onClickLog()
        presenter.onClickLog()
        presenter.onClickLog()
        button.setOnClickListener {
            presenter.onClickLog()
        }
    }

    override fun getDrawMessage(message: String) {
        Logger.i(message)
    }

}
package com.example.drawingapp

import android.graphics.Canvas
import com.example.drawingapp.data.*
import com.example.drawingapp.draw.RectanglePresenter
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*


class RectanglePresenterTest {

    @Mock
    private lateinit var view: Contract.View

    @Mock
    private lateinit var repository: Repository

    private lateinit var presenter: RectanglePresenter

    @Before
    fun setRectanglePresenter() {
        MockitoAnnotations.openMocks(this)
        presenter = RectanglePresenter(view, repository)
    }

    @Test
    fun test_rectangleLog() {
        presenter.onClickLog()
        verify(view).getDrawMessage(presenter.getRectangleLog())
    }

    @Test
    fun test_onClickDraw() {
        val rectangle = repository.getRectangle(repository.getInputFactory())
        `when`(repository.getPlaneCount()).thenReturn(1)
        `when`(repository.getPlane(0)).thenReturn(rectangle)
        presenter.getDrawRectangle()
        verify(view).drawRectangle(rectangle)
    }

}
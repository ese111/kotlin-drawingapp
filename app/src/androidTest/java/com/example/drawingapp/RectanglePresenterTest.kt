package com.example.drawingapp

import com.example.drawingapp.draw.RectanglePresenter
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.*
import org.mockito.Mockito.verify


class RectanglePresenterTest {

    @Mock
    private lateinit var view: Contract.View

    @Mock
    private lateinit var repository: Contract.Repository

    private lateinit var presenter: RectanglePresenter

    @Before
    fun setRectanglePresenter(){
        MockitoAnnotations.openMocks(this)
        presenter = RectanglePresenter(view, repository)
    }

    @Test
    fun test_drawRectangle(){
        presenter.onClickLog()
        verify(view).getDrawMessage(presenter.getDrawRectangle())
    }
}
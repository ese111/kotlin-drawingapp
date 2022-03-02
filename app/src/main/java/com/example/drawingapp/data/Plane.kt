package com.example.drawingapp.data

class Plane {
//    새로운 사각형을 생성하고 나면 Plane에 추가한다.
    private val rectangleList = mutableListOf<Rectangle>()
//    사각형 전체 개수를 알려주는 메소드 (또는 computed property)가 있다.
    fun getCount() = rectangleList.size
//    index를 넘기면 해당 사각형 모델을 return한다.
    fun getPlane(index : Int) = rectangleList[index]
//    터치 좌표를 넘기면, 해당 위치를 포함하는 사각형이 있는지 판단한다.
    fun onTouchRectangle() {

    }

    fun setPlane(rectangle: Rectangle) {
        rectangleList.add(rectangle)
    }

}
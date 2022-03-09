package com.example.drawingapp.data

class Plane {

    //    새로운 사각형을 생성하고 나면 Plane에 추가한다.
    private var list = mutableListOf<Type>()

    // 라이브 데이터 적용하기
    //    사각형 전체 개수를 알려주는 메소드 (또는 computed property)가 있다.
    fun getCount() = list.size

    //    index를 넘기면 해당 사각형 모델을 return한다.
    fun getPlane(index: Int) = list[index]

    fun setPlane(t: Type) {
        list.add(t)
    }

    fun setAlpha(index: Int, alpha: Int) {
        list[index].setAlpha(alpha)
    }

}
package com.example.drawingapp.data

import android.graphics.Rect
import com.example.drawingapp.draw.ListLiveData

class Plane {

    //    새로운 사각형을 생성하고 나면 Plane에 추가한다.
    val list = ListLiveData<Type>()

    // 라이브 데이터 적용하기
    //    사각형 전체 개수를 알려주는 메소드 (또는 computed property)가 있다.
    fun getCount() = list.getList()?.size

    //    index를 넘기면 해당 사각형 모델을 return한다.
    fun getPlane(index: Int) = list.getList()?.get(index)

    fun setPlane(t: Type) {
        list.add(t)
    }

    fun setAlpha(index: Int, alpha: Int) {
        list.getList()?.get(index)?.setAlpha(alpha)
    }

    fun setXY(index: Int, rect: Rect?) {
        if (rect != null) {
            list.getList()?.get(index)?.rect?.left = rect.left
            list.getList()?.get(index)?.rect?.top = rect.top
            list.getList()?.get(index)?.rect?.right = rect.right
            list.getList()?.get(index)?.rect?.bottom = rect.bottom
        }
    }

}
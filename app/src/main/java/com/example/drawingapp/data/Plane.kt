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
        list.getList()?.get(index)?.alpha = alpha
    }

    fun setXY(typeList: List<Type>) {
        for (i in 0 until list.getList()!!.size) {
            if(list.getList()!![i].click) {
                list.getList()?.get(i)?.point?.x = typeList[i].point.x
                list.getList()?.get(i)?.point?.y = typeList[i].point.y
                list.getList()?.get(i)?.rect?.left = typeList[i].rect.left
                list.getList()?.get(i)?.rect?.top = typeList[i].rect.top
                list.getList()?.get(i)?.rect?.right = typeList[i].rect.right
                list.getList()?.get(i)?.rect?.bottom = typeList[i].rect.bottom
            }
        }
    }
//    fun setXY(index: Int, rect: Rect?) {
//        list.getList()?.filter { it.click }?.forEach {
//
//        }
//        if (rect != null) {
//            list.getList()?.get(index)?.rect?.left = rect.left
//            list.getList()?.get(index)?.rect?.top = rect.top
//            list.getList()?.get(index)?.rect?.right = rect.right
//            list.getList()?.get(index)?.rect?.bottom = rect.bottom
//        }
//    }

    fun resetClick() {
        list.getList()?.forEach {
            it.click = false
        }
    }

}
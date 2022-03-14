package com.example.drawingapp.data

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
            if (list.getList()!![i].click){
                list.getList()!![i].point?.x = typeList[i].point.x
                list.getList()!![i].point?.y = typeList[i].point.y
                list.getList()!![i].rect?.left = typeList[i].rect.left
                list.getList()!![i].rect?.top = typeList[i].rect.top
                list.getList()!![i].rect?.right = typeList[i].rect.right
                list.getList()!![i].rect?.bottom = typeList[i].rect.bottom
            }
        }
    }

    fun resetClick() {
        list.getList()?.forEach {
            it.click = false
        }
    }
}
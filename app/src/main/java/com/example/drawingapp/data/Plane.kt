package com.example.drawingapp.data

import com.example.drawingapp.draw.ListLiveData

class Plane {

    val list = ListLiveData<Type>()

    fun getCount() = list.getList()?.size

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
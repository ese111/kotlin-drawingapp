package com.example.drawingapp.data

class RectangleId {
    companion object {
        private val idSet = HashSet<String>()
    }

    fun putId(id: String) = idSet.add(id)

    fun checkId(id: String) = !idSet.contains(id)

    fun getId() = idSet.elementAt(idSet.size-1)
}
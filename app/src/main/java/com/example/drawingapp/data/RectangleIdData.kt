package com.example.drawingapp.data

class RectangleIdData {
    companion object {
        private val idSet = HashSet<String>()
        fun putId(id: String) = when(idSet.contains(id)) {
            true -> {
                idSet.add(id)
                true
            }
            else -> false
        }
    }
}
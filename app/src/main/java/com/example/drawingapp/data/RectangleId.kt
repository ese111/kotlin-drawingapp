package com.example.drawingapp.data


class RectangleId {
    private val idSet = mutableSetOf<String>()

    fun putId(id: String) = idSet.add(id)

    fun checkId(id: String) = !idSet.contains(id)

    fun getId() =
        when (idSet.isEmpty()) {
            true -> "사각형이 없습니다."
            false -> {
                idSet.elementAt(idSet.size - 1)
            }
        }

    fun makeRandomId(randomId: String): String {
        val getRandomStr = randomId.replace("-", "").chunked(9)
        val str = changeStringToStringBuilder(getRandomStr[0])
        return setId(str)
    }

    private fun changeStringToStringBuilder(id: String) = StringBuilder(id)

    private fun setId(id: StringBuilder): String {
        id.insert(3, "-")
        id.insert(7, "-")
        return id.toString()
    }

}
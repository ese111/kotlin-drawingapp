package com.example.drawingapp.draw

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.drawingapp.R
import com.example.drawingapp.data.Type
import com.example.drawingapp.data.input.InputType

class DrawAdapter(private val data: List<Type>?) : RecyclerView.Adapter<DrawAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.type_image)
        val textView: TextView = view.findViewById(R.id.type_name)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.draw_list, viewGroup, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(data?.get(position)?.type) {
            InputType.RECTANGLE -> {
                holder.imageView.setImageResource(R.drawable.baseline_crop_square_24)
                holder.textView.text = "Rect${data[position].number}"
            }

            InputType.PICTURE -> {
                holder.imageView.setImageResource(R.drawable.baseline_image_20)
                holder.textView.text = "Pic${data[position].number}"
            }

            InputType.TEXT -> {
                holder.imageView.setImageResource(R.drawable.baseline_title_24)
                holder.textView.text = "Text${data[position].number}"
            }
            else -> {
                Log.d("new dataType" , "new Type!! ${data?.get(position)?.type}")
            }
        }
    }

    override fun getItemCount() = data!!.size
}
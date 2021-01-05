package com.example.madcapstoneprojectmazegame.item

import android.content.Context
import android.graphics.Paint
import android.graphics.Point
import androidx.core.content.ContextCompat
import com.example.madcapstoneprojectmazegame.Dot
import com.example.madcapstoneprojectmazegame.R


class Player(context: Context, start: Point, size: Int) : Dot(size, start, getPaint(context)) {
    companion object {
        private fun getPaint(context: Context): Paint {
            val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.color = ContextCompat.getColor(context, R.color.colorPlayer)
            return paint
        }
    }
}
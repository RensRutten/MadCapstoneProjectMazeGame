package com.example.madcapstoneprojectmazegame

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import java.util.*

open class Dot(private val size: Int, var point: Point, var paint: Paint) : Drawable {

    fun goTo(x: Int, y: Int) {
        point.x = x
        point.y = y
    }

    val x: Int
        get() = point.x
    val y: Int
        get() = point.y

    override fun draw(canvas: Canvas, rect: Rect) {
        val cellSize  : Float = ((rect.right - rect.left) / size).toFloat()
        canvas.drawRect(
            rect.left + point.x * cellSize,
            rect.top + point.y * cellSize,
            rect.left + point.x * cellSize + cellSize,
            rect.top + point.y * cellSize + cellSize,
            paint)
    }
}
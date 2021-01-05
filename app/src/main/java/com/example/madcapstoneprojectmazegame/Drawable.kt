package com.example.madcapstoneprojectmazegame

import android.graphics.Canvas
import android.graphics.Rect

interface Drawable {
    fun draw(canvas: Canvas, rect: Rect)
}
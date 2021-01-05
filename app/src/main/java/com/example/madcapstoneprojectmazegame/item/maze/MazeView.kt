package com.example.madcapstoneprojectmazegame.item.maze

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.view.View
import com.example.madcapstoneprojectmazegame.GameManager


@SuppressLint("ViewConstructor")
class MazeView(
    context: Context,
    private val gameManager : GameManager
) : View(context){
    init {
        gameManager.view = this
    }

    override fun onDraw(canvas: Canvas) {
        gameManager.draw(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        gameManager.setScreenSize(w,h)
    }
}
package com.example.madcapstoneprojectmazegame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import com.example.madcapstoneprojectmazegame.item.Bonus
import com.example.madcapstoneprojectmazegame.item.Player
import com.example.madcapstoneprojectmazegame.item.maze.Maze
import java.util.*
import kotlin.math.min


class GameManager(val context: Context) {
    private var drawables : ArrayList<Drawable> = ArrayList()
    lateinit var view : View
    lateinit var player : Player
    lateinit var maze : Maze
    lateinit var bonus: Bonus
    var score: Int = 0
    private var rect : Rect = Rect()
    init {
        create(5)
        Control(this)
    }

    fun create(size: Int) {
        drawables.clear()

        maze = Maze(context, size)
        drawables.add(maze)

        player = Player(context, maze.start, size)
        drawables.add(player)

        bonus = Bonus(this, size)
        drawables.add(bonus)
    }

    fun draw(canvas: Canvas){
        for(drawableItem in drawables){
            drawableItem.draw(canvas, rect)
        }
    }

    fun setScreenSize(width: Int, height: Int) {
        val screenSize = min(width, height)
        rect.set(
            (width - screenSize) / 2,
            (height - screenSize) / 2,
            (width + screenSize) / 2,
            (height + screenSize) / 2
        )
    }
}
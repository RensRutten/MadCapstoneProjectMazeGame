package com.example.madcapstoneprojectmazegame.item.maze

import android.content.Context
import android.graphics.*
import androidx.core.content.ContextCompat
import com.example.madcapstoneprojectmazegame.Drawable
import com.example.madcapstoneprojectmazegame.R
import java.util.*
import kotlin.random.Random


class Maze(
    context: Context, val size: Int
) : Drawable {
    private var wallPaint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var backPaint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var array: Array<BooleanArray> =  Array(size) { BooleanArray(size) }
    private val end : Point = Point(1, 1)
    private var bestScore : Int = 0
    lateinit var start : Point

    init {
        wallPaint.color = ContextCompat.getColor(context, R.color.colorWall)
        backPaint.color = Color.WHITE

        generateMaze()
    }

    private fun generateMaze(){
        for (i in 1 until size) {
            for (j in 1 until size) {
                array[i][j] = i % 2 != 0 && j % 2 != 0 && i < size - 1 && j < size - 1
            }
        }

        val stack : Stack<Point> = Stack<Point>()
        stack.push(end)
        while (stack.size > 0){
            val current = stack.peek()
            val unusedNeighbors : LinkedList<Point> = LinkedList<Point>()

            //left
            if (current.x > 2){
                if(!isUsedCell(current.x - 2, current.y)){
                    unusedNeighbors.add(Point(current.x - 2, current.y))
                }
            }

            //right
            if (current.x < size - 2){
                if(!isUsedCell(current.x + 2, current.y)){
                    unusedNeighbors.add(Point(current.x + 2, current.y))
                }
            }

            //up
            if (current.y > 2){
                if(!isUsedCell(current.x, current.y - 2)){
                    unusedNeighbors.add(Point(current.x, current.y - 2))
                }
            }

            //bottom
            if (current.y < size - 2){
                if(!isUsedCell(current.x, current.y + 2)){
                    unusedNeighbors.add(Point(current.x, current.y + 2))
                }
            }
            if (unusedNeighbors.size > 0){
                val rnd = Random.nextInt(unusedNeighbors.size)
                val direction : Point = unusedNeighbors[rnd]
                val diffX : Int = (direction.x - current.x) / 2
                val diffY : Int = (direction.y - current.y) / 2
                array[current.y + diffY][current.x + diffX] = true
                stack.push(direction)
            }else{
                if (bestScore < stack.size){
                    bestScore = stack.size
                    start = current

                    array[1][0] = true;
                }
                stack.pop()
            }
        }
    }

    fun canPlayerGoTo(x: Int, y: Int): Boolean {
        if (x < 0 || y < 0) return false
        return if (x > size || y > size) false else array[y][x]
    }

    private fun isUsedCell(x: Int, y: Int) : Boolean{
        if(x < 0 || y < 0 || x >= size - 1 || y >= size - 1) {
            return true
        }
        return array[y - 1][x] //left
                || array[y][x - 1]//up
                || array[y + 1][x]//right
                || array[y][x + 1]//bottom

    }
    override fun draw(canvas: Canvas, rect: Rect) {
        canvas.drawColor(backPaint.color)
        val cellSize : Float = ((rect.right - rect.left) / size).toFloat()
        for (i in 0 until size){
            for (j in 0 until size){
                if (!array[i][j]) {
                    val left : Float = j * cellSize + rect.left
                    val top = i * cellSize + rect.top

                    canvas.drawRect(left, top, left + cellSize, top + cellSize, wallPaint)

                }
            }
        }
    }
}
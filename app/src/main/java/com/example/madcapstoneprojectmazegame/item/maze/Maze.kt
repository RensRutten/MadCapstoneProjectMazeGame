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
    private var wallPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var backPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var array: Array<BooleanArray> = Array(size) { BooleanArray(size) }
    private val end: Point = Point(1, 1)
    private var bestScore: Int = 0
    lateinit var start: Point
    private val blocksBuildSize = 1
    private val blockSizeNormal = 0
    private val controlBlockSize = 2

    init {
        wallPaint.color = ContextCompat.getColor(context, R.color.colorWall)
        backPaint.color = Color.WHITE

        generateMaze()
    }

    private fun generateMaze() {
        for (i in blocksBuildSize until size) {
            for (j in blocksBuildSize until size) {
                array[i][j] = i % controlBlockSize != blockSizeNormal && j % controlBlockSize != blockSizeNormal && i < size - blocksBuildSize && j < size - blocksBuildSize
            }
        }

        val stack: Stack<Point> = Stack<Point>()
        stack.push(end)
        while (stack.size > blockSizeNormal) {
            val current = stack.peek()
            val unusedNeighbors: LinkedList<Point> = LinkedList<Point>()

            //left
            if (current.x > controlBlockSize) {
                if (!isUsedCell(current.x - controlBlockSize, current.y)) {
                    unusedNeighbors.add(Point(current.x - controlBlockSize, current.y))
                }
            }

            //right
            if (current.x < size - controlBlockSize) {
                if (!isUsedCell(current.x + controlBlockSize, current.y)) {
                    unusedNeighbors.add(Point(current.x + controlBlockSize, current.y))
                }
            }

            //up
            if (current.y > controlBlockSize) {
                if (!isUsedCell(current.x, current.y - controlBlockSize)) {
                    unusedNeighbors.add(Point(current.x, current.y - controlBlockSize))
                }
            }

            //bottom
            if (current.y < size - controlBlockSize) {
                if (!isUsedCell(current.x, current.y + controlBlockSize)) {
                    unusedNeighbors.add(Point(current.x, current.y + controlBlockSize))
                }
            }
            if (unusedNeighbors.size > blockSizeNormal) {
                val rnd = Random.nextInt(unusedNeighbors.size)
                val direction: Point = unusedNeighbors[rnd]
                val diffX: Int = (direction.x - current.x) / controlBlockSize
                val diffY: Int = (direction.y - current.y) / controlBlockSize
                array[current.y + diffY][current.x + diffX] = true
                stack.push(direction)
            } else {
                if (bestScore < stack.size) {
                    bestScore = stack.size
                    start = current

                    array[blocksBuildSize][blockSizeNormal] = true
                }
                stack.pop()
            }
        }
    }

    fun canPlayerGoTo(x: Int, y: Int): Boolean {
        if (x < blockSizeNormal || y < blockSizeNormal) return false
        return if (x > size || y > size) false else array[y][x]
    }

    private fun isUsedCell(x: Int, y: Int): Boolean {
        if (x < blockSizeNormal || y < blockSizeNormal || x >= size - controlBlockSize || y >= size - controlBlockSize) {
            return true
        }
        return array[y - blocksBuildSize][x] //left
                || array[y][x - blocksBuildSize]//up
                || array[y + blocksBuildSize][x]//right
                || array[y][x + blocksBuildSize]//bottom

    }

    override fun draw(canvas: Canvas, rect: Rect) {
        canvas.drawColor(backPaint.color)
        val cellSize: Float = ((rect.right - rect.left) / size).toFloat()
        for (i in blockSizeNormal until size) {
            for (j in blockSizeNormal until size) {
                if (!array[i][j]) {
                    val left: Float = j * cellSize + rect.left
                    val top = i * cellSize + rect.top

                    canvas.drawRect(left, top, left + cellSize, top + cellSize, wallPaint)

                }
            }
        }
    }
}
package com.example.madcapstoneprojectmazegame.item


import android.graphics.*
import com.example.madcapstoneprojectmazegame.Drawable
import com.example.madcapstoneprojectmazegame.GameManager

class Bonus(private val gm: GameManager, private val size: Int) : Drawable {

    val listPoint: ArrayList<Point> = ArrayList()
    private var bonusPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bonusCount = 0.2
    private val noPoints = 0
    private val bonusSize = 1
    private val randsize = 2
    private val noRand = 0
    private val rand = 1

    init {
        bonusPaint.color = Color.BLACK

        generateBonus()
    }

    private fun generateBonus() {
        val countBonus: Int = (size * bonusCount).toInt()
        for (i in noPoints until countBonus) {
            val rndX: Int = rand(bonusSize, size - bonusSize)
            val rndY: Int = rand(bonusSize, size - bonusSize)
            val bonus: Point = Point(rndX, rndY)
            if (gm.maze.start != bonus) {
                if (listPoint.size == noPoints) {
                    listPoint.add(bonus)
                } else {
                    for (list in listPoint) {
                        if (list != bonus) {
                            listPoint.add(bonus)
                            break
                        }
                    }
                }

            }
        }
    }

    private fun rand(min: Int, max: Int): Int {
        val rnd: Int = (min + randsize * (Math.random() * ((max - min) / randsize + rand))).toInt()
        return if (rnd % randsize == noRand) rnd - rand else rnd
    }

    override fun draw(canvas: Canvas, rect: Rect) {
        val cellSize: Float = ((rect.right - rect.left) / size).toFloat()
        for (i in listPoint) {
            val left: Float = i.x * cellSize + rect.left
            val top: Float = i.y * cellSize + rect.top

            canvas.drawRect(left, top, left + cellSize, top + cellSize, bonusPaint)
        }
    }
}
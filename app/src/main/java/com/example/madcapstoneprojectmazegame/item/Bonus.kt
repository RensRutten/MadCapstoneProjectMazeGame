package com.example.madcapstoneprojectmazegame.item


import android.graphics.*
import com.example.madcapstoneprojectmazegame.Drawable
import com.example.madcapstoneprojectmazegame.GameManager
import kotlin.collections.ArrayList

class Bonus(private val gm: GameManager, private val size : Int) : Drawable {
    val listPoint : ArrayList<Point> = ArrayList()
    private var bonusPaint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        bonusPaint.color = Color.BLACK

        generateBonus()
    }

    private fun generateBonus(){
        val countBonus : Int = (size * 0.2).toInt()
        for (i in 0 until countBonus) {
            val rndX : Int = rand(1, size - 1)
            val rndY : Int = rand(1, size - 1)
            val bonus : Point = Point(rndX,rndY)
            if (gm.maze.start != bonus) {
                if (listPoint.size == 0){
                    listPoint.add(bonus)
                }else{
                    for (list in listPoint){
                        if(list != bonus) {
                            listPoint.add(bonus)
                            break
                        }
                    }
                }

            }
        }
    }

    private fun rand(min : Int, max  : Int) : Int{
        val rnd : Int  = (min + 2*(Math.random()*((max-min)/2+1))).toInt()
        return if(rnd % 2 == 0) rnd - 1 else rnd
    }

    override fun draw(canvas: Canvas, rect: Rect) {
        val cellSize : Float = ((rect.right - rect.left) / size).toFloat()
        for (i in listPoint){
            val left : Float = i.x * cellSize + rect.left
            val top : Float = i.y * cellSize + rect.top

            canvas.drawRect(left, top, left + cellSize, top + cellSize , bonusPaint)
        }
    }
}
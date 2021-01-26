package com.example.madcapstoneprojectmazegame

import android.graphics.Point
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast

class Control(private val gm: GameManager) {
    private val notMove = 0
    private val moveUp = -1
    private val moveDown = 1
    private val moveLeft = -1
    private val moveRight = 1
    private val speed = 1
    private val makeMazeBigger = 4
    private val scorePoint = 1

    private val mainContext: MainActivity = gm.context as MainActivity
    private var run: Boolean = false
    private val tvScore: TextView = mainContext.findViewById(R.id.tvScore)

    init {
        moveKeys()
        additionalKeys()
    }

    private fun moveKeys() {
        val btUp: Button = mainContext.findViewById(R.id.btUp)
        val btDown: Button = mainContext.findViewById(R.id.btDown)
        val btLeft: Button = mainContext.findViewById(R.id.btLeft)
        val btRight: Button = mainContext.findViewById(R.id.btRight)

        btUp.setOnClickListener { goMove(notMove, moveUp) }
        btDown.setOnClickListener { goMove(notMove, moveDown) }
        btLeft.setOnClickListener { goMove(moveLeft, notMove) }
        btRight.setOnClickListener { goMove(moveRight, notMove) }

    }

    private fun goMove(diffX: Int, diffY: Int) {
        var stepX: Int = gm.player.x
        var stepY: Int = gm.player.y

        if (run){
            while (gm.maze.canPlayerGoTo(stepX + diffX, stepY + diffY)) {
                stepX += diffX
                stepY += diffY
                bonusRemove(stepX,stepY)
                if (diffX != 0) {
                    if (gm.maze.canPlayerGoTo(stepX, stepY + speed)
                            || gm.maze.canPlayerGoTo(stepX, stepY - speed)) {
                        break
                    }
                }
                if (diffY != 0) {
                    if (gm.maze.canPlayerGoTo(stepX + speed, stepY)
                            || gm.maze.canPlayerGoTo(stepX - speed, stepY)) {
                        break
                    }
                }
            }
        }else{
            if (gm.maze.canPlayerGoTo(stepX + diffX, stepY + diffY)) {
                stepX += diffX
                stepY += diffY
            }
        }
        bonusRemove(stepX,stepY)

        gm.player.goTo(stepX, stepY)

        if (gm.player.x == 0 || gm.player.y == 0) {
            gm.create(gm.maze.size + makeMazeBigger)
        }

        gm.view.invalidate()
    }

    private fun bonusRemove(x :Int ,y :Int){
        for (i in gm.bonus.listPoint){
            if (x == i.x && y == i.y) {
                gm.bonus.listPoint.remove(Point(x, y))
                Toast.makeText(mainContext, "Succesfull! go on and find more", Toast.LENGTH_SHORT).show()
                gm.score = gm.score + scorePoint
                tvScore.text = "Points found: ${gm.score}"
                return
            }
        }
    }
    private fun additionalKeys(){
        val swRun : Switch = mainContext.findViewById(R.id.swRun)
        swRun.setOnCheckedChangeListener { _, isChecked ->
            run = isChecked
        }
        val btRestart : Button = mainContext.findViewById(R.id.btRestart)
        btRestart.setOnClickListener {
            gm.create(gm.maze.size)
            gm.view.invalidate()
        }
    }
}
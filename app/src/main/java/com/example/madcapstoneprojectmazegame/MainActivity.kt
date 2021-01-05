package com.example.madcapstoneprojectmazegame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.example.madcapstoneprojectmazegame.item.maze.MazeView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val lvMap  : LinearLayout = findViewById(R.id.lvMap)

        val gameManager : GameManager = GameManager(this)
        val view : MazeView = MazeView(this, gameManager)
        view.invalidate()
        lvMap.addView(view)
    }

}
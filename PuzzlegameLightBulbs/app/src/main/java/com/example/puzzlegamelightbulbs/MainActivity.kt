package com.example.puzzlegamelightbulbs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import java.util.Random


class MainActivity : AppCompatActivity() {
    private lateinit var grid: GridLayout
    private lateinit var buttons: Array<Array<Button?>>
    private lateinit var scoreTextView: TextView
    private lateinit var levelTextView: TextView
    private var size = 4
    private var score = 0
    private var level = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        grid = findViewById(R.id.grid)
        scoreTextView = findViewById(R.id.score)
        levelTextView = findViewById(R.id.level)

        startLevel()
    }

    private fun startLevel() {
        grid.removeAllViews()
        grid.columnCount = size
        grid.rowCount = size

        buttons = Array(size) { arrayOfNulls<Button>(size) }
        for (i in 0 until size) {
            for (j in 0 until size) {
                val button = Button(this)
                button.setBackgroundColor(if (Random().nextBoolean()) Color.BLACK else Color.WHITE)
                button.setOnClickListener { v -> click(v as Button, i, j) }
                buttons[i][j] = button
                grid.addView(button, GridLayout.LayoutParams().apply {
                    width = resources.displayMetrics.widthPixels / size
                    height = resources.displayMetrics.widthPixels / size
                })
            }
        }

        levelTextView.text = "Level: $level"
    }

    private fun click(button: Button, i: Int, j: Int) {
        val dx = listOf(-1, 0, 1, 0, 0)
        val dy = listOf(0, 1, 0, -1, 0)

        for (direction in dx.indices) {
            val ni = i + dx[direction]
            val nj = j + dy[direction]
            if (ni in 0 until size && nj in 0 until size) {
                val neighborButton = buttons[ni][nj]
                neighborButton?.setBackgroundColor(if ((neighborButton?.background as ColorDrawable).color == Color.BLACK) Color.WHITE else Color.BLACK)
            }
        }

        // Добавляем задержку перед проверкой доски
            if (allButtonsAreSameColor()) {
                score++
                scoreTextView.text = "Score: $score"
                level++
                size++
                startLevel()
            } else {
                // Меняем цвет нажатой кнопки
                button.setBackgroundColor(if ((button.background as ColorDrawable).color == Color.BLACK) Color.WHITE else Color.BLACK)
            }
        } // Задержка в миллисекундах


    private fun allButtonsAreSameColor(): Boolean {
        val firstButtonColor = (buttons[0][0]?.background as ColorDrawable).color
        for (i in 0 until size) {
            for (j in 0 until size) {
                if ((buttons[i][j]?.background as ColorDrawable).color != firstButtonColor) {
                    return false
                }
            }
        }
        return true
    }
}
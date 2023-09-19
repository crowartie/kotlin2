package com.example.seabattle

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout

class MainActivity : AppCompatActivity() {
    private val boardSize = 10
    private val board = Array(boardSize) { IntArray(boardSize) }
    private val ships = listOf(4, 3, 3, 2, 2, 2, 1, 1, 1, 1)
    private lateinit var grid: GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        grid = findViewById(R.id.grid)
        placeShips()
        drawBoard()
    }

    private fun placeShips() {
        for (ship in ships) {
            var placed = false
            while (!placed) {
                val orientation = (0..1).random()
                val startX = (0 until boardSize).random()
                val startY = (0 until boardSize).random()

                if (canPlaceShip(startX, startY, ship, orientation)) {
                    for (i in 0 until ship) {
                        if (orientation == 0) {
                            board[startX][startY + i] = 1
                        } else {
                            board[startX + i][startY] = 1
                        }
                    }
                    placed = true
                }
            }
        }
    }

    private fun canPlaceShip(x: Int, y: Int, ship: Int, orientation: Int): Boolean {
        for (i in 0 until ship) {
            if (orientation == 0) {
                if (y + i >= boardSize || board[x][y + i] == 1 || !isSurroundingAreaFree(x, y + i)) {
                    return false
                }
            } else {
                if (x + i >= boardSize || board[x + i][y] == 1 || !isSurroundingAreaFree(x + i, y)) {
                    return false
                }
            }
        }
        return true
    }

    private fun isSurroundingAreaFree(x: Int, y: Int): Boolean {
        for (i in -1..1) {
            for (j in -1..1) {
                if (x + i in 0 until boardSize && y + j in 0 until boardSize && board[x + i][y + j] == 1) {
                    return false
                }
            }
        }
        return true
    }

    private fun printBoard() {
        for (i in board.indices) {
            for (j in board[i].indices) {
                print("${board[i][j]} ")
            }
            println()
        }
    }
    private fun drawBoard() {
        for (i in board.indices) {
            for (j in board[i].indices) {
                val button = Button(this)
                button.layoutParams = GridLayout.LayoutParams(
                    GridLayout.spec(i),
                    GridLayout.spec(j)
                )
                button.setBackgroundColor(if (board[i][j] == 1) Color.BLACK else Color.WHITE)
                grid.addView(button)
            }
        }
    }
}
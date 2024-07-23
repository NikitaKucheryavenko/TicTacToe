package com.nikitaapp.tictac

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var buttons: Array<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttons = arrayOf(
            findViewById(R.id.button), findViewById(R.id.button2), findViewById(R.id.button3),
            findViewById(R.id.button4), findViewById(R.id.button5), findViewById(R.id.button6),
            findViewById(R.id.button7), findViewById(R.id.button8), findViewById(R.id.button9)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.info, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_restart -> {
                restartGame()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun btnClick(view: View) {
        val selectedButton = view as Button
        var cellID = 0
        when (selectedButton.id) {
            R.id.button -> cellID = 1
            R.id.button2 -> cellID = 2
            R.id.button3 -> cellID = 3
            R.id.button4 -> cellID = 4
            R.id.button5 -> cellID = 5
            R.id.button6 -> cellID = 6
            R.id.button7 -> cellID = 7
            R.id.button8 -> cellID = 8
            R.id.button9 -> cellID = 9
        }
        playGame(cellID, selectedButton)
    }

    var Player1 = ArrayList<Int>()
    var Player2 = ArrayList<Int>()
    var ActivePlayer = 1

    fun playGame(cellId: Int, selectedButton: Button) {
        if (ActivePlayer == 1) {
            selectedButton.text = "X"
            selectedButton.setTextColor(Color.BLACK)
            selectedButton.setTextSize(30f)
            selectedButton.setBackgroundColor(Color.GREEN)
            Player1.add(cellId)
            ActivePlayer = 2
        } else {
            selectedButton.text = "O"
            selectedButton.setTextColor(Color.BLACK)
            selectedButton.setTextSize(30f)
            selectedButton.setBackgroundColor(Color.CYAN)
            Player2.add(cellId)
            ActivePlayer = 1
        }
        selectedButton.isEnabled = false
        checkWinner()
    }

    fun checkWinner() {
        var winner = -1
        val winningCombinations = arrayOf(
            arrayOf(1, 2, 3), arrayOf(4, 5, 6), arrayOf(7, 8, 9), // Rows
            arrayOf(1, 4, 7), arrayOf(2, 5, 8), arrayOf(3, 6, 9), // Columns
            arrayOf(1, 5, 9), arrayOf(3, 5, 7) // Diagonals
        )

        for (combination in winningCombinations) {
            if (Player1.containsAll(combination.toList())) {
                winner = 1
            }
            if (Player2.containsAll(combination.toList())) {
                winner = 2
            }
        }

        if (winner != -1) {
            if (winner == 1) {
                Toast.makeText(this, "X победил", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "O победил", Toast.LENGTH_LONG).show()
            }
            disableAllButtons()
            return
        }

        if (Player1.size + Player2.size == 9) {
            Toast.makeText(this, "Ничья", Toast.LENGTH_LONG).show()
            disableAllButtons()
        }
    }

    private fun disableAllButtons() {
        buttons.forEach { it.isEnabled = false }
    }

    fun restartGame() {
        Player1.clear()
        Player2.clear()
        ActivePlayer = 1
        buttons.forEach {
            it.text = ""
            it.setBackgroundColor(Color.GRAY)
            it.isEnabled = true
        }
        Toast.makeText(this, "Новая игра", Toast.LENGTH_SHORT).show()
    }
}
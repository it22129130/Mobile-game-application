package com.example.game_app

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var gbuttton0: Button
    private lateinit var gbuttton1: Button
    private lateinit var gbuttton2: Button
    private lateinit var gbuttton3: Button
    private lateinit var gbuttton4: Button
    private lateinit var gbuttton5: Button
    private lateinit var gbuttton6: Button
    private lateinit var gbuttton7: Button
    private lateinit var gbuttton8: Button

    private lateinit var tv: TextView
    private var player1 = 0
    private var player2 = 1
    private var activePlayer = player1
    private lateinit var filledPos: IntArray

    private var gameActive = true
    private var scorePlayer1 = 0
    private var scorePlayer2 = 0
    private var roundCount = 0
    private var highScorePlayer1 = 0
    private var highScorePlayer2 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        filledPos = IntArray(9) { -1 }

        tv = findViewById(R.id.textView2)
        gbuttton0 = findViewById(R.id.bb0)
        gbuttton1 = findViewById(R.id.bb1)
        gbuttton2 = findViewById(R.id.bb2)
        gbuttton3 = findViewById(R.id.bb3)
        gbuttton4 = findViewById(R.id.bb4)
        gbuttton5 = findViewById(R.id.bb5)
        gbuttton6 = findViewById(R.id.bb6)
        gbuttton7 = findViewById(R.id.bb7)
        gbuttton8 = findViewById(R.id.bb8)

        gbuttton0.setOnClickListener(this)
        gbuttton1.setOnClickListener(this)
        gbuttton2.setOnClickListener(this)
        gbuttton3.setOnClickListener(this)
        gbuttton4.setOnClickListener(this)
        gbuttton5.setOnClickListener(this)
        gbuttton6.setOnClickListener(this)
        gbuttton7.setOnClickListener(this)
        gbuttton8.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (!gameActive)
            return

        val btnClicked = findViewById<Button>(v!!.id)
        val clickedTag = v.tag.toString().toInt()

        if (filledPos[clickedTag] != -1)
            return

        filledPos[clickedTag] = activePlayer

        if (activePlayer == player1) {
            btnClicked.text = "0"
            activePlayer = player2
            tv.text = "Player-2 Turn"
            btnClicked.setTextColor(Color.BLACK)
            btnClicked.backgroundTintList = getColorStateList(R.color.green)
        } else {
            btnClicked.text = "x"
            activePlayer = player1
            tv.text = "Player-1 Turn"
            btnClicked.setTextColor(Color.BLACK)
            btnClicked.backgroundTintList = getColorStateList(R.color.yellow)
        }

        checkForWin()
    }

    private fun checkForWin() {
        val winpos = arrayOf(
            intArrayOf(0, 1, 2),
            intArrayOf(3, 4, 5),
            intArrayOf(6, 7, 8),
            intArrayOf(0, 3, 6),
            intArrayOf(1, 4, 7),
            intArrayOf(2, 5, 8),
            intArrayOf(0, 4, 8),
            intArrayOf(2, 4, 6)
        )

        for (i in winpos.indices) {
            val (val0, val1, val2) = winpos[i]

            if (filledPos[val0] == filledPos[val1] && filledPos[val1] == filledPos[val2]) {
                if (filledPos[val0] != -1) {
                    gameActive = false
                    updateScores(filledPos[val0])
                    showResultDialog(filledPos[val0])
                    return
                }
            }
        }

        var count = 0
        for (i in filledPos) {
            if (i == -1) {
                count++
            }
        }
        if (count == 0) {
            gameActive = false
            showResultDialog(-1)
        }
    }

    private fun updateScores(winner: Int) {
        if (winner == player1) {
            scorePlayer1++
            if (scorePlayer1 > highScorePlayer1) {
                highScorePlayer1 = scorePlayer1
            }
        } else if (winner == player2) {
            scorePlayer2++
            if (scorePlayer2 > highScorePlayer2) {
                highScorePlayer2 = scorePlayer2
            }
        }
    }

    private fun showResultDialog(winner: Int) {
        roundCount++
        val message: String = when {
            winner == player1 -> {
                "Player-1 is the winner!\nScore: Player-1 - $scorePlayer1, Player-2 - $scorePlayer2"
            }
            winner == player2 -> {
                "Player-2 is the winner!\nScore: Player-1 - $scorePlayer1, Player-2 - $scorePlayer2"
            }
            else -> {
                "It's a draw!\nScore: Player-1 - $scorePlayer1, Player-2 - $scorePlayer2"
            }
        }

        val dialogBuilder = AlertDialog.Builder(this)
            .setMessage(message)
            .setTitle("Game Over")
            .setPositiveButton("Restart Game") { dialog, which ->
                restartGame()
            }

        if (roundCount == 3) {
            dialogBuilder.setNegativeButton("View High Score") { dialog, which ->
                showHighScoreDialog()
            }
        }

        dialogBuilder.show()
    }

    private fun restartGame() {
        filledPos = IntArray(9) { -1 }
        activePlayer = player1
        gameActive = true
        tv.text = "Player-1 Turn"
        val buttons = arrayOf(
            gbuttton0, gbuttton1, gbuttton2,
            gbuttton3, gbuttton4, gbuttton5,
            gbuttton6, gbuttton7, gbuttton8
        )
        for (button in buttons) {
            button.text = ""
            button.backgroundTintList =
                getColorStateList(com.google.android.material.R.color.design_default_color_primary)
        }
    }

    private fun showHighScoreDialog() {
        val highScoreMessage =
            "High Score:\nPlayer-1: $highScorePlayer1\nPlayer-2: $highScorePlayer2"

        AlertDialog.Builder(this)
            .setMessage(highScoreMessage)
            .setTitle("High Score")
            .setPositiveButton("OK") { dialog, which ->
                // Reset round count after viewing high score
                roundCount = 0
                restartGame()
            }
            .show()
    }
}

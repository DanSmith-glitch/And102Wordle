package com.example.wordle

import FourLetterWordList
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    //The randomly selected word for the round
    private lateinit var wordToGuess: String

    private var guessCount = 0


    private lateinit var input: EditText
    private lateinit var guessButton: Button
    private lateinit var guessTextArray: Array<TextView>
    private lateinit var guessCheckArray: Array<TextView>




    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /**
         * Parameters / Fields:
         *   wordToGuess : String - the target word the user is trying to guess
         *   guess : String - what the user entered as their guess
         *
         * Returns a String of 'O', '+', and 'X', where:
         *   'O' represents the right letter in the right place
         *   '+' represents the right letter in the wrong place
         *   'X' represents a letter not in the target word
         */
        fun checkGuess(guess: String) : String {
            var result = ""
            for (i in 0..3) {
                if (guess[i] == wordToGuess[i]) {
                    result += "O"
                }
                else if (guess[i] in wordToGuess) {
                    result += "+"
                }
                else {
                    result += "X"
                }
            }
            return result
        }

        fun resetGame(){
            guessButton.setOnClickListener(){
                finish()
                startActivity(Intent(this, javaClass))}
        }



        fun make_guess(){
            //Check if the guess is valid
            if (input.text.length != 4){
                Toast.makeText(this, "word must be 4 letters", Toast.LENGTH_SHORT).show()
                return
            }

            //Get guess from EditText(then clear entry field)
            var guess = input.text.toString().uppercase()
            input.text.clear()


            //Add guess to board
            guessTextArray[guessCount].text = guess

            //Add check to board
            guessCheckArray[guessCount].text = checkGuess(guess)

            guessCount++

            //Check if the game ahs ended
            if (guess.equals(wordToGuess) or (guessCount == 3)){
                input.setHint("Target word: $wordToGuess")
                input.isEnabled = false

                guessButton.setText("Play Again")

                resetGame()
            }
        }


        input = findViewById(R.id.guess)

        guessButton = findViewById(R.id.guessButton)

        //Set button to wait for guesses
        guessButton.setOnClickListener { make_guess() }

        guessTextArray = arrayOf(
            findViewById(R.id.guess1),
            findViewById(R.id.guess2),
            findViewById(R.id.guess3)
        )

        guessCheckArray = arrayOf(
            findViewById(R.id.check1),
            findViewById(R.id.check2),
            findViewById(R.id.check3)
        )

        wordToGuess = FourLetterWordList.FourLetterWordList.getRandomFourLetterWord()

        Log.d("text", wordToGuess)


    }
}
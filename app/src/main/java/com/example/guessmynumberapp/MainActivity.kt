package com.example.guessmynumberapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.guessmynumberapp.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding

    private var rnd = Random()
    private var secretNumber = 0
    private var numberOfGuesses = 0

    companion object
    {
        const val SECRET_NUMBER = "SECRET_NUMBER"
        const val NUMBER_OF_GUESSES = "NUMBER_OF_GUESSES"
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonGuess.setOnClickListener {
            handleGuess()
        }

        binding.editTextGuess.requestFocus()

        if (savedInstanceState != null &&
            savedInstanceState.containsKey(SECRET_NUMBER) &&
            savedInstanceState.containsKey(NUMBER_OF_GUESSES)
        )
        {
            secretNumber = savedInstanceState.getInt(SECRET_NUMBER)
            numberOfGuesses = savedInstanceState.getInt(NUMBER_OF_GUESSES)
        }
        else
        {
            initGame()
        }
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        super.onSaveInstanceState(outState)

        outState.putInt(SECRET_NUMBER, secretNumber)
        outState.putInt(NUMBER_OF_GUESSES, numberOfGuesses)
    }

    private fun initGame()
    {
        secretNumber = rnd.nextInt(100) + 1
        numberOfGuesses = 0
    }

    private fun handleGuess()
    {
        if (binding.editTextGuess.text.isNotEmpty())
        {
            try
            {
                checkGuess()
            }
            catch (e: Exception)
            {
                binding.editTextGuess.error = e.message
            }
        }
        else
        {
            binding.editTextGuess.error = getString(R.string.text_this_field_cannot_be_empty)
        }
    }

    private fun checkGuess()
    {
        numberOfGuesses++
        binding.textViewGuesses.text = getString(R.string.text_number_of_guesses, numberOfGuesses.toString())

        val guess = binding.editTextGuess.text.toString().toInt()

        if (guess == secretNumber)
        {
            binding.textViewResult.text = getString(R.string.text_you_have_won)
        }
        else if (guess < secretNumber)
        {
            binding.textViewResult.text = getString(R.string.text_too_low)
        }
        else
        {
            binding.textViewResult.text = getString(R.string.text_too_high)
        }
    }
}
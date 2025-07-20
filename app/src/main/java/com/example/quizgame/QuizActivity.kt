package com.example.quizgame

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizgame.databinding.ActivityMainBinding
import com.example.quizgame.databinding.ActivityQuizBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.random.Random

class QuizActivity : AppCompatActivity() {
    lateinit var binding: ActivityQuizBinding
    val database = FirebaseDatabase.getInstance()
    val databaseReference = database.reference.child("Quations")
    var question = ""
    var answerA = ""
    var answerB = ""
    var answerC = ""
    var answerD = ""
    var correctAnswer = ""
    var questionCount = 0
    var questionNumber = 1
    var userAnswer = ""
    var userCorrect = 0
    var userWrong = 0
    lateinit var timer: CountDownTimer
    private val totalTime: Long = 25000L
    var timerContinue = false
    var leftTime = totalTime

    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val scoreRef = database.reference

    val questions = HashSet<Int>()

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
         do {
             val number = Random.nextInt(1, 11)
             questions.add(number)
         }while (questions.size < 5)
        gameLogic()

        binding.buttonNext.setOnClickListener{
            resetTimer()
            gameLogic()
        }

        binding.buttonFinish.setOnClickListener{
            sendScore()
        }

        binding.answerA.setOnClickListener{
            userAnswer= "a"
            pauseTimer()
            if (correctAnswer == userAnswer){
                binding.answerA.setBackgroundColor(Color.GREEN)
                userCorrect++
                binding.correctAnswers.text = userCorrect.toString()
            }
            else{
                binding.answerA.setBackgroundColor(Color.RED)
                userWrong++
                binding.wrongAnswers.text = userWrong.toString()
                findAnswer()
            }
            disableButtons()
        }
        binding.answerB.setOnClickListener{
            userAnswer = "b"
            pauseTimer()
            if (correctAnswer == userAnswer){
                binding.answerB.setBackgroundColor(Color.GREEN)
                userCorrect++
                binding.correctAnswers.text = userCorrect.toString()
            }
            else{
                binding.answerB.setBackgroundColor(Color.RED)
                userWrong++
                binding.wrongAnswers.text = userWrong.toString()
                findAnswer()
            }
            disableButtons()
        }
        binding.answerC.setOnClickListener{
            pauseTimer()
            userAnswer = "c"

            if (correctAnswer == userAnswer){
                binding.answerC.setBackgroundColor(Color.GREEN)
                userCorrect++
                binding.correctAnswers.text = userCorrect.toString()
            }
            else{
                binding.answerC.setBackgroundColor(Color.RED)
                userWrong++
                binding.wrongAnswers.text = userWrong.toString()
                findAnswer()
            }
            disableButtons()
        }
        binding.answerD.setOnClickListener{
            pauseTimer()
            userAnswer = "d"

            if (correctAnswer == userAnswer){
                binding.answerD.setBackgroundColor(Color.GREEN)
                userCorrect++
                binding.correctAnswers.text = userCorrect.toString()
            }
            else{
                binding.answerD.setBackgroundColor(Color.RED)
                userWrong++
                binding.wrongAnswers.text = userWrong.toString()
                findAnswer()
            }
            disableButtons()
        }

    }

    private fun gameLogic() {
        restoreOptions()
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                    questionCount  = snapshot.childrenCount.toInt()
                    if (questionNumber <= questions.size){
                        question = snapshot.child(questions.elementAt(questionNumber).toString()).child("q").getValue(String::class.java) ?: ""
                        answerA = snapshot.child(questions.elementAt(questionNumber).toString()).child("a").getValue(String::class.java) ?: ""
                        answerB = snapshot.child(questions.elementAt(questionNumber).toString()).child("b").getValue(String::class.java) ?: ""
                        answerC = snapshot.child(questions.elementAt(questionNumber).toString()).child("c").getValue(String::class.java) ?: ""
                        answerD = snapshot.child(questions.elementAt(questionNumber).toString()).child("d").getValue(String::class.java) ?: ""
                        correctAnswer = snapshot.child(questions.elementAt(questionNumber).toString()).child("answer").getValue(String::class.java) ?: ""

                        binding.questionText.text = question
                        binding.answerA.text = answerA
                        binding.answerB.text = answerB
                        binding.answerC.text = answerC
                        binding.answerD.text = answerD

                        binding.progressBar.visibility = View.INVISIBLE
                        binding.answersLayout.visibility = View.VISIBLE
                        binding.navigationButtons.visibility = View.VISIBLE
                        binding.topBar.visibility = View.VISIBLE
                        startTimer()
                    }
                else{
                       val alertDialog = AlertDialog.Builder(this@QuizActivity)
                        .setTitle("Quiz Finished")
                        .setMessage("You answered all the questions. Do you want to see your score?")
                        .setCancelable(false)
                           .setPositiveButton("See Score") { dialogWindow, position ->
                               sendScore()

                           }
                            .setNegativeButton("Cancel") { dialogWindow, position ->
                                 val intent = Intent(this@QuizActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                            }
                        .create()
                           .show()
                    }
                questionNumber++
                }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    fun findAnswer(){
        when(correctAnswer){
            "a" -> binding.answerA.setBackgroundColor(Color.GREEN)
            "b" -> binding.answerB.setBackgroundColor(Color.GREEN)
            "c" -> binding.answerC.setBackgroundColor(Color.GREEN)
            "d" -> binding.answerD.setBackgroundColor(Color.GREEN)
        }
    }
    fun disableButtons() {
        binding.answerA.isEnabled = false
        binding.answerB.isEnabled = false
        binding.answerC.isEnabled = false
        binding.answerD.isEnabled = false
    }
    fun restoreOptions() {
        binding.answerA.setBackgroundColor(Color.WHITE)
        binding.answerB.setBackgroundColor(Color.WHITE)
        binding.answerC.setBackgroundColor(Color.WHITE)
        binding.answerD.setBackgroundColor(Color.WHITE)

        binding.answerA.isEnabled = true
        binding.answerB.isEnabled = true
        binding.answerC.isEnabled = true
        binding.answerD.isEnabled = true
    }
    private fun startTimer(){
        timer = object : CountDownTimer(leftTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                leftTime = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                disableButtons()
                resetTimer()
                updateCountDownText()
                binding.questionText.text = "Time's up!"
            }
        }.start()
        timerContinue = true
    }
    private fun resetTimer() {
        pauseTimer()
        leftTime = totalTime
        updateCountDownText()
    }
    private fun updateCountDownText() {
        val remainingTime: Int = (leftTime / 1000).toInt()
        binding.timerText.text = remainingTime.toString()
    }
    private fun pauseTimer() {
            timer.cancel()
            timerContinue = false

    }
    fun sendScore(){
        user?.let{
            val userId = it.uid
            val score = userCorrect - userWrong
            scoreRef.child("Scores").child(userId).child("correct").setValue(userCorrect)
            scoreRef.child("Scores").child(userId).child("wrong").setValue(userWrong).addOnSuccessListener{
                Toast.makeText(this, "Score saved!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ResultActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
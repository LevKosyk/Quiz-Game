package com.example.quizgame

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizgame.databinding.ActivityResultBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ResultActivity : AppCompatActivity() {
    lateinit var binding: ActivityResultBinding
    val databaseReference = FirebaseDatabase.getInstance().reference.child("Scores")
    val auth= FirebaseAuth.getInstance()
    val user = auth.currentUser
    var userCorrect = ""
    var userWrong = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                user?.let {
                    val userId = it.uid
                    userCorrect = snapshot.child(userId).child("correct").value.toString()
                    userWrong = snapshot.child(userId).child("wrong").value.toString()
                    binding.textViewCorrectValue.text = userCorrect
                    binding.textViewWrongValue.text = userWrong
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        binding.buttonPlayAgain.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.buttonBackToHome.setOnClickListener {

        }

    }
}
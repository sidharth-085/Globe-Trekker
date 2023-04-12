package my.quiz.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart: Button = findViewById(R.id.btnStart)
        val givenText: EditText = findViewById(R.id.givenText)
        btnStart.setOnClickListener {
            if (givenText.text.isEmpty()) {
                Toast.makeText(this, "Name is Required", Toast.LENGTH_LONG).show()
            }
            else {
                val intent = Intent(this, QuizQuestions::class.java)
                intent.putExtra(Constants.USER_NAME, givenText.text.toString())
                startActivity(intent)
            }
        }
    }
}
package my.quiz.app

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import kotlinx.coroutines.selects.select

class QuizQuestions : AppCompatActivity(), View.OnClickListener {
    private var questions: ArrayList<Question>? = null
    private var currentQuestionNo = 1
    private var selectedOption = 0
    private var correctAnswers = 0
    private var userName: String? = null

    private var questionText: TextView? = null
    private var imageView: ImageView? = null
    private var progressBar: ProgressBar? = null
    private var progressBarText: TextView? = null
    private var option1: TextView? = null
    private var option2: TextView? = null
    private var option3: TextView? = null
    private var option4: TextView? = null
    private var submitBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        questionText = findViewById(R.id.questionText)
        imageView = findViewById(R.id.imageView)
        progressBar = findViewById(R.id.progressBar)
        progressBarText = findViewById(R.id.progressBarText)
        option1 = findViewById(R.id.option1)
        option2 = findViewById(R.id.option2)
        option3 = findViewById(R.id.option3)
        option4 = findViewById(R.id.option4)
        submitBtn = findViewById(R.id.btnSubmit)
        questions = Constants.getQuestions()
        userName = intent.getStringExtra(Constants.USER_NAME)

        option1?.setOnClickListener(this)
        option2?.setOnClickListener(this)
        option3?.setOnClickListener(this)
        option4?.setOnClickListener(this)
        submitBtn?.setOnClickListener(this)

        setQuestion()
    }

    private fun setQuestion() {
        defaultOptionView()
        val currentQues: Question = questions!![currentQuestionNo-1]
        progressBar?.progress = currentQuestionNo
        progressBarText?.text = "$currentQuestionNo / ${progressBar?.max}"
        questionText?.text = currentQues.question
        imageView?.setImageResource(currentQues.image)
        option1?.text = currentQues.option1
        option2?.text = currentQues.option2
        option3?.text = currentQues.option3
        option4?.text = currentQues.option4

        if (currentQuestionNo == questions!!.size) submitBtn?.text = "FINISH"
        else submitBtn?.text = "SUBMIT"
    }

    private fun defaultOptionView() {
        val textViewList = ArrayList<TextView>()
        option1?.let {
            textViewList.add(0, it)
        }
        option2?.let {
            textViewList.add(1, it)
        }
        option3?.let {
            textViewList.add(2, it)
        }
        option4?.let {
            textViewList.add(3, it)
        }
        for (option in textViewList) {
            option.setTextColor(Color.parseColor("#6c757d"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_bg)
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionView()
        selectedOption = selectedOptionNum
        tv.setTextColor(Color.parseColor("#000000"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_bg
        )
    }

    private fun answerView(answer: Int, drawable: Int) {
        when (answer) {
            1 -> {
                option1?.background = ContextCompat.getDrawable(this, drawable)
            }

            2 -> {
                option2?.background = ContextCompat.getDrawable(this, drawable)
            }

            3 -> {
                option3?.background = ContextCompat.getDrawable(this, drawable)
            }

            4 -> {
                option4?.background = ContextCompat.getDrawable(this, drawable)
            }
        }
    }
    // This function provide the background to the selected answer & correct answer.

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.option1 -> {
                option1?.let {
                    selectedOptionView(it, 1)
                }
            }

            R.id.option2 -> {
                option2?.let {
                    selectedOptionView(it, 2)
                }
            }

            R.id.option3 -> {
                option3?.let {
                    selectedOptionView(it, 3)
                }
            }

            R.id.option4 -> {
                option4?.let {
                    selectedOptionView(it, 4)
                }
            }

            R.id.btnSubmit -> {
                if (selectedOption == 0) {
                    currentQuestionNo++

                    if (currentQuestionNo <= questions!!.size) {
                        setQuestion()
                    }
                    else {
                        val intent = Intent(this, ResultActivity::class.java)
                        intent.putExtra(Constants.USER_NAME, userName)
                        intent.putExtra(Constants.CORRECT_ANSWERS, correctAnswers)
                        intent.putExtra(Constants.TOTAL_QUESTIONS, questions!!.size)
                        startActivity(intent)
                        finish()
                    }
                }

                else {
                    val currentQuestion: Question = questions!![currentQuestionNo - 1]
                    if (selectedOption != currentQuestion.correctOption) {
                        answerView(selectedOption, R.drawable.wrong_answer)
                    }
                    else {
                        correctAnswers++
                    }
                    answerView(currentQuestion.correctOption, R.drawable.correct_answer)

                    if (currentQuestionNo == questions!!.size) {
                        submitBtn?.text = "FINISH"
                    }
                    else {
                        submitBtn?.text = "GO TO NEXT QUESTION"
                    }
                    selectedOption = 0
                }
            }
        }
    }

}
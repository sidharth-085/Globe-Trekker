package my.quiz.app

data class Question(
    var id: Int,
    var question: String,
    var image: Int,
    var option1: String,
    var option2: String,
    var option3: String,
    var option4: String,
    var correctOption: Int)

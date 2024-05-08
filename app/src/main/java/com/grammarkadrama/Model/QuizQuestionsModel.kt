package com.grammarkadrama.Model
import android.os.Parcelable

class QuizQuestionsModel {
    class QuizQuestion {
        private var questionText: String = ""
        private var options: Map<String, String> = mapOf()
        private var correctOption: Int = 1
        private var explanation: String = ""
        private var hint: String = ""

        fun getQuestionText(): String {
            return questionText
        }

        fun setQuestionText(questionText: String) {
            this.questionText = questionText
        }

        fun getOptions(s: String): Map<String, String> {
            return options
        }

        fun setOptions(options: Map<String, String>) {
            this.options = options
        }

        fun getCorrectOption(): Int {
            return correctOption
        }

        fun setCorrectOption(correctOption: Int) {
            this.correctOption = correctOption
        }

        fun getExplanation(): String {
            return explanation
        }

        fun setExplanation(explanation: String) {
            this.explanation = explanation
        }

        fun getHint(): String {
            return hint
        }

        fun setHint(hint: String) {
            this.hint = hint
        }
    }

}
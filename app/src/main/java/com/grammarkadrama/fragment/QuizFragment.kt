//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.RadioButton
//import android.widget.RadioGroup
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import com.grammarkadrama.Model.QuizQuestionsModel
//import com.grammarkadrama.R
//
//class QuizFragment : Fragment() {
//
//    private lateinit var quizQuestion: QuizQuestionsModel.QuizQuestion
//    private var questionNumber: Int = 0
//    private lateinit var radioGroup: RadioGroup
//
//    companion object {
//        private const val ARG_QUIZ_QUESTION = "arg_quiz_question"
//        private const val ARG_QUESTION_NUMBER = "arg_question_number"
//
//        fun newInstance(quizQuestion: QuizQuestionsModel.QuizQuestion, questionNumber: Int): QuizFragment {
//            val fragment = QuizFragment()
//            val args = Bundle()
//            args.putParcelable(ARG_QUIZ_QUESTION, quizQuestion)
//            args.putInt(ARG_QUESTION_NUMBER, questionNumber)
//            fragment.arguments = args
//            return fragment
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_quiz, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        arguments?.let {
//            quizQuestion = it.getParcelable(ARG_QUIZ_QUESTION)!!
//            questionNumber = it.getInt(ARG_QUESTION_NUMBER)
//        }
//
//        // Initialize views
//        val textQuestion = view.findViewById<TextView>(R.id.textQuestion)
//        val radioOption1 = view.findViewById<RadioButton>(R.id.radioOption1)
//        val radioOption2 = view.findViewById<RadioButton>(R.id.radioOption2)
//        val radioOption3 = view.findViewById<RadioButton>(R.id.radioOption3)
//        val radioOption4 = view.findViewById<RadioButton>(R.id.radioOption4)
//        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
//
//        // Set question and options
//        textQuestion.text = quizQuestion.getQuestionText()
//        radioOption1.text = quizQuestion.getOptions("1").toString()
//        radioOption2.text = quizQuestion.getOptions("2").toString()
//        radioOption3.text = quizQuestion.getOptions("3").toString()
//        radioOption4.text = quizQuestion.getOptions("4").toString()
//
//        // Set radio group listener to capture selected answer
//        radioGroup.setOnCheckedChangeListener { group, checkedId ->
//            val checkedRadioButton = group.findViewById<RadioButton>(checkedId)
//            val selectedOption = checkedRadioButton.text.toString()
//            // Update selected answer in the global map
//            // You need to define the global map variable to store selected answers
//            // UpdateMap(questionNumber, selectedOption) // Implement this method
//        }
//    }
//
//    // Function to get the selected option from the radio button group
//    fun getSelectedOption(): Int? {
//        val checkedRadioButtonId = radioGroup.checkedRadioButtonId
//        return when (checkedRadioButtonId) {
//            R.id.radioOption1 -> 1
//            R.id.radioOption2 -> 2
//            R.id.radioOption3 -> 3
//            R.id.radioOption4 -> 4
//            else -> null
//        }
//    }
//}

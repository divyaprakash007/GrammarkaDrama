//package com.grammarkadrama.Adapter
//
//import QuizFragment
//import android.content.Context
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentManager
//import androidx.fragment.app.FragmentPagerAdapter
//import com.grammarkadrama.Model.QuizQuestionsModel.QuizQuestion
//
//class QuizPagerAdapter(private val context: Context, fm: FragmentManager, private val quizData: List<QuizQuestion>) : FragmentPagerAdapter(fm) {
//
//    private val fragmentList: MutableList<Fragment> = ArrayList()
//
//    // Add this function to get the selected option in the current fragment
//    fun getSelectedOption(position: Int): Int? {
//        if (position < fragmentList.size && fragmentList[position] is QuizFragment) {
//            return (fragmentList[position] as QuizFragment).getSelectedOption()
//        }
//        return null
//    }
//
//    override fun getCount(): Int {
//        return quizData.size
//    }
//
//    override fun getItem(position: Int): Fragment {
//        val fragment = QuizFragment.newInstance(quizData[position])
//        fragmentList.add(fragment)
//        return fragment
//    }
//
//    override fun getPageTitle(position: Int): CharSequence? {
//        return "Question ${position + 1}"
//    }
//}
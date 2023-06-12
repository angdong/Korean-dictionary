package edu.skku.cs.afinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val myAdapter = CustomAdapter_X(SearchActivity.searchHistory, applicationContext, "history")
        val listView = findViewById<ListView>(R.id.listView)
        CoroutineScope(Dispatchers.Main).launch {
            listView.adapter = myAdapter
        }

        if (SearchActivity.searchHistory.size > 0){
            val noResultImage = findViewById<ImageView>(R.id.imageNoResult)
            noResultImage.setImageResource(0)
            val noResult = findViewById<TextView>(R.id.textNoResult)
            noResult.text = ""
        }

        val searchBtn = findViewById<Button>(R.id.btnSearch)
        searchBtn.setOnClickListener{
            val nextIntent = Intent(this, SearchActivity::class.java)
            startActivity(nextIntent)
            // Go to left
            overridePendingTransition(R.anim.to_left, R.anim.from_right)
        }
        val starBtn = findViewById<Button>(R.id.btnStar)
        starBtn.setOnClickListener{
            val nextIntent = Intent(this, StarActivity::class.java)
            startActivity(nextIntent)
            // Go to right
            overridePendingTransition(R.anim.to_right, R.anim.from_left)
        }
        val quizBtn = findViewById<Button>(R.id.btnQuiz)
        quizBtn.setOnClickListener{
            val nextIntent = Intent(this, QuizActivity::class.java)
            startActivity(nextIntent)
            // Go to right
            overridePendingTransition(R.anim.to_right, R.anim.from_left)
        }
    }
    override fun onResume() {
        super.onResume()
        val myAdapter = CustomAdapter_X(SearchActivity.searchHistory, applicationContext, "history")
        val listView = findViewById<ListView>(R.id.listView)
        CoroutineScope(Dispatchers.Main).launch {
            listView.adapter = myAdapter
        }

        val noResultImage = findViewById<ImageView>(R.id.imageNoResult)
        val noResult = findViewById<TextView>(R.id.textNoResult)

        if (SearchActivity.searchHistory.size > 0){
            noResultImage.setImageResource(0)
            noResult.text = ""
        }
        else{
            noResultImage.setImageResource(R.drawable.no_history)
            noResult.text = "검색 기록이\n없습니다"
        }
    }
}
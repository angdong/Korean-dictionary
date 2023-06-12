package edu.skku.cs.afinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_star)

        val myAdapter = CustomAdapter_X(SearchActivity.bookmarkWords, applicationContext, "star")
        val listView = findViewById<ListView>(R.id.listView)
        CoroutineScope(Dispatchers.Main).launch {
            listView.adapter = myAdapter
        }

        if (SearchActivity.bookmarkWords.size > 0){
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
        val historyBtn = findViewById<Button>(R.id.btnHistory)
        historyBtn.setOnClickListener{
            val nextIntent = Intent(this, HistoryActivity::class.java)
            startActivity(nextIntent)
            // Go to left
            overridePendingTransition(R.anim.to_left, R.anim.from_right)
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
        val myAdapter = CustomAdapter_X(SearchActivity.bookmarkWords, applicationContext, "star")
        val listView = findViewById<ListView>(R.id.listView)
        CoroutineScope(Dispatchers.Main).launch {
            listView.adapter = myAdapter
        }

        val noResultImage = findViewById<ImageView>(R.id.imageNoResult)
        val noResult = findViewById<TextView>(R.id.textNoResult)

        if (SearchActivity.bookmarkWords.size > 0){
            noResultImage.setImageResource(0)
            noResult.text = ""
        }
        else{
            noResultImage.setImageResource(R.drawable.no_star)
            noResult.text = "즐겨찾기한 단어가\n없습니다"
        }
    }
}
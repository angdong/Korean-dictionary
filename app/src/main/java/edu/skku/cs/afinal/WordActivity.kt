package edu.skku.cs.afinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.TextView

class WordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word)

        val word = intent.getStringExtra("word")
        val sup = intent.getStringExtra("sup")
        val pos = intent.getStringExtra("pos")
        val def = intent.getStringExtra("def")

        var nowWord = SearchResult(" ", " ", " ", " ")

        var starIdx = 0

        for (item in SearchActivity.bookmarkWords){
            if (item.word == word && item.sup_no == sup){
                nowWord = item
                val btnBookmark = findViewById<Button>(R.id.btnBookmark)
                val drawable = resources.getDrawable(R.drawable.yellow_size, null)
                btnBookmark.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
                break
            }
            starIdx += 1
        }

        val textWord = findViewById<TextView>(R.id.textWord)
        val textPos = findViewById<TextView>(R.id.textPos)
        val textDef = findViewById<TextView>(R.id.textDef)

        textWord.text = word
        if (sup != "0"){
            textWord.text = Html.fromHtml("<b>$word</b><sup>$sup<sup>")
        }

        textPos.text = "「$pos」"
        textDef.text = def!!.replace(".", ".\n\n")

        val btnBookmark = findViewById<Button>(R.id.btnBookmark)

        btnBookmark.setOnClickListener{
            nowWord = SearchResult(
                word.toString(),
                pos.toString(),
                def,
                sup.toString()
            )
            starIdx = 0
            var haveBookmark = false
            for (item in SearchActivity.bookmarkWords){
                if (item.word == word && item.sup_no == sup){
                    haveBookmark = true
                    try{
                        SearchActivity.bookmarkWords.removeAt(starIdx)
                    } catch (e: java.lang.IndexOutOfBoundsException){
                        println("Exception occurred: ${e.message}")
                    }
                    break
                }
                starIdx += 1
            }
            if(haveBookmark){
                // set Empty star
                val drawable = resources.getDrawable(R.drawable.star_size, null)
                btnBookmark.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
            }
            else{
                // set Yellow star
                SearchActivity.bookmarkWords.add(0, nowWord)
                val drawable = resources.getDrawable(R.drawable.yellow_size, null)
                btnBookmark.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
            }
        }

//        val searchBtn = findViewById<Button>(R.id.btnSearch)
//        searchBtn.setOnClickListener{
//            val nextIntent = Intent(this, SearchActivity::class.java)
//            startActivity(nextIntent)
//        }
//        val historyBtn = findViewById<Button>(R.id.btnHistory)
//        historyBtn.setOnClickListener{
//            val nextIntent = Intent(this, HistoryActivity::class.java)
//            startActivity(nextIntent)
//        }
//        val starBtn = findViewById<Button>(R.id.btnStar)
//        starBtn.setOnClickListener{
//            isStar = false
//            val nextIntent = Intent(this, StarActivity::class.java)
//            startActivity(nextIntent)
//        }
//        val quizBtn = findViewById<Button>(R.id.btnQuiz)
//        quizBtn.setOnClickListener{
//            val nextIntent = Intent(this, QuizActivity::class.java)
//            startActivity(nextIntent)
//        }
    }

//    override fun onResume() {
//        super.onResume()
//        val word = findViewById<TextView>(R.id.textWord).text
//        val btnBookmark = findViewById<Button>(R.id.btnBookmark)
//        val drawable = resources.getDrawable(R.drawable.star_size, null)
//        btnBookmark.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
//        for (item in SearchActivity.bookmarkWords){
//            if (item.word == word){
//                val btnBookmark = findViewById<Button>(R.id.btnBookmark)
//                val drawable = resources.getDrawable(R.drawable.yellow_size, null)
//                btnBookmark.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
//                break
//            }
//        }
//    }
}
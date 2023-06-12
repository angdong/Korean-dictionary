package edu.skku.cs.afinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

class SearchActivity : AppCompatActivity() {
    companion object {
        var searchHistory = ArrayList<SearchResult>()
        var bookmarkWords = ArrayList<SearchResult>()
        const val apiKey = "YOUR_API_KEY"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val btnDelete = findViewById<ImageButton>(R.id.btnDelete)
        btnDelete.bringToFront()
        println(Thread.currentThread())

        btnDelete.setOnClickListener{
            val textWord = findViewById<EditText>(R.id.editTextWord)
            textWord.setText("")
        }

        val client = OkHttpClient()
        val host = "https://stdict.korean.go.kr/api/search.do?"

        val textWord = findViewById<EditText>(R.id.editTextWord)
        // can't click
        val searchText = textWord.text.toString().trim()
        val btnFind = findViewById<Button>(R.id.btnFind)
        btnFind.isEnabled = searchText.isNotEmpty()

        textWord.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트 변경 시 검색 버튼 활성화 여부 업데이트
                val searchText = textWord.text.toString().trim()
                btnFind.isEnabled = searchText.isNotEmpty()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        btnFind.setOnClickListener{
            val textWord = findViewById<EditText>(R.id.editTextWord)
            val word = textWord.text

            // get URL
            var getWord =
                        host + "key=" + apiKey +
                        "&advanced=y&method=start&type_search=search&req_type=json&q=" +
                        word
            println(getWord)
            var req = Request.Builder().url(getWord).build()
            val wordData = ArrayList<SearchResult>()

            client.newCall(req).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException){
                    e.printStackTrace()
                }
                override fun onResponse(call: Call, response: Response) {
                    response.use{
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")
                        val str = response.body!!.string()
                        val data = Gson().fromJson(str, Word::class.java)

                        wordData.clear()

                        CoroutineScope(Dispatchers.Main).launch {
                            val myAdapter = CustomAdapter(wordData, applicationContext)
                            val listView = findViewById<ListView>(R.id.listViewWord)
                            listView.adapter = myAdapter
                        }

                        // if there's no word
                        if (data.channel == null){
                            CoroutineScope(Dispatchers.Main).launch {
                                val noResultImage = findViewById<ImageView>(R.id.imageNoResult)
                                noResultImage.setImageResource(R.drawable.noresult)
                                val noResult = findViewById<TextView>(R.id.textNoResult)
                                noResult.text = "검색결과가\n없습니다!"
                            }
                            return
                        }
                        else{
                            var totalNum:Int = data.channel.total
                            println("First getting total Num : $totalNum")
                            CoroutineScope(Dispatchers.Main).launch {

                                val noResultImage = findViewById<ImageView>(R.id.imageNoResult)
                                noResultImage.setImageResource(0)
                                val noResult = findViewById<TextView>(R.id.textNoResult)
                                noResult.text = ""
                                for (i:Int in 0 until data.channel.total){
//                                    println(data.channel.item[i].word.replace("-", ""))
                                    wordData.add(SearchResult(
                                        data.channel.item[i].word.replace("-", "").replace("^", " "),
                                        data.channel.item[i].pos,
                                        data.channel.item[i].sense.definition,
                                        data.channel.item[i].sup_no
                                    ))
                                    if (i == 9) break
                                }
                            }
                            var startNum = 1

                            while(totalNum > 10){
                                totalNum -= 10
                                startNum += 1
                                val tmpTotalNum = totalNum
                                val tmpStartNum = startNum
                                getWord =
                                    host + "key=" + apiKey +
                                            "&advanced=y&method=start&type_search=search&req_type=json&q=" +
                                            word + "&start=" + tmpStartNum
                                req = Request.Builder().url(getWord).build()
                                client.newCall(req).enqueue(object : Callback {
                                    override fun onFailure(call: Call, e: IOException){
                                        e.printStackTrace()
                                    }
                                    override fun onResponse(call: Call, response: Response) {
                                        response.use{
                                            if (!response.isSuccessful) throw IOException("Unexpected code $response")
                                            val str = response.body!!.string()
                                            val data = Gson().fromJson(str, Word::class.java)

                                            CoroutineScope(Dispatchers.Main).launch {
                                                var maxIter = data.channel.total
                                                if(tmpTotalNum < 10){
                                                    maxIter = tmpTotalNum % 10
                                                }
                                                for (i:Int in 0 until maxIter){
                                                    wordData.add(SearchResult(
                                                        data.channel.item[i].word.replace("-", "").replace("^", " "),
                                                        data.channel.item[i].pos,
                                                        data.channel.item[i].sense.definition,
                                                        data.channel.item[i].sup_no
                                                    ))
//                                                        println(data.channel.item[i].word.replace("-", "").replace("^", " "))
                                                    if (i == 9) break
                                                }
                                                val myAdapter = CustomAdapter(wordData, applicationContext)
                                                val listView = findViewById<ListView>(R.id.listViewWord)
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    listView.adapter = myAdapter
                                                }
                                            }
                                        }
                                    }
                                })
                                Thread.sleep(200)
                            }

//                            val myAdapter = CustomAdapter(wordData, applicationContext)
//                            val listView = findViewById<ListView>(R.id.listViewWord)
//                            CoroutineScope(Dispatchers.Main).launch {
////                                println(wordData)
//                                listView.adapter = myAdapter
//                            }
                        }
                    }
                }
            })
        }
        val historyBtn = findViewById<Button>(R.id.btnHistory)
        historyBtn.setOnClickListener{
            val nextIntent = Intent(this, HistoryActivity::class.java)
            startActivity(nextIntent)

            // Go to right
            overridePendingTransition(R.anim.to_right, R.anim.from_left)
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
}
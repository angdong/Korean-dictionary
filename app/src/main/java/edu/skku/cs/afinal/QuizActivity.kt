package edu.skku.cs.afinal

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

class QuizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val wrongImage = findViewById<ImageView>(R.id.imageWrong)
        wrongImage.setImageResource(0)
        val correctImage = findViewById<ImageView>(R.id.imageCorrect)
        correctImage.setImageResource(0)
        wrongImage.bringToFront()
        correctImage.bringToFront()

        val nowWord = (1..50000).random()
        val list = (0..3).toMutableList()
        val ans = list.shuffled()

        for(item in ans){
            val idx = nowWord + item * 5
            val client = OkHttpClient()
            val host = "https://stdict.korean.go.kr/api/view.do?"
            var getWord =
                host + "key=" + SearchActivity.apiKey +
                        "&type_search=view&req_type=json&method=TARGET_CODE&q=" +
                        idx.toString()

            var req = Request.Builder().url(getWord).build()

            client.newCall(req).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException){
                    e.printStackTrace()
                }
                override fun onResponse(call: Call, response: Response) {
                    response.use{
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")
                        val str = response.body!!.string()
                        val data = Gson().fromJson(str, WordDesc::class.java)

                        // if there's no word
                        if (data.channel.total == 0){
                            Log.e("Quiz", "No Result! target code is $idx")
                            CoroutineScope(Dispatchers.Main).launch {
                                if (item == 0){
                                    var btn = findViewById<Button>(R.id.button)
                                    if(ans.indexOf(item) == 0){
                                        btn = findViewById<Button>(R.id.button)
                                    }
                                    else if(ans.indexOf(item) == 1){
                                        btn = findViewById<Button>(R.id.button2)
                                    }
                                    else if(ans.indexOf(item) == 2){
                                        btn = findViewById<Button>(R.id.button3)
                                    }
                                    else if(ans.indexOf(item) == 3){
                                        btn = findViewById<Button>(R.id.button4)
                                    }
                                    btn.text = "국내 최고 명문 사립대학교"
                                    val text = findViewById<TextView>(R.id.textQuiz)
                                    text.text = "성균관대학교"
                                }
                                else{
                                    if (ans.indexOf(item) == 0){
                                        val btn = findViewById<Button>(R.id.button)
                                        btn.text = "사람과 환경이 이루는 심리적 관계를 호도그래프를 써서 나타낸 일종의 기하학적 체계"
                                    }
                                    else if (ans.indexOf(item) == 1){
                                        val btn = findViewById<Button>(R.id.button2)
                                        btn.text = "일에 나서서 참견하거나 관심을 두다"
                                    }
                                    else if (ans.indexOf(item) == 2){
                                        val btn = findViewById<Button>(R.id.button3)
                                        btn.text = "숨을 매우 가쁘고 거칠게 쉬는 소리가 잇따라 나다"
                                    }
                                    else if (ans.indexOf(item) == 3){
                                        val btn = findViewById<Button>(R.id.button4)
                                        btn.text = "쌀밥에 김치, 야채 따위를 잘게 썰어 넣고 기름에 볶아 만든 밥"
                                    }
                                }
                            }
                            return
                        }
                        else{
                            CoroutineScope(Dispatchers.Main).launch {
                                var btn = findViewById<Button>(R.id.button)

                                if (ans.indexOf(item) == 0){
                                    btn = findViewById(R.id.button)
                                }
                                else if (ans.indexOf(item) == 1){
                                    btn = findViewById(R.id.button2)
                                }
                                else if (ans.indexOf(item) == 2){
                                    btn = findViewById(R.id.button3)
                                }
                                else{
                                    btn = findViewById(R.id.button4)
                                }
                                var word = data.channel.item.word_info.pos_info[0].comm_pattern_info[0].sense_info[0].definition.split(".")[0]
                                if (word.length > 58){
                                    word = word.slice(IntRange(0, 58)) + "..."
                                }
                                btn.text = word

                                if(item == 0){
                                    val text = findViewById<TextView>(R.id.textQuiz)
                                    text.text = data.channel.item.word_info.word.replace("-", "").replace("^", " ")
                                }
                            }
                        }
                    }
                }
            })
        }

        val btn1 = findViewById<Button>(R.id.button)
        val btn2 = findViewById<Button>(R.id.button2)
        val btn3 = findViewById<Button>(R.id.button3)
        val btn4 = findViewById<Button>(R.id.button4)

        btn1.setOnClickListener{
            if(ans[0] == 0){
                // right!
                val noResultImage = findViewById<ImageView>(R.id.imageCorrect)
                noResultImage.setImageResource(R.drawable.accept)
            }
            else{
                // wrong
                val noResultImage = findViewById<ImageView>(R.id.imageWrong)
                noResultImage.setImageResource(R.drawable.decline)
            }
            Handler().postDelayed(Runnable {
                //딜레이
                val intent = intent
                finish()
                startActivity(intent)
                overridePendingTransition( android.R.anim.fade_in, android.R.anim.fade_out )
            }, 600)
        }

        btn2.setOnClickListener{
            if(ans[1] == 0){
                // right!
                val noResultImage = findViewById<ImageView>(R.id.imageCorrect)
                noResultImage.setImageResource(R.drawable.accept)
            }
            else{
                // wrong
                val noResultImage = findViewById<ImageView>(R.id.imageWrong)
                noResultImage.setImageResource(R.drawable.decline)
            }
            Handler().postDelayed(Runnable {
                //딜레이
                val intent = intent
                finish()
                startActivity(intent)
                overridePendingTransition( android.R.anim.fade_in, android.R.anim.fade_out )
            }, 600)
        }

        btn3.setOnClickListener{
            if(ans[2] == 0){
                // right!
                val noResultImage = findViewById<ImageView>(R.id.imageCorrect)
                noResultImage.setImageResource(R.drawable.accept)
            }
            else{
                // wrong
                val noResultImage = findViewById<ImageView>(R.id.imageWrong)
                noResultImage.setImageResource(R.drawable.decline)
            }
            Handler().postDelayed(Runnable {
                //딜레이
                val intent = intent
                finish()
                startActivity(intent)
                overridePendingTransition( android.R.anim.fade_in, android.R.anim.fade_out )
            }, 600)
        }

        btn4.setOnClickListener{
            if(ans[3] == 0){
                // right!
                val noResultImage = findViewById<ImageView>(R.id.imageCorrect)
                noResultImage.setImageResource(R.drawable.accept)
            }
            else{
                // wrong
                val noResultImage = findViewById<ImageView>(R.id.imageWrong)
                noResultImage.setImageResource(R.drawable.decline)
            }
            Handler().postDelayed(Runnable {
                //딜레이
                val intent = intent
                finish()
                startActivity(intent)
                overridePendingTransition( android.R.anim.fade_in, android.R.anim.fade_out )
            }, 600)
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
        val starBtn = findViewById<Button>(R.id.btnStar)
        starBtn.setOnClickListener{
            val nextIntent = Intent(this, StarActivity::class.java)
            startActivity(nextIntent)
            // Go to left
            overridePendingTransition(R.anim.to_left, R.anim.from_right)
        }
    }
}
package edu.skku.cs.afinal

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView

class CustomAdapter(val data: ArrayList<SearchResult>, val context: Context) : BaseAdapter(){
    //    CustomAdapter가 BaseAdapter를 가져온다
//    interface에 제시된 모든 함수들을 구현해줘야 한다(BaseAdapter의)
    override fun getCount(): Int {
//    data 개수 알려주는 함수
        return data.size
    }
    override fun getItem(p0: Int): Any {
//    p0 번째 원소를 반환하는 함수
        return data[p0]
    }
    override fun getItemId(p0: Int): Long {
//    id를 반환하는 함수, id 따로 없으므로 0 반환하자
        return 0
    }
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val layoutInflater: LayoutInflater =
            context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
            ) as LayoutInflater
//        applicationContext 가 필요하지만 activity가 아니라 직접적으로 알 수 있는 방법이 없다
//        따라서 외부에서 applicationContext를 가져와야 한다

        val view = layoutInflater.inflate(
            R.layout.word_list, null)
//        image랑 textView를 찾아야 한다
        val btnWord = view.findViewById<TextView>(R.id.btnWord)
        if (data[p0].sup_no != "0"){
            btnWord.text = Html.fromHtml(data[p0].word + "<sup>" + data[p0].sup_no + "<sup>")
        }
        else{
            btnWord.text = data[p0].word
        }

        btnWord.setOnClickListener{
            var removeIdx = 0
            for (item in SearchActivity.searchHistory){
                if (item.word == data[p0].word && item.sup_no == data[p0].sup_no){
                    SearchActivity.searchHistory.removeAt(removeIdx)
                    break
                }
                removeIdx += 1
            }
            SearchActivity.searchHistory.add(0, data[p0])

            val intent = Intent(context, WordActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("word", data[p0].word)
            intent.putExtra("sup", data[p0].sup_no)
            intent.putExtra("pos", data[p0].pos)
            intent.putExtra("def", data[p0].definition)
            context.startActivity(intent)
        }

        return view
    }
}
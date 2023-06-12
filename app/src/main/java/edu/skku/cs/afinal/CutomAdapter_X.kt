package edu.skku.cs.afinal

import android.content.Context
import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class CustomAdapter_X(val data: ArrayList<SearchResult>, val context: Context, val where: String) : BaseAdapter(){
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

        val view = layoutInflater.inflate(
            R.layout.word_list_x, null)
//        val btnDelete = view.findViewById<ImageButton>(R.id.btnDelete)
//        btnDelete.bringToFront()

        val btnWord = view.findViewById<TextView>(R.id.btnWord)
        if (data[p0].sup_no != "0"){
            btnWord.text = Html.fromHtml(data[p0].word + "<sup>" + data[p0].sup_no + "<sup>")
        }
        else{
            btnWord.text = data[p0].word
        }

        btnWord.setOnClickListener{

//            SearchActivity.searchHistory.add(0, data[p0])

            val intent = Intent(context, WordActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("word", data[p0].word)
            intent.putExtra("sup", data[p0].sup_no)
            intent.putExtra("pos", data[p0].pos)
            intent.putExtra("def", data[p0].definition)
            context.startActivity(intent)
        }

        val btnDelete = view.findViewById<ImageButton>(R.id.btnDelete)
        btnDelete.setOnClickListener {
            removeItem(p0)

//            if(where == "star" && SearchActivity.bookmarkWords.size == 0){
//                println("Star delete")
//                val layoutInflater: LayoutInflater =
//                    context.getSystemService(
//                        Context.LAYOUT_INFLATER_SERVICE
//                    ) as LayoutInflater
//
//                val view = layoutInflater.inflate(
//                    R.layout.activity_star, null)
//                val noResultImage = view.findViewById<ImageView>(R.id.imageNoResult)
//                noResultImage.setImageResource(R.drawable.no_star)
//                val noResult = view.findViewById<TextView>(R.id.textNoResult)
//                noResult.text = "즐겨찾기한 단어가\n없습니다"
//
//            }
//            else if(where == "history" && SearchActivity.searchHistory.size == 0){
//                println("History delete")
//                val layoutInflater: LayoutInflater =
//                    context.getSystemService(
//                        Context.LAYOUT_INFLATER_SERVICE
//                    ) as LayoutInflater
//
//                val view = layoutInflater.inflate(
//                    R.layout.activity_history, null)
//                val noResultImage = view.findViewById<ImageView>(R.id.imageNoResult)
//                noResultImage.setImageResource(R.drawable.no_history)
//                val noResult = view.findViewById<TextView>(R.id.textNoResult)
//                noResult.text = "검색 기록이\n없습니다"
//            }
        }
        return view
    }

    private fun removeItem(position: Int){
        data.removeAt(position)
        notifyDataSetChanged()
    }
}
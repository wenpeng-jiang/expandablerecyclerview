package com.example.expandablerecyclerview.View

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.util.Log
import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.example.expandablerecyclerview.Adapter.FaqAdapter
import com.example.expandablerecyclerview.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    var list = arrayListOf<Questions>(
        Questions("Question 1"),
        Questions("Question 2"),
        Questions("Question 3"),
        Questions("Question 4"),
        Questions("Question 5")
    )
    var faqAdapter : FaqAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for ( i in 1..list.size){
            list.get(i-1).addSubItem(Answers("Answer " + i))
        }

        var layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager
        faqAdapter = FaqAdapter(list).apply {
            setOnItemChildClickListener { adapter, view, position ->
                if(view.id == R.id.question_id) {
                    var item : Questions = adapter.getItem(position) as Questions
                    if (item.isExpanded){
                        adapter.collapse(position)
                    } else {
                        Log.d("TEST", item.getSubItem(0).answer )
                        adapter.expand(position)
                    }
                }
            }
        }

        recycler_view.adapter = faqAdapter
        var searchView = findViewById<SearchView>(R.id.search_view)
        searchFilter(searchView)
    }

    private fun searchFilter(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(text: String?): Boolean {
                Log.d("searching", "Testing")
                var searchList = arrayListOf<Questions>()
                if(TextUtils.isEmpty(text)){
                    faqAdapter = FaqAdapter(list)
                } else {
                    searchList.clear()
                    for (searchText in list) {
                        if (searchText.question.contains(text!!)) {
                            searchList.add(searchText)
                            Log.d("searching", searchText.question)
                        }
                    }
                    faqAdapter = FaqAdapter(searchList)
                }
                faqAdapter?.apply {
                    setOnItemChildClickListener { adapter, view, position ->
                        var item : Questions = adapter.getItem(position) as Questions
                        if (item.isExpanded){
                            adapter.collapse(position)
                        } else {
                            Log.d("TEST", item.getSubItem(0).answer )
                            adapter.expand(position)
                        }
                    }
                }
                recycler_view.adapter = faqAdapter
                faqAdapter?.notifyDataSetChanged()

                return true
            }

            override fun onQueryTextSubmit(text: String?): Boolean {
                return true
            }
        })
    }


    data class Questions(var question: String) : AbstractExpandableItem<Answers>(), MultiItemEntity {
        override fun getItemType(): Int {
            return FaqAdapter.TYPE_QUESTION
        }

        override fun getLevel(): Int {
            return 0
        }

    }

    data class Answers(var answer: String) : MultiItemEntity {
        override fun getItemType(): Int {
            return FaqAdapter.TYPE_ANSWER
        }

    }
}

package com.example.expandablerecyclerview.Adapter

import android.util.Log
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.example.expandablerecyclerview.R
import com.example.expandablerecyclerview.View.MainActivity

class FaqAdapter (data: List<MultiItemEntity>) : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data){

    init {
        addItemType(TYPE_QUESTION, R.layout.question_row)
        addItemType(TYPE_ANSWER, R.layout.answer_row)
    }

    override fun convert(helper: BaseViewHolder, item: MultiItemEntity) {
        helper.addOnClickListener(R.id.question_id)
        when (helper.itemViewType) {
            TYPE_QUESTION ->{
                var q = item as MainActivity.Questions
                helper.setText(R.id.question_id, q.question)

                helper.itemView.setOnClickListener {
                    var position = helper.adapterPosition

                    if (q.isExpanded) {
                        collapse(position)
                    } else {
                        expand(position)
                    }
                }
            }
            TYPE_ANSWER -> {
                val a = item as MainActivity.Answers
                helper.setText(R.id.answer_id, a.answer)

            }
        }
    }

    companion object {
        private val TAG = FaqAdapter::class.java.simpleName

        val TYPE_QUESTION = 0
        val TYPE_ANSWER = 1
    }

}
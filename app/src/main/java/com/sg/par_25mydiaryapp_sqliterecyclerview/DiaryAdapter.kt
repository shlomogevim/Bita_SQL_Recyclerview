package com.sg.par_25mydiaryapp_sqliterecyclerview

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sg.par_25mydiaryapp_sqliterecyclerview.data.Diary

class DiaryAdapter(private var diaryList: MutableList<Diary>) :
    RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.diary_item, parent, false)
        return DiaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        holder.bindDiary(diaryList[position])
    }

    override fun getItemCount() = diaryList.size


    class DiaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private lateinit var diary: Diary
        private var date:TextView
        private var title:TextView

        init {
            itemView.setOnClickListener(this)
            date=itemView.findViewById(R.id.date_recycler_item)
            title=itemView.findViewById(R.id.title_recycler_item)
        }

        override fun onClick(v: View?) {
            val contex=itemView.context
            val intent=Intent(contex,NewDiary::class.java)
            intent.putExtra("IDofRow",diary.id)
            contex.startActivity(intent)


        }
        fun bindDiary(diary: Diary){
            this.diary=diary
            date.text=diary.date
            title.text=diary.title

        }

    }
}



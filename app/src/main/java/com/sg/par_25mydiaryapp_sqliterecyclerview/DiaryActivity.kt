package com.sg.par_25mydiaryapp_sqliterecyclerview

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sg.par_25mydiaryapp_sqliterecyclerview.data.DatabaseManager.DiaryEntry.COLUMN_DATE
import com.sg.par_25mydiaryapp_sqliterecyclerview.data.DatabaseManager.DiaryEntry.COLUMN_DIARY
import com.sg.par_25mydiaryapp_sqliterecyclerview.data.DatabaseManager.DiaryEntry.COLUMN_TITLE
import com.sg.par_25mydiaryapp_sqliterecyclerview.data.DatabaseManager.DiaryEntry.TABLE_NAME
import com.sg.par_25mydiaryapp_sqliterecyclerview.data.DatabaseManager.DiaryEntry._ID
import com.sg.par_25mydiaryapp_sqliterecyclerview.data.Diary
import com.sg.par_25mydiaryapp_sqliterecyclerview.data.DiaryDBHelper
import kotlinx.android.synthetic.main.activity_diary.*

class DiaryActivity : AppCompatActivity() {

    private lateinit var mDBHelper:DiaryDBHelper

    private  var diaryList:ArrayList<Diary> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter:DiaryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

       /* diaryList.add(Diary("2018","First Diary","my  first diary"))
        diaryList.add(Diary("2019","Second Diary","my  second diary"))*/

        mDBHelper= DiaryDBHelper(this)

    }
    override fun onStart() {
        super.onStart()
        diaryList.clear()
        displayDateInfo()
    }

    private fun displayDateInfo() {
        val db = mDBHelper.readableDatabase
        val projection = arrayOf(_ID, COLUMN_DATE, COLUMN_TITLE, COLUMN_DIARY)
        val cursor: Cursor = db.query(TABLE_NAME, projection, null, null, null, null, null)
        val idCoulmnIndex = cursor.getColumnIndexOrThrow(_ID)
        val dateCoulmnIndex = cursor.getColumnIndexOrThrow(COLUMN_DATE)
        val titleCoulmnIndex = cursor.getColumnIndexOrThrow(COLUMN_TITLE)
        val diaryCoulmnIndex = cursor.getColumnIndexOrThrow(COLUMN_DIARY)
        while (cursor.moveToNext()) {
            val currendID = cursor.getInt(idCoulmnIndex)
            val currentDate = cursor.getString(dateCoulmnIndex)
            val currentTitle = cursor.getString(titleCoulmnIndex)
            val currentDiary = cursor.getString(diaryCoulmnIndex)
            val diary = Diary(currendID, currentDate, currentTitle, currentDiary)
            diaryList.add(diary)
        }
        cursor.close()

        linearLayoutManager=LinearLayoutManager(this)
        recycler_view.layoutManager=linearLayoutManager
        adapter=DiaryAdapter(diaryList)
        recycler_view.adapter=adapter
    }



    fun createNewDiary( view:View){
        val intent=Intent(this,NewDiary::class.java)
        startActivity(intent)

    }
}
package com.sg.par_25mydiaryapp_sqliterecyclerview

import android.content.ContentValues
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.sg.par_25mydiaryapp_sqliterecyclerview.data.DatabaseManager.DiaryEntry.COLUMN_DATE
import com.sg.par_25mydiaryapp_sqliterecyclerview.data.DatabaseManager.DiaryEntry.COLUMN_DIARY
import com.sg.par_25mydiaryapp_sqliterecyclerview.data.DatabaseManager.DiaryEntry.COLUMN_TITLE
import com.sg.par_25mydiaryapp_sqliterecyclerview.data.DatabaseManager.DiaryEntry.TABLE_NAME
import com.sg.par_25mydiaryapp_sqliterecyclerview.data.DatabaseManager.DiaryEntry._ID
import com.sg.par_25mydiaryapp_sqliterecyclerview.data.DiaryDBHelper
import kotlinx.android.synthetic.main.activity_new_diary.*
import java.text.SimpleDateFormat
import java.util.*

class NewDiary : AppCompatActivity() {

    private var id=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_diary)

        id=intent.getIntExtra("IDofRow",0)

       if (id!=0){
           readDiary(id)
       }


        Log.d("NewDiary","the Pass ID is $id")

        var currentDate=SimpleDateFormat("EEE, d MMM yyyy")
        current_data_diary.text=currentDate.format(Date())
    }

    private fun readDiary(id: Int) {
        val mDBHelper=DiaryDBHelper(this)
        val db=mDBHelper.readableDatabase
        val projection= arrayOf(COLUMN_DATE, COLUMN_TITLE, COLUMN_DIARY)
        val selection="$_ID = ?"
        val selectionArg= arrayOf("$id")

        val cursor:Cursor = db.query(
            TABLE_NAME,
            projection,
            selection,
            selectionArg, null, null, null
        )
        val dateColumnIndex=cursor.getColumnIndexOrThrow(COLUMN_DATE)
        val titleColumnIndex=cursor.getColumnIndexOrThrow(COLUMN_TITLE)
        val diaryColumnIndex=cursor.getColumnIndexOrThrow(COLUMN_DIARY)

        while (cursor.moveToNext()){
            val currentDate = cursor.getString(dateColumnIndex)
            val currentTitle=cursor.getString(titleColumnIndex)
            val currentDiary=cursor.getString(diaryColumnIndex)
            current_data_diary.text=currentDate
            title_dairy.setText(currentTitle)
            diart_text.setText(currentDiary)
        }
        cursor.close()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater:MenuInflater=menuInflater
        inflater.inflate(R.menu.action_bar_menu,menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item?.itemId){
            R.id.save_diary ->{
                insertDiary()
                finish()
                true
            }
            else->super.onOptionsItemSelected(item)
        }
    }

    private fun insertDiary() {
        val dateString=current_data_diary.text.toString()
        val titleString=title_dairy.text.toString().trim(){it <= ' '}
        val diaryString=diart_text.text.toString().trim() {it <= ' '}

        val mDBHelper=DiaryDBHelper (this)
        val db=mDBHelper.writableDatabase

        val values=ContentValues().apply {
            put(COLUMN_DATE, dateString)
            put(COLUMN_TITLE,titleString)
            put(COLUMN_DIARY,diaryString)
        }
        val rowId= db.insert(TABLE_NAME,null,values)
        if (rowId.equals(-1)){
            Toast.makeText(this,"Problem in inserting new diary.",Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this,"New diary insert $rowId",Toast.LENGTH_LONG).show()

        }
}


}
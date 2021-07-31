package com.sg.par_25mydiaryapp_sqliterecyclerview.data

import android.provider.BaseColumns

object DatabaseManager {
    object DiaryEntry:BaseColumns{
        const val TABLE_NAME ="diaries"

        const val _ID = BaseColumns._ID

        const val COLUMN_DATE = "date"

        const val COLUMN_TITLE = "title"

        const val COLUMN_DIARY = "diary"


    }
}
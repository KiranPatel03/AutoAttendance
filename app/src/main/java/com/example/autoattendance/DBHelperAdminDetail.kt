package com.example.autoattendance

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build.VERSION_CODES.P
import android.provider.ContactsContract

class DBHelperAdminDetail(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object{
        private val DATABASE_NAME = "ATTENDANCE"
        private val DATABASE_VERSION = 1
        val TABLE_NAME_1 = "admin_detail"
        val ID_COL="id"
        val EMAIL_COL="email"
        val NAME_COL="name"
        val ROL_COL="role"
        val PHONE_NUMBER_COL="phone_number"
    }

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME_1 + " ("
                + ID_COL + " TEXT PRIMARY KEY, "
                + EMAIL_COL + " TEXT,"
                + NAME_COL + " TEXT,"
                + ROL_COL + " TEXT,"
                + PHONE_NUMBER_COL + " TEXT," + ")")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1)
        onCreate(db)
    }

    fun addId(id: String, email: String){
        val values = ContentValues()
        values.put(ID_COL, id)
        values.put(EMAIL_COL, email)
        val db = this.writableDatabase
        db.insert(TABLE_NAME_1, null, values)
        db.close()
    }

    fun addAdminDetail(name : String, rol:String, phoneNumber: String){
        val values = ContentValues()
        values.put(NAME_COL, name)
        values.put(ROL_COL, rol)
        values.put(PHONE_NUMBER_COL, phoneNumber)
        val db = this.writableDatabase
        db.insert(TABLE_NAME_1, null, values)
        db.close()
    }


//    fun getEmail(id: String): P? {
//        val db = this.readableDatabase
//
//        // below code returns a cursor to
//        // read data from the database
//        val cursor= db.rawQuery("SELECT * FROM"+ TABLE_NAME_1 + "WHERE" + ID_COL + "=" + id, null)
//        val p: P?=null
//        if (cursor.moveToFirst()) {
//            cursor.moveToFirst()
//
//            val id = Integer.parseInt(cursor.getString(0))
//            val email_temp = cursor.getString(1)
//            p = P(id, email_temp)
//            cursor.close()
//        }
//        db.close()
//        return email
//    }

    @SuppressLint("Range")
    fun readUser(userid: String): ArrayList<AdminModel> {
        val users = ArrayList<AdminModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBHelperAdminDetail.TABLE_NAME_1 + " WHERE " + DBHelperAdminDetail.ID_COL + "='" + userid + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            val query = ("CREATE TABLE " + TABLE_NAME_1 + " ("
                    + ID_COL + " TEXT PRIMARY KEY, "
                    + EMAIL_COL + " TEXT,"
                    + NAME_COL + " TEXT,"
                    + ROL_COL + " TEXT,"
                    + PHONE_NUMBER_COL + " TEXT," + ")")
            db.execSQL(query)
            return ArrayList()
        }

        var email: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                email = cursor.getString(cursor.getColumnIndex(DBHelperAdminDetail.EMAIL_COL))

                users.add(AdminModel(userid, email, null, null, null))
                cursor.moveToNext()
            }
        }
        return users
    }

}
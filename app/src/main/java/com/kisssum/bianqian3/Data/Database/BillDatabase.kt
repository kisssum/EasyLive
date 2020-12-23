package com.kisssum.bianqian3.Data.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kisssum.bianqian3.Data.Dao.BillDao
import com.kisssum.bianqian3.Data.Entity.Bill

@Database(entities = [Bill::class], version = 1)
abstract class BillDatabase : RoomDatabase() {
    abstract fun billDao(): BillDao
}
package com.kisssum.bianqian3.Data.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kisssum.bianqian3.Data.Dao.MenoDao
import com.kisssum.bianqian3.Data.Entity.Meno

@Database(entities = [Meno::class], version = 1)
abstract class MenoDatabase : RoomDatabase() {
    abstract fun menoDao(): MenoDao
}
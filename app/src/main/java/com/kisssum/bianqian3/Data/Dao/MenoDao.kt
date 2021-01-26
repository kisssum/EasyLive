package com.kisssum.bianqian3.Data.Dao

import androidx.room.*
import com.kisssum.bianqian3.Data.Entity.Meno

@Dao
interface MenoDao {
    @Query("select * from meno order by lastTime desc")
    fun getAll(): List<Meno>

    @Insert
    fun inserts(vararg meno: Meno)

    @Update
    fun updates(vararg meno: Meno)

    @Delete
    fun deletes(vararg meno: Meno)
}
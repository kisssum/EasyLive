package com.kisssum.bianqian3.Data.Dao

import androidx.room.*
import com.kisssum.bianqian3.Data.Entity.Bill

@Dao
interface BillDao {
    @Query("select * from bill")
    fun getAll(): List<Bill>

    @Insert
    fun adds(vararg bill: Bill)

    @Delete
    fun dels(vararg bill: Bill)

    @Query("delete from bill")
    fun delAll()

    @Update
    fun updates(vararg bill: Bill)

    @Query("select count(*) from Bill")
    fun getCount(): Int

    @Query("select * from Bill where uid=:id")
    fun getBill(id: Int): Bill
}
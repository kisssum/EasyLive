package com.kisssum.bianqian3.Data.Dao

import androidx.room.*
import com.kisssum.bianqian3.Data.Entity.Bill

@Dao
interface BillDao {
    @Query("select * from bill order by time desc")
    fun getAll(): List<Bill>

    @Insert
    fun inserts(vararg bill: Bill)

    @Delete
    fun dels(vararg bill: Bill)

    @Query("delete from bill")
    fun delAll()

    @Update
    fun updates(vararg bill: Bill)

    @Query("select count(*) from Bill")
    fun getCount(): Int

    @Query("select * from Bill where uid=:id")
    fun findBill(id: Int): Bill
}
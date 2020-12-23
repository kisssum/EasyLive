package com.kisssum.bianqian3.Data.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Bill(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,

    @ColumnInfo(name = "price")
    var price: Double = 0.0,

    @ColumnInfo(name = "notes")
    var notes: String = "",

    @ColumnInfo(name = "type")
    var type: Int = 0,

    @ColumnInfo(name = "time")
    var time: Long = Calendar.getInstance().timeInMillis,
)

// 编号，支出/收入，笔记，类型，时间
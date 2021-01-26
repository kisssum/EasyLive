package com.kisssum.bianqian3.Data.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Meno(
    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "text")
    var text: String = "",
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0

    @ColumnInfo(name = "createTime")
    var createTime: Long = Calendar.getInstance().timeInMillis

    @ColumnInfo(name = "lastTime")
    var lastTime: Long = Calendar.getInstance().timeInMillis
}
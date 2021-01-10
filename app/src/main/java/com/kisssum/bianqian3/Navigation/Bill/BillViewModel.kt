package com.kisssum.bianqian3.Navigation.Bill

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.kisssum.bianqian3.Data.Database.BillDatabase
import com.kisssum.bianqian3.Data.Entity.Bill

class BillViewModel(application: Application) : AndroidViewModel(application) {
    private var billData: MutableLiveData<List<Bill>> = MutableLiveData()
    private val db =
        Room.databaseBuilder(application, BillDatabase::class.java, "bill").allowMainThreadQueries()
            .build()

    init {
        update()
    }

    fun getBillData() = billData

    fun update() {
        val billDao = db.billDao()
        billData.value = billDao.getAll()
    }

    fun getBillDao() = db.billDao()
}
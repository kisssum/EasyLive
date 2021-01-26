package com.kisssum.bianqian3.Navigation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.kisssum.bianqian3.Data.Database.BillDatabase
import com.kisssum.bianqian3.Data.Database.MenoDatabase
import com.kisssum.bianqian3.Data.Entity.Bill
import com.kisssum.bianqian3.Data.Entity.Meno

class ViewModel(application: Application) : AndroidViewModel(application) {
    private var menoData: MutableLiveData<List<Meno>> = MutableLiveData()
    private val menoDB =
        Room.databaseBuilder(application, MenoDatabase::class.java, "meno").allowMainThreadQueries()
            .build()

    init {
        reLoadMenoData()
    }

    fun getMenoData() = menoData
    fun getMenoDao() = menoDB.menoDao()
    fun reLoadMenoData() {
        menoData.value = menoDB.menoDao().getAll()
    }

//    private var billData: MutableLiveData<List<Bill>> = MutableLiveData()
//    private val db =
//        Room.databaseBuilder(application, BillDatabase::class.java, "bill").allowMainThreadQueries()
//            .build()
//    private val billDao = db.billDao()
//
//    init {
//        update()
//    }
//
//    fun getBillData() = billData
//
//    fun update() {
//        billData.value = billDao.getAll()
//    }
//
//    fun getBillDao() = billDao
}
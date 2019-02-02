package com.example.dario.pcbakovic.repository

import com.example.dario.pcbakovic.database.dao.ItemDataDao
import com.example.dario.pcbakovic.database.entity.Item
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ShoppingItemsApiService @Inject constructor(private val itemsDao: ItemDataDao) {

    fun getShoppingItemsFromDB(): Observable<List<Item>> {
        return itemsDao.getShoppingItems()
            .toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun storeItemsToDb(items: List<Item>): Observable<Unit> {
        return Observable.fromCallable { itemsDao.insertData(items) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
    }

    fun deleteAllItems(): Observable<Unit> {
        return Observable.fromCallable { itemsDao.deleteAll() }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
    }

    fun deleteItemFromDb(item: Item): Observable<Unit> {
        return Observable.fromCallable { itemsDao.deleteItem(item) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
    }

    fun updateDbItem(item: Item): Observable<Unit> {
        return Observable.fromCallable { itemsDao.updateItem(item) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
    }
}
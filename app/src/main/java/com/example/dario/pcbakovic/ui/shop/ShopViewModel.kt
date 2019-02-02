package com.example.dario.pcbakovic.ui.shop

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.dario.pcbakovic.database.entity.Item
import com.example.dario.pcbakovic.repository.ShoppingItemsApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class ShopViewModel @Inject constructor(private val api: ShoppingItemsApiService) : ViewModel() {

    private val disposable = CompositeDisposable()

    val shoppingItems: MutableLiveData<ArrayList<Item>> = MutableLiveData()
    val showEmptyListHolder: MutableLiveData<Boolean> = MutableLiveData()

    init {
        getItemsFromDB()
    }

    fun deleteItemFromDb(item: Item) {
        disposable.add(
            api.deleteItemFromDb(item)
                .subscribe()
        )
    }

    fun storeItemsToDb(items: ArrayList<Item>) {
        disposable.addAll(
            api.storeItemsToDb(items)
                .subscribe()
        )
    }


    fun deleteAll() {
        disposable.addAll(
            api.deleteAllItems()
                .subscribe()
        )
    }

    private fun getItemsFromDB() {
        disposable.add(api.getShoppingItemsFromDB()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { it ->
                    if (it.isNotEmpty()) {
                        Timber.d("Apps registry downloaded successfully!")
                        onRetrieveItems(it as ArrayList<Item>)
                    } else {
                        showEmptyListHolder.value = true
                    }
                },
                { error ->
                    Timber.e("Error while downloading items from db: ${error.localizedMessage}")
                }
            ))
    }

    private fun onRetrieveItems(items: ArrayList<Item>) {
        shoppingItems.value = items
    }

    override fun onCleared() {
        super.onCleared()
        if (!disposable.isDisposed)
            disposable.dispose()
    }
}
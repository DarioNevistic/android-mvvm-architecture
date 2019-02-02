package com.example.dario.pcbakovic.ui.catalogue

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dario.pcbakovic.R
import com.example.dario.pcbakovic.base.BaseFragment
import com.example.dario.pcbakovic.util.CirclePagerIndicatorDecoration
import com.google.firebase.firestore.FirebaseFirestore


class CatalogueFragment: BaseFragment() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var catalogueList: List<String> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_catalogue, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db.collection("catalogue")
            .get()
            .addOnCompleteListener {
                for (document in it.result!!) {
                    catalogueList = document.data["images"] as List<String>

                    setupRecyclerView()
                }
            }
    }

    private fun setupRecyclerView() {
        var recyclerView = activity!!.findViewById(R.id.catalogue_recycler_view) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val snapHelper = PagerSnapHelper()
        recyclerView.addItemDecoration(CirclePagerIndicatorDecoration())
        snapHelper.attachToRecyclerView(recyclerView)

        val adapter = CatalogueAdapter(catalogueList, activity!!)
        recyclerView.adapter = adapter
    }

    companion object {
        fun newInstance(): CatalogueFragment {
            return CatalogueFragment()
        }
    }
}
package com.example.dario.pcbakovic.ui.home

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dario.pcbakovic.R
import com.example.dario.pcbakovic.base.BaseFragment
import com.example.dario.pcbakovic.model.ItemModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment() {

    private var adapter: ActionFirestoreRecyclerAdapter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val recyclerView = home_recycler_view
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val rootRef = FirebaseFirestore.getInstance()
        val query = rootRef.collection("actions")
            .orderBy("date", Query.Direction.DESCENDING)


        val options = FirestoreRecyclerOptions.Builder<ItemModel>()
            .setQuery(query, ItemModel::class.java)
            .build()


        adapter = ActionFirestoreRecyclerAdapter(options)
        recyclerView.adapter = adapter
    }

    private inner class ActionFirestoreRecyclerAdapter internal constructor(options: FirestoreRecyclerOptions<ItemModel>)
        : FirestoreRecyclerAdapter<ItemModel, CustomHolder>(options) {
        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CustomHolder {
            return CustomHolder(activity!!,
                LayoutInflater.from(activity!!)
                    .inflate(R.layout.item_action, parent, false)
            )
        }

        override fun onBindViewHolder(holder: CustomHolder, position: Int, model: ItemModel) {
            holder.bind(model)
        }
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()

        if (adapter != null) {
            adapter!!.stopListening()
        }
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}
package com.sermage.mymoviecollection.screens.ui.search

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sermage.mymoviecollection.R
import com.sermage.mymoviecollection.adapters.MovieAdapter
import com.sermage.mymoviecollection.screens.ui.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_search.*


class MainSearchFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.actionBar?.hide()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val card=view.findViewById<CardView>(R.id.cardViewTitleSearch)
        card.setOnClickListener {
            view.findNavController().navigate(R.id.action_navigation_search_to_searchingFragment)
        }
    }

    override fun onStop() {
        super.onStop()
        activity?.actionBar?.show()
    }
}
package com.sermage.mymoviecollection.screens.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sermage.mymoviecollection.R
import com.sermage.mymoviecollection.adapters.FavoritesViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_favorites.*


class FavoritesFragment : Fragment() {


    private lateinit var favoritesCollectionAdapter: FavoritesViewPagerAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoritesCollectionAdapter = FavoritesViewPagerAdapter(this)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = favoritesCollectionAdapter
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        TabLayoutMediator(tabLayout, pager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.title_tab_item_movies)
                1 -> tab.text = getString(R.string.title_tab_item_tvshows)
            }
        }.attach()
    }

}
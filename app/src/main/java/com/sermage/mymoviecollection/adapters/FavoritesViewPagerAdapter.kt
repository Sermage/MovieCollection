package com.sermage.mymoviecollection.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sermage.mymoviecollection.screens.favorites.FavoriteItemFragment

class FavoritesViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val ARG_INIT = "number"
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = FavoriteItemFragment()
        fragment.arguments = Bundle().apply {
            putInt(ARG_INIT, position + 1)
        }
        return fragment
    }
}
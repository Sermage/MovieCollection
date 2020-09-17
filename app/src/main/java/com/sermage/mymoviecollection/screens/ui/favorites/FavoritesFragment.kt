package com.sermage.mymoviecollection.screens.ui.favorites

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sermage.mymoviecollection.R
import com.sermage.mymoviecollection.adapters.FavoritesViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_favorites.*


class FavoritesFragment : Fragment() {


//    private lateinit var favoritesAdapter:MovieAdapter

    private lateinit var favoritesCollectionAdapter: FavoritesViewPagerAdapter
    private lateinit var viewPager:ViewPager2

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoritesCollectionAdapter= FavoritesViewPagerAdapter(this)
        viewPager=view.findViewById(R.id.pager)
        viewPager.adapter=favoritesCollectionAdapter
        val tabLayout=view.findViewById<TabLayout>(R.id.tab_layout)
        TabLayoutMediator(tabLayout,pager){tab,position->
           when(position){
               0->tab.text="Фильмы"
               1->tab.text="Сериалы"
           }
        }.attach()

//        val recyclerViewFavorites=view.findViewById<RecyclerView>(R.id.recyclerViewFavorites)
//        recyclerViewFavorites.layoutManager=GridLayoutManager(context,getColumnCount())
//        favoritesAdapter= MovieAdapter()
//        recyclerViewFavorites.adapter=favoritesAdapter
//        favoritesAdapter.posterListener=object :MovieAdapter.OnClickMoviePosterListener{
//            override fun onClickMoviePoster(position: Int) {
//                val movie=favoritesAdapter.movies[position]
//                view.findNavController().navigate(R.id.action_navigation_favorites_to_movieDetailsFragment,
//                    MovieDetailsFragment.newInstance(movie).arguments)
//            }
//
//        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        val favoritesViewModel:FavoritesViewModel by viewModels()
//        favoritesViewModel.favoritesList.observe(viewLifecycleOwner, Observer {
//            favoritesAdapter.movies=it.toMutableList()
//        })
    }

    private fun getColumnCount(): Int {
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val width = (displayMetrics.widthPixels / displayMetrics.density).toInt()
        return if (width / 92 > 2) width / 92 else 2
    }
}
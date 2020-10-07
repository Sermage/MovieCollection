package com.sermage.mymoviecollection.screens.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.sermage.mymoviecollection.R


class MainSearchFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val actionTitle = resources.getString(R.string.action_title)
        val fantasyTitle = resources.getString(R.string.fantasy_title)
        val scifiTitle = resources.getString(R.string.scifi_title)
        val comedyTitle = resources.getString(R.string.comedy_title)
        val horrorTitle = resources.getString(R.string.horror_title)
        val warTitle = resources.getString(R.string.war_title)
        val romanceTitle = resources.getString(R.string.romance_title)
        val historyTitle = resources.getString(R.string.history_title)
        val criminalTitle = resources.getString(R.string.crime_title)
        val adventureTitle = resources.getString(R.string.adventure_title)
        val card = view.findViewById<CardView>(R.id.cardViewTitleSearch)
        card.setOnClickListener {
            view.findNavController().navigate(R.id.action_navigation_search_to_searchingFragment)
        }
        val actionPoster = view.findViewById<CardView>(R.id.cardViewActionGenre)
        actionPoster.setOnClickListener {
            view.findNavController().navigate(
                R.id.action_navigation_search_to_genreFragment, GenreFragment.newInstance(
                    ARG_ACTION_MOVIE, actionTitle
                ).arguments
            )
        }
        val fantasyPoster = view.findViewById<CardView>(R.id.cardViewFantasyGenre)
        fantasyPoster.setOnClickListener {
            view.findNavController().navigate(
                R.id.action_navigation_search_to_genreFragment, GenreFragment.newInstance(
                    ARG_FANTASY_MOVIE, fantasyTitle
                ).arguments
            )
        }

        val sciFiPoster = view.findViewById<CardView>(R.id.cardViewScifiGenre)
        sciFiPoster.setOnClickListener {
            view.findNavController().navigate(
                R.id.action_navigation_search_to_genreFragment, GenreFragment.newInstance(
                    ARG_SCI_FI_MOVIE, scifiTitle
                ).arguments
            )
        }
        val comedyPoster = view.findViewById<CardView>(R.id.cardViewComedyGenre)
        comedyPoster.setOnClickListener {
            view.findNavController().navigate(
                R.id.action_navigation_search_to_genreFragment, GenreFragment.newInstance(
                    ARG_COMEDY_MOVIE, comedyTitle
                ).arguments
            )
        }
        val horrorPoster = view.findViewById<CardView>(R.id.cardViewHorrorGenre)
        horrorPoster.setOnClickListener {
            view.findNavController().navigate(
                R.id.action_navigation_search_to_genreFragment, GenreFragment.newInstance(
                    ARG_HORROR_MOVIE, horrorTitle
                ).arguments
            )
        }
        val warPoster = view.findViewById<CardView>(R.id.cardViewWarGenre)
        warPoster.setOnClickListener {
            view.findNavController().navigate(
                R.id.action_navigation_search_to_genreFragment, GenreFragment.newInstance(
                    ARG_WAR_MOVIE, warTitle
                ).arguments
            )
        }
        val romancePoster = view.findViewById<CardView>(R.id.cardViewRomanticGenre)
        romancePoster.setOnClickListener {
            view.findNavController().navigate(
                R.id.action_navigation_search_to_genreFragment, GenreFragment.newInstance(
                    ARG_ROMANTIC_MOVIE, romanceTitle
                ).arguments
            )
        }
        val historyPoster = view.findViewById<CardView>(R.id.cardViewHistoryGenre)
        historyPoster.setOnClickListener {
            view.findNavController().navigate(
                R.id.action_navigation_search_to_genreFragment, GenreFragment.newInstance(
                    ARG_HISTORY_MOVIE, historyTitle
                ).arguments
            )
        }
        val crimePoster = view.findViewById<CardView>(R.id.cardViewCriminalGenre)
        crimePoster.setOnClickListener {
            view.findNavController().navigate(
                R.id.action_navigation_search_to_genreFragment, GenreFragment.newInstance(
                    ARG_CRIMINAL_MOVIE, criminalTitle
                ).arguments
            )
        }
        val adventurePoster = view.findViewById<CardView>(R.id.cardViewAdventureGenre)
        adventurePoster.setOnClickListener {
            view.findNavController().navigate(
                R.id.action_navigation_search_to_genreFragment, GenreFragment.newInstance(
                    ARG_ADVENTURE_MOVIE, adventureTitle
                ).arguments
            )
        }

    }

    companion object {
        private const val ARG_ACTION_MOVIE = "28"
        private const val ARG_FANTASY_MOVIE = "14"
        private const val ARG_SCI_FI_MOVIE = "878"
        private const val ARG_COMEDY_MOVIE = "35"
        private const val ARG_HORROR_MOVIE = "27"
        private const val ARG_WAR_MOVIE = "10752"
        private const val ARG_ROMANTIC_MOVIE = "10749"
        private const val ARG_HISTORY_MOVIE = "36"
        private const val ARG_CRIMINAL_MOVIE = "80"
        private const val ARG_ADVENTURE_MOVIE = "12"

    }

}
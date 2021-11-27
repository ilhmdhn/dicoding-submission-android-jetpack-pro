package com.ilhmdhn.movieapps.utils

import com.ilhmdhn.movieapps.data.source.local.entity.ListMovieEntity
import com.ilhmdhn.movieapps.data.source.local.entity.ListTvShowEntity
import com.ilhmdhn.movieapps.data.source.local.entity.MovieEntity
import com.ilhmdhn.movieapps.data.source.local.entity.TvShowEntity
import com.ilhmdhn.movieapps.data.source.remote.response.ResultItems
import com.ilhmdhn.movieapps.data.source.remote.response.ShowResponse

object DataDummy {
    fun getMovieData(): List<ListMovieEntity> =
        arrayListOf(
            ListMovieEntity(
                527774,
                "/lPsD10PP4rgUGiGR4CCXA6iY0QQ.jpg",
                "Raya and the Last Dragon",
                "Long ago, in the fantasy world of Kumandra, humans and dragons lived together in harmony. But when an evil force threatened the land, the dragons sacrificed themselves to save humanity. Now, 500 years later, that same evil has returned and it’s up to a lone warrior, Raya, to track down the legendary last dragon to restore the fractured land and its divided people."
            ),
            ListMovieEntity(
                793723,
                "/fFRq98cW9lTo6di2o4lK1qUAWaN.jpg",
                "Sentinelle",
                "Transferred home after a traumatizing combat mission, a highly trained French soldier uses her lethal skills to hunt down the man who hurt her sister."
            ),
            ListMovieEntity(
                587996,
                "/dWSnsAGTfc8U27bWsy2RfwZs0Bs.jpg",
                "Below Zero",
                "When a prisoner transfer van is attacked, the cop in charge must fight those inside and outside while dealing with a silent foe: the icy temperatures."
            )
        )

    fun getTvShowData(): List<ListTvShowEntity> =
        arrayListOf(
            ListTvShowEntity(
                69050,
                "Set in the present, the series offers a bold, subversive take on Archie, Betty, Veronica and their friends, exploring the surreality of small-town life, the darkness and weirdness bubbling beneath Riverdale’s wholesome facade.",
                "Riverdale",
                "/wRbjVBdDo5qHAEOVYoMWpM58FSA.jpg"
            ),
            ListTvShowEntity(
                60735,
                "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \"meta-human\" who was created in the wake of the accelerator explosion -- and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won't be long before the world learns what Barry Allen has become...The Flash.",
                "The Flash",
                "/lJA2RCMfsWoskqlQhXPSLFQGXEJ.jpg"
            )
        )

    fun getMovieDetail(id: Int): ArrayList<MovieEntity> {
        val detailMovie = ArrayList<MovieEntity>()
        detailMovie.add(
            MovieEntity(
                527774,
                "/lPsD10PP4rgUGiGR4CCXA6iY0QQ.jpg",
                "Raya and the Last Dragon",
                "en",
                "Long ago, in the fantasy world of Kumandra, humans and dragons lived together in harmony. But when an evil force threatened the land, the dragons sacrificed themselves to save humanity. Now, 500 years later, that same evil has returned and it’s up to a lone warrior, Raya, to track down the legendary last dragon to restore the fractured land and its divided people.",
                3686.429,
                8.5,
                false
            )
        )
        return detailMovie
    }

    fun getTvShowDetail(id: Int): ArrayList<TvShowEntity> {
        val detailMovie = ArrayList<TvShowEntity>()
        detailMovie.add(
            TvShowEntity(
                156676,
                "/fIT6Y6O3cUX1X8qY8pZgzEvxUDQ.jpg",
                "Sam and Bucky",
                5201.914,
                "en",
                "Sam and Bucky go to a criminal safe haven to find information about the Super Soldier serum.",
                7.8,
                false
            )
        )
        return detailMovie
    }
}
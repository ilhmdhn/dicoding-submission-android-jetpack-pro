package com.ilhmdhn.movieapps.utils

import androidx.paging.PagedList
import org.mockito.Mockito

object PagedListUtils {

    @Suppress("UNCHECKED_CAST")
    fun <T> mockPagedList(list: List<T>): PagedList<T> {
        val pagedList = Mockito.mock(PagedList::class.java) as PagedList<T>
        Mockito.`when`(pagedList[Mockito.anyInt()]).then {
            val index = it.arguments.first() as Int
            list[index]
        }
        Mockito.`when`(pagedList.size).thenReturn(list.size)
        return pagedList
    }
}
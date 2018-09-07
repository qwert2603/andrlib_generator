package com.qwert2603.anth

import com.qwert2603.andrlib.base.mvi.load_refresh.LRModel
import com.qwert2603.andrlib.base.mvi.load_refresh.LRViewState
import com.qwert2603.andrlib.base.mvi.load_refresh.list.ListModel
import com.qwert2603.andrlib.base.mvi.load_refresh.list.ListViewState
import com.qwert2603.andrlib.generator.GenerateLRChanger
import com.qwert2603.andrlib.generator.GenerateListChanger
import com.qwert2603.andrlib.model.IdentifiableLong

@GenerateLRChanger
@GenerateListChanger
data class SomeVS(
        val i: Int,
        override val lrModel: LRModel,
        override val listModel: ListModel,
        override val showingList: List<IdentifiableLong>
) : LRViewState, ListViewState<IdentifiableLong>

@GenerateLRChanger
data class SomeVS_2(
        val i: Int,
        override val lrModel: LRModel
) : LRViewState

@GenerateLRChanger
@GenerateListChanger
data class SomeVS_3<Q : IdentifiableLong>(
        val i: Int,
        override val lrModel: LRModel,
        override val listModel: ListModel,
        override val showingList: List<Q>
) : ListViewState<Q>


@GenerateLRChanger
data class SomeVS_4<W, E, R, T>(
        val i: Int,
        val w: W,
        val e: E,
        val r: R,
        val t: T,
        override val lrModel: LRModel
) : LRViewState
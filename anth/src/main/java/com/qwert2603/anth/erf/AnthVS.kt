package com.qwert2603.anth.erf

import com.qwert2603.andrlib.base.mvi.load_refresh.LRModel
import com.qwert2603.andrlib.base.mvi.load_refresh.list.ListModel
import com.qwert2603.andrlib.base.mvi.load_refresh.list.ListViewState
import com.qwert2603.andrlib.generator.GenerateLRChanger
import com.qwert2603.andrlib.generator.GenerateListChanger
import com.qwert2603.andrlib.model.IdentifiableLong

@GenerateLRChanger
@GenerateListChanger
data class AnthVS<Q : IdentifiableLong>(
        val i: Int,
        override val lrModel: LRModel,
        override val listModel: ListModel,
        override val showingList: List<Q>
) : ListViewState<Q>



package com.qwert2603.anth

import com.qwert2603.andrlib.base.mvi.load_refresh.list.ListModel
import com.qwert2603.andrlib.base.mvi.load_refresh.list.ListViewState
import com.qwert2603.andrlib.base.mvi.load_refresh.list.listModelChangerInstance
import com.qwert2603.andrlib.base.mvi.load_refresh.lrModelChangerInstance
import com.qwert2603.andrlib.generated.LRModelChangerImpl
import com.qwert2603.andrlib.generated.ListModelChangerImpl
import com.qwert2603.anth.erf.AnthVS

object App {
    init {
        lrModelChangerInstance = LRModelChangerImpl()

        listModelChangerInstance = object : ListModelChangerImpl() {
            override fun <VS : ListViewState<*>> changeListModel(vs: VS, listModel: ListModel): VS {
                if (vs is AnthVS<*> && vs.i == 42) {
                    return vs
                }
                return super.changeListModel(vs, listModel)
            }
        }
    }
}
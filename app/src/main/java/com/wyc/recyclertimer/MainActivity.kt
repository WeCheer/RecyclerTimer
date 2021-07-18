package com.wyc.recyclertimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val mList = mutableListOf<CountDownTimerBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = TimerAdapter()
        getDataList()
        adapter.setNewData(mList)
        adapter.bindAdapterToRecyclerView(recyclerView)
    }

    private fun getDataList() {
        var bean = CountDownTimerBean(600L * 1000, false)
        mList.add(bean)
        bean = CountDownTimerBean(782L * 1000, false)
        mList.add(bean)
        bean = CountDownTimerBean(711 * 1000, false)
        mList.add(bean)
        bean = CountDownTimerBean(959 * 1000, false)
        mList.add(bean)
        bean = CountDownTimerBean(641 * 1000, false)
        mList.add(bean)
        bean = CountDownTimerBean(236 * 1000, false)
        mList.add(bean)
        bean = CountDownTimerBean(855 * 1000, false)
        mList.add(bean)
        bean = CountDownTimerBean(212 * 1000, false)
        mList.add(bean)
        bean = CountDownTimerBean(112 * 1000, false)
        mList.add(bean)
        bean = CountDownTimerBean(345 * 1000, false)
        mList.add(bean)
        bean = CountDownTimerBean(666 * 1000, false)
        mList.add(bean)
        bean = CountDownTimerBean(111 * 1000, false)
        mList.add(bean)
        bean = CountDownTimerBean(123 * 1000, false)
        mList.add(bean)
    }
}

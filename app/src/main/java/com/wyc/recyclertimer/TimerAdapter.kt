package com.wyc.recyclertimer

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*


/**
 *作者： wyc
 * <p>
 * 创建时间： 2020/12/25 17:32
 * <p>
 * 文件名字： com.wyc.recyclertimer
 * <p>
 * 类的介绍：
 */
class TimerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = "TimerAdapter"

    private var mList: MutableList<CountDownTimerBean> = mutableListOf()

    //线程调度，用来更新列表
    private var mHandler: Handler

    private var mTimer: Timer? = null
    private var mTask: CountDownTask? = null

    init {
        mHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    1 -> notifyItemChanged(msg.arg1, mList[msg.arg1])
                }
            }
        }
        mTask = CountDownTask()
    }

    fun bindAdapterToRecyclerView(view: RecyclerView) {
        view.adapter = this
    }

    /**
     * 设置新的数据源
     *
     * @param list 数据
     */
    fun setNewData(list: MutableList<CountDownTimerBean>) {
        destroy()
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
        if (mTimer == null) {
            mTimer = Timer()
        }
        mTimer?.schedule(mTask, 0, 1000)
    }

    /**
     * 销毁资源
     */
    fun destroy() {
        mHandler.removeMessages(1)
        mTimer?.cancel()
        mTimer?.purge()
        mTimer = null
    }

    inner class CountDownTask : TimerTask() {
        override fun run() {
            if (mList.isEmpty()) {
                return
            }
            val size: Int = mList.size
            var bean: CountDownTimerBean
            var totalTime: Long
            for (i in 0 until size) {
                bean = mList[i]
                if (!bean.isPause) { //不处于暂停状态
                    totalTime = bean.totalTime - 1000
                    if (totalTime <= 0) {
                        bean.isPause = true
                        bean.totalTime = 0
                    }
                    bean.totalTime = totalTime
                    val message: Message = mHandler.obtainMessage(1)
                    message.arg1 = i
                    mHandler.sendMessage(message)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_count_down_timer, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val bean = mList[position]
        val day = bean.totalTime / (1000 * 60 * 60 * 24)
        val hour = bean.totalTime / (1000 * 60 * 60) - day * 24
        val min = bean.totalTime / (60 * 1000) - day * 24 * 60 - hour * 60
        val s = bean.totalTime / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60
        if (holder is Holder) {
            holder.ivIcon.setImageResource(R.mipmap.ic_launcher_round)
            holder.tvTime.text = "剩余时间: " + day + "天" + hour + "小时" + min + "分" + s + "秒"
            holder.btnAction.text = if (bean.isPause) "开始" else "暂停"
            holder.btnAction.isEnabled = bean.totalTime != 0L
            holder.btnAction.setOnClickListener {
                if (bean.isPause) {
                    bean.isPause = false
                    holder.btnAction.text = "暂停"
                } else {
                    bean.isPause = true
                    holder.btnAction.text = "开始"
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
            return
        }
        //更新某个控件,比如说只需要更新时间信息，其他不用动
        val (totalTime, isPause) = mList[position]
        val day = totalTime / (1000 * 60 * 60 * 24)
        val hour = totalTime / (1000 * 60 * 60) - day * 24
        val min = totalTime / (60 * 1000) - day * 24 * 60 - hour * 60
        val s = totalTime / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60
        if (holder is Holder) {
            holder.tvTime.text = "剩余时间: " + day + "天" + hour + "小时" + min + "分" + s + "秒"
            holder.btnAction.text = if (isPause) "开始" else "暂停"
            holder.btnAction.isEnabled = totalTime != 0L
            if (totalTime == 0L) {
                Log.d(TAG, "onBindViewHolder network")
            }
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivIcon: ImageView = itemView.findViewById(R.id.iv_icon)
        val tvTime: TextView = itemView.findViewById(R.id.tv_time)
        val btnAction: TextView = itemView.findViewById(R.id.btn_action)
    }
}
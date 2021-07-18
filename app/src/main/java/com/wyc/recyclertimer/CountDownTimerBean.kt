package com.wyc.recyclertimer

import android.os.Parcel
import android.os.Parcelable

/**
 *作者： wyc
 * <p>
 * 创建时间： 2020/12/25 17:33
 * <p>
 * 文件名字： com.wyc.recyclertimer
 * <p>
 * 类的介绍：
 */
data class CountDownTimerBean(var totalTime: Long, var isPause: Boolean) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(totalTime)
        parcel.writeByte(if (isPause) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<CountDownTimerBean> {
            override fun createFromParcel(parcel: Parcel): CountDownTimerBean {
                return CountDownTimerBean(parcel)
            }

            override fun newArray(size: Int): Array<CountDownTimerBean?> {
                return arrayOfNulls(size)
            }
        }
    }
}
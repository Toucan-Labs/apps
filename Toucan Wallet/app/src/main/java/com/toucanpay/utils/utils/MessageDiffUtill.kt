package com.toucanpay.utils.utils

import androidx.recyclerview.widget.DiffUtil
import com.toucanpay.data.models.Message

class MessageDiffUtil(
    private val oldList: List<Message>,
    private val newList: List<Message>
): DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}
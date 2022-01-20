package com.aks.testrecycler

import androidx.recyclerview.widget.DiffUtil


class ConversationsDiffCallback(
    private val oldList: List<ConversationItem>,
    private val newList: List<ConversationItem>
) : AdapterDiffCallback<ConversationItem>(oldList, newList) {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        val oldMessage = (oldItem as ConversationItem).conversation
        val newMessage = (newItem as ConversationItem).conversation
        return  oldMessage == newMessage
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }
}

open class AdapterDiffCallback<O>(private val oldList: List<O>, private val newList: List<O>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            = oldList[oldItemPosition] === newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            = oldList[oldItemPosition] == newList[newItemPosition]
}
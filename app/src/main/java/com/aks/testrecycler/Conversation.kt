package com.aks.testrecycler

import android.graphics.PorterDuff
import android.util.Log
import androidx.core.content.ContextCompat
import coil.imageLoader
import coil.request.ImageRequest
import com.aks.testrecycler.databinding.ItemBinding
import java.util.*

data class Conversation(
    val name: String,
    val type: Int,
    val imagePath: String
){
    val theme: Theme = Theme.getRandom()
}

enum class Theme(
    val colorRes: Int,
    val colorSlight: Int
){
    GREEN(R.color.colorFullGreen, R.color.colorFulSlightTurquoise),
    RED(R.color.colorRed, R.color.colorSlightRed),
    VIOLET(R.color.colorFulViolet, R.color.colorFullSlightViolet),
    PINK(R.color.colorFulPink, R.color.colorFulSlightPink),
    PURPLE(R.color.colorFulPurple, R.color.colorFulSlightPurple),
    BLUE(R.color.colorBlue, R.color.colorFullSlightBlue),
    CORAL(R.color.colorFullCoral, R.color.colorFullCoralAlpha);
    companion object {
        fun getTheme(theme: String?) = values().find { it.name.equals(theme, ignoreCase = true) } ?: GREEN
        fun getRandom() = values().toList().shuffled().first()
    }
}

class ConversationItem(val conversation: Conversation, val position: Int){
    fun onMute(){}
    fun onPin(){}

    var isFirst = true
    fun onBind(holder: ViewHolder){
        //val bind = (holder.binding as ItemSwipeBinding)
        val bind = (holder.binding as ItemBinding)
        //val cv = bind.cvAvatar
        /*Log.d("ConversationItem ","${isFirst}")
        if (isFirst) {
            if (conversation.type == 1) {
                val request = ImageRequest.Builder(cv.context)
                    .data("https://www.college-declic.fr/wp-content/uploads/2019/01/image3.jpg")
                    .target(
                        { cv.setImageDrawable(ContextCompat.getDrawable(cv.context, R.drawable.ic_avatar_placeholder)) },
                        { cv.setImageDrawable(ContextCompat.getDrawable(cv.context, R.drawable.ic_avatar_placeholder)) },
                        { cv.setImageDrawable(it) }
                    )
                    .build()
                cv.context.imageLoader.enqueue(request)
            } else {*/
        /*if (conversation.type == 2) {
            val text = bind.tvName
            text.text = conversation.name.let {
                (it[0].toString() + (it.getOrNull(it.substringBefore(' ').length + 1)
                    ?: "")).toUpperCase(
                    Locale.ROOT
                )
            }
            conversation.theme.let {
                text.setTextColor(ContextCompat.getColor(text.context, it.colorRes))
                //cv.setImageDrawable(ContextCompat.getDrawable(text.context, R.drawable.ic_masked_circle))
                //cv.setColorFilter(ContextCompat.getColor(text.context, it.colorSlight), PorterDuff.Mode.SRC_IN)
            }
        }*/
            //}
            //isFirst = false
        //}
        bind.item = this
    }
}
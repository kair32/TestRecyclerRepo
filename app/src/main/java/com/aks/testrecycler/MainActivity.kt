package com.aks.testrecycler

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import coil.Coil
import coil.ImageLoader
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import coil.util.CoilUtils
import com.aks.testrecycler.databinding.ActivityMainBinding
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.OkHttpClient
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.rv.adapter = ConversationsAdapter()
    }
}

@BindingAdapter(value = ["loadChatAvatar", "defaultChatAvatar"], requireAll = false)
fun setAvatarChat(fv: FrameLayout, conversation: Conversation?, defaultDraw: Drawable?){
    if (conversation == null) return
    val cv = fv.findViewById<CircleImageView>(R.id.cv_avatar)
    if (conversation.type == 1) {
        /*cv.load(conversation.imagePath){
            crossfade(true)
            placeholder(defaultDraw)
            transformations(CircleCropTransformation())
        }*/
        val request = ImageRequest.Builder(cv.context)
            .data("https://www.college-declic.fr/wp-content/uploads/2019/01/image3.jpg")
            .target(
                { defaultDraw?.let { cv.setImageDrawable(it)} },
                { defaultDraw?.let { cv.setImageDrawable(it)} },
                { cv.setImageDrawable(it) }
            )
            .build()
        cv.context.imageLoader.enqueue(request)
    }
    else{
        val text = fv.findViewById<TextView>(R.id.tv_name)
        text.text = conversation.name.let {
            (it[0].toString() + (it.getOrNull(it.substringBefore(' ').length + 1) ?: "")).toUpperCase(
                Locale.ROOT)
        }
        conversation.theme.let {
            text.setTextColor(ContextCompat.getColor(text.context, it.colorRes))
            cv.setImageDrawable(ContextCompat.getDrawable(text.context, R.drawable.ic_masked_circle))
            cv.setColorFilter(ContextCompat.getColor(text.context, it.colorSlight), PorterDuff.Mode.SRC_IN)
        }
    }
}
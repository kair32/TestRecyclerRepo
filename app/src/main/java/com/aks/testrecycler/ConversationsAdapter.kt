package com.aks.testrecycler

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aks.testrecycler.databinding.ItemBinding
import java.util.*

class ViewHolder(val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root), LifecycleOwner {

    fun setItem(conversationItem: ConversationItem, position: Int){
        val bind = (binding as ItemBinding)
        Log.d("ViewHolderItem","Position: ${conversationItem.position}, $position")
        val conversation = conversationItem.conversation
        if (conversation.type == 2) {
            val text = bind.tvName
            text.text = conversation.name.let {
                (it[0].toString() + (it.getOrNull(it.substringBefore(' ').length + 1)
                        ?: "")).toUpperCase(
                        Locale.ROOT
                )
            }
            conversation.theme.let {
                text.setTextColor(ContextCompat.getColor(text.context, it.colorRes))
            }
        }
    }

    private val lifecycleRegistry = LifecycleRegistry(this)

    init {
        lifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
    }
    fun markDetach() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)//on the test
    }
    fun markAttach() {
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)//on the test
    }
    override fun getLifecycle(): Lifecycle = lifecycleRegistry
}

val ss = listOf<Conversation>(
    Conversation("чат", 1,"https://cdn.humoraf.ru/wp-content/uploads/2017/08/beautiful-pictures-on-the-desktop-summer-nature-21.jpg"),
    Conversation("Сергей Прокаев",1,"https://img2.goodfon.ru/original/1920x1408/0/3d/zamok-gogencollern.jpg"),
    Conversation("тест",2,"https://wallbox.ru/wallpapers/main2/201847/ozero-gory.jpg"),
    Conversation("Елизавета Малахина",1,"https://avatars.mds.yandex.net/get-pdb/2160551/24512881-4252-40c8-997c-9cf43c7a00e8/s1200?webp=false"),
    Conversation("Бурнаев П., Эскобар П.",1,"https://agnitime.com/wp-content/uploads/2020/01/4k-ultra-hd-nature-high-resolution-pictures.jpg"),
    Conversation("testt",2,"https://c.wallhere.com/photos/de/4e/2560x1600_px_AT_lake_sunset-1716913.jpg!d"),
    Conversation("тест",1,"https://img2.fonwall.ru/o/uc/vesennyaya-tropa-vesna-derevya.jpg"),
    Conversation("test 2",1,"https://c3.emosurf.com/0005bY007gbB/rabstol_net_winter_20.jpg"),
    Conversation("test 1",1,"https://cdn.fishki.net/upload/post/2019/09/07/3079295/9cc12bc1d34ecfe71c106482f17a02c9.jpg"),
    Conversation("111",1,"https://c.wallhere.com/photos/a3/43/mountains_high_snow-988481.jpg!d"),
    Conversation("авы",2,""),
    Conversation("qw",1,"https://img2.goodfon.ru/original/2880x1800/d/6a/paris-france-la-tour-eiffel-490.jpg"),
    Conversation("he",2,""),
    Conversation("hello world",1,"https://img1.goodfon.ru/wallpaper/nbig/7/63/forest-hature-landscape-water.jpg"),
    Conversation("привет",1,"https://www.ejin.ru/wp-content/uploads/2017/09/4-629.jpg"),
    Conversation("пока",2,""),
    Conversation("тест",1,"https://www.1zoom.ru/big2/174/251457-svetik.jpg"),
    Conversation("молодец",2,""),
    Conversation("толкайся",2,""),
    Conversation("шагай",1,"https://wallbox.ru/resize/1600x1200/wallpapers/main2/201732/1502363168598c3e20d53c28.02991705.jpg"),
    Conversation("ножками",1,"https://img5.goodfon.ru/original/1366x768/0/49/gora-sky-nebo-zakat.jpg"),
    Conversation("вооот",1,"https://img2.goodfon.ru/original/1920x1200/7/6a/list-zelen-kapli-rosa-5299.jpg"),
    Conversation("молодец",1,"http://wallpapers-image.ru/2560x1440/bridges/wallpapers/wallpapers-bridges-20.jpg"),
    Conversation("давай",2,""),
    Conversation("ток",1,"https://img5.goodfon.ru/original/1920x1080/7/1e/reka-kamni-les-gory-foto.jpg"),
    Conversation("давай шагаааааааааааааай!!!!",1,"https://mota.ru/upload/wallpapers/2017/07/15/13/24/53290/15001141805969ed047a9a01.66744297.jpg"),
)

class ConversationsAdapter: RecyclerView.Adapter<ViewHolder>() {
    private val items = LinkedList<ConversationItem>()

    init { transform(ss) }

    private fun transform(conversations: List<Conversation>){
        val list = mutableListOf<ConversationItem>()
        list.addAll(conversations.mapIndexed { i, it -> ConversationItem(it, i) })
        update(list)
    }

    private fun update(list: List<ConversationItem>) {
        val result = DiffUtil.calculateDiff(ConversationsDiffCallback(items, list))
        items.clear()
        items.addAll(list)
        result.dispatchUpdatesTo(this)
    }

    var pos: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemBinding.inflate(inflater, parent, false))
            .apply  {
                setItem(items[pos], pos)
                pos++
                binding.lifecycleOwner = this
            }
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.markAttach()
    }
    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.markDetach()
    }
    override fun getItemCount() = items.size
    //override fun getItemViewType(position: Int): Int =  items[position].type.value
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items[position]
        items[position].onBind(holder)
    }
}
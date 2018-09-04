package sasliderdemo.salmaan.ahmsal.com.flagchatadapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import java.util.*

/**
 * Created by salmaanahmed on 04/09/2018.
 */
class ChatAdapter(context: Context, private var list: ArrayList<Any>, clickListener: ((View, Int) -> Unit)? = null) : FlagChatAdapter(context, clickListener) {
    override val otherName: String get() = "John"

    override val listSize: Int get() = list.size

    override fun chatMessage(position: Int): String {
        return (list[position] as ChatModel).message!!
    }

    override fun messageTime(position: Int): String {
        return (list[position] as ChatModel).time!!
    }

    override fun isMe(position: Int): Boolean {
        return (list[position] as ChatModel).isMe!!
    }

    override fun animation(position: Int): Boolean {
        return (list[position] as ChatModel).animate!!
    }

    override fun setAnimationStatus(position: Int, animationStatus: Boolean) {
        (list[position] as ChatModel).animate = animationStatus
    }

    override fun isChatModel(position: Int): Boolean {
        return list[position] is ChatModel
    }

    override fun date(position: Int): String {
        val item = list[position] as Calendar
        return when {
            item.isToday() -> "Today"
            item.isYesterday() -> "Yesterday"
            else -> item.toDateLong()
        }
    }

    override fun colorOther(context: Context): Int {
        return ContextCompat.getColor(context, R.color.red)
    }

    override fun colorMe(context: Context): Int {
        return ContextCompat.getColor(context, R.color.cyan)
    }

    override fun showTime(position: Int): Boolean {
        return super.showTime(position)
    }

}
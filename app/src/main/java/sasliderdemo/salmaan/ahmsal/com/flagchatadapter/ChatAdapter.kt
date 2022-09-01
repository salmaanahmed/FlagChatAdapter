package sasliderdemo.salmaan.ahmsal.com.flagchatadapter

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.util.*

/**
 * Created by salmaanahmed on 04/09/2018.
 * Chat Adapter extended by FlagChatAdapter
 */
class ChatAdapter(context: Context, private var list: ArrayList<Any>) : FlagChatAdapter(context) {

    /**
     * Name of the person user is chatting with
     */
    override val otherName: String get() = "John"

    /**
     * Size of list i.e. number of messages
     */
    override val listSize: Int get() = list.size

    /**
     * Message on this index
     */
    override fun chatMessage(position: Int): String {
        return (list[position] as ChatModel).message
    }

    /**
     * Message time on this index
     */
    override fun messageTime(position: Int): String {
        return (list[position] as ChatModel).time
    }

    /**
     * Message sender on this index
     */
    override fun isMe(position: Int): Boolean {
        return (list[position] as ChatModel).isMe
    }

    /**
     * Animation on this index
     */
    override fun animation(position: Int): Boolean {
        return (list[position] as ChatModel).animate
    }

    /**
     * Set animation status on this index
     */
    override fun setAnimationStatus(position: Int, animationStatus: Boolean) {
        (list[position] as ChatModel).animate = animationStatus
    }

    /**
     * Object type on this index
     */
    override fun isChatModel(position: Int): Boolean {
        return list[position] is ChatModel
    }

    /**
     * Date string on this index
     */
    override fun date(position: Int): String {
        val item = list[position] as Calendar
        return when {
            item.isToday() -> "Today"
            item.isYesterday() -> "Yesterday"
            else -> item.toDateLong()
        }
    }

    /**
     * Bubble color of other person
     */
    override fun colorOther(context: Context): Int {
        return ContextCompat.getColor(context, R.color.red)
    }

    /**
     * Bubble color of user
     */
    override fun colorMe(context: Context): Int {
        return ContextCompat.getColor(context, R.color.cyan)
    }

    /**
     * Handle long click event
     */
    override fun onMessageLongClicked(position: Int) {
        Toast.makeText(context, "Long clicked on position $position", Toast.LENGTH_LONG).show()
    }

    /**
     * If you want to change show time logic, you can override this method.
     * I prefer the logic currently implemented in adapter
     */
    override fun showTime(position: Int): Boolean {
        return super.showTime(position)
    }

}
package sasliderdemo.salmaan.ahmsal.com.flagchatadapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.cell_chat.view.*
import kotlinx.android.synthetic.main.cell_chat_date.view.*

/**
 * Created by salmaanahmed on 04/09/2018.
 */
abstract class FlagChatAdapter(val context: Context, val clickListener: ((View, Int) -> Unit)? = null) : RecyclerView.Adapter<FlagChatAdapter.ViewHolder>() {

    private val chatView = 0
    private val dateView = 1

    abstract fun chatMessage(position: Int): String
    abstract fun messageTime(position: Int): String
    abstract fun isMe(position: Int): Boolean
    abstract fun animation(position: Int): Boolean
    abstract fun setAnimationStatus(position: Int, animationStatus: Boolean)
    abstract val otherName: String
    abstract val listSize: Int

    open fun colorMe(context: Context): Int {
        return ContextCompat.getColor(context, R.color.orange)
    }

    open fun colorOther(context: Context): Int {
        return ContextCompat.getColor(context, R.color.green)
    }

    open fun isChatModel(position: Int): Boolean {
        return true
    }

    open fun date(position: Int): String {
        return ""
    }

    open fun showTime(position: Int): Boolean {
        if (position > 0) {
            if (isChatModel(position) && isChatModel(position - 1) && isMe(position) && isMe(position - 1)) return false
        }
        return true
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (isChatModel(position)) {
            var chatMessage = chatMessage(position)
            var showTime = showTime(position)
            var messageTime = messageTime(position)
            var isMe = isMe(position)
            var animate = animation(position)
            holder?.bindData(chatMessage, showTime, messageTime, isMe, colorMe(context), colorOther(context), otherName)


            /// If flag is to be shown or not
            if (position > 0 && isChatModel(position - 1) && isMe == isMe(position - 1)) { //Hide Flag
                holder?.itemView!!.name.visibility = View.GONE
                holder?.itemView!!.flagPadding.visibility = View.GONE
            } else { // show Flag
                if (animate) {// If flag is not animated before
                    holder?.itemView!!.name.visibility = View.INVISIBLE
                    holder?.itemView!!.flagPadding.visibility = View.VISIBLE
                    flagAnimation(holder?.itemView!!, position)
                } else { // Do not animate flag if its animated before i.e. if user is scrolling up
                    holder?.itemView!!.name.visibility = View.VISIBLE
                    holder?.itemView!!.flagPadding.visibility = View.VISIBLE
                }
            }
        } else {
            holder?.itemView.date.text = date(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isChatModel(position)) chatView else dateView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == chatView) {
            val v = LayoutInflater.from(parent?.context).inflate(R.layout.cell_chat, parent, false)
            ViewHolder(v)
        } else {
            val v = LayoutInflater.from(parent?.context).inflate(R.layout.cell_chat_date, parent, false)
            ViewHolder(v)
        }
    }

    override fun getItemCount(): Int {
        return listSize
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(message: String, showTime: Boolean = true, time: String, isMe: Boolean, colorMe: Int, colorOther: Int, otherName: String) {
            setSides(isMe, colorMe, colorOther)
            setMessageType(message, showTime, time, isMe, otherName)
        }

        private fun setSides(isMe: Boolean, colorMe: Int, colorOther: Int) {
            if (isMe) {
                (itemView.line.layoutParams as RelativeLayout.LayoutParams).addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                (itemView.name.layoutParams as RelativeLayout.LayoutParams).addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                (itemView.message.layoutParams as LinearLayout.LayoutParams).gravity = Gravity.RIGHT
                (itemView.message.layoutParams as LinearLayout.LayoutParams).leftMargin = itemView.context.resources.getDimensionPixelSize(R.dimen.message_padding)
                (itemView.message.layoutParams as LinearLayout.LayoutParams).rightMargin = 0
                itemView.line.setBackgroundColor(colorMe)
                itemView.name.setBackgroundColor(colorMe)
                itemView.linearLayout.gravity = Gravity.RIGHT
                itemView.name.gravity = Gravity.RIGHT
                itemView.time.gravity = Gravity.RIGHT
            } else {
                (itemView.line.layoutParams as RelativeLayout.LayoutParams).addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0)
                (itemView.name.layoutParams as RelativeLayout.LayoutParams).addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0)
                (itemView.message.layoutParams as LinearLayout.LayoutParams).gravity = Gravity.LEFT
                (itemView.message.layoutParams as LinearLayout.LayoutParams).rightMargin = itemView.context.resources.getDimensionPixelSize(R.dimen.message_padding)
                (itemView.message.layoutParams as LinearLayout.LayoutParams).leftMargin = 0
                itemView.line.setBackgroundColor(colorOther)
                itemView.name.setBackgroundColor(colorOther)
                itemView.linearLayout.gravity = Gravity.LEFT
                itemView.name.gravity = Gravity.LEFT
                itemView.time.gravity = Gravity.LEFT
            }
        }

        private fun setMessageType(message: String, showTime: Boolean, time: String, isMe: Boolean, otherName: String) {
            itemView.message.text = message
            itemView.time.text = time
            itemView.name.text = if (isMe) "Me" else otherName
            if (showTime) itemView.time.visibility = View.VISIBLE
            else itemView.time.visibility = View.GONE
        }
    }

    private fun flagAnimation(itemView: View, position: Int) {
        val translateAnim = AnimationUtils.loadAnimation(itemView!!.context, R.anim.item_animation_from_bottom)
        translateAnim.fillAfter = true
        translateAnim.isFillEnabled = true
        translateAnim.fillBefore = false
        translateAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                itemView.linearLayout.alpha = 0f
                itemView.name.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation) {
                itemView.linearLayout.animate().alpha(1.0f).duration = 1000
//                var model = list[position]
//                if (model is ChatModel) model.animate = false
                setAnimationStatus(position, false)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        itemView.name.startAnimation(translateAnim)
    }


}
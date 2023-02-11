//Created on 04/09/2018
package sasliderdemo.salmaan.ahmsal.com.flagchatadapter

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * Extend your recycler view adapter with this adapter and voila!
 * You have your animated chat adapter working.
 * @author  Salmaan Ahmed
 * @since  1.0.1
 */
abstract class FlagChatAdapter(val context: Context) : RecyclerView.Adapter<FlagChatAdapter.ViewHolder>() {

    private val chatView = 0 //View types - Chat View
    private val dateView = 1 //View types - Date View

    /**
     * @return chat message on the position passed as parameter
     */
    abstract fun chatMessage(position: Int): String

    /**
     * @return time of message as string format on the position passed as parameter
     */
    abstract fun messageTime(position: Int): String

    /**
     * @return message sender on the position passed as parameter
     * if its you, return true
     */
    abstract fun isMe(position: Int): Boolean

    /**
     * you must have a variable of animation in the object i.e. if you want to animate or not
     */
    abstract fun animation(position: Int): Boolean

    /**
     * the animation variable must be set to false when animation is performed once
     * otherwise flags will animate on every scroll
     */
    abstract fun setAnimationStatus(position: Int, animationStatus: Boolean)

    /** You can implement whatever you want onLongClick event. */
    abstract fun onMessageLongClicked(position: Int)

    /** Name of the sender. */
    abstract val otherName: String

    /** You shall simply return list.size. */
    abstract val listSize: Int

    /** **OPTIONAL:** you can change flag color of your chat message. */
    open fun colorMe(context: Context): Int {
        return ContextCompat.getColor(context, R.color.orange)
    }

    /** **OPTIONAL:** you can change flag color of other person's chat message. */
    open fun colorOther(context: Context): Int {
        return ContextCompat.getColor(context, R.color.green)
    }

    /** **OPTIONAL:**
     * If your list contains some other data type from chat model i.e. date
     * you must return false in that case so adapter can check for date and display the date
     * chat message functions will not be called on the position if !isChatModel
     */
    open fun isChatModel(position: Int): Boolean {
        return true
    }

    /** **OPTIONAL:**
     * If !isChatModel adapter will display date place holder
     * it will be populated with the string returned by this function
     * you shall return the date or strings such as TODAY or YESTERDAY in this function
     */
    open fun date(position: Int): String {
        return ""
    }

    /** **OPTIONAL:**
     * This current code will not display time if same user sends message in same time
     * You may override this method to change the logic or simply return true if you always want to show time
     */
    open fun showTime(position: Int): Boolean {
        if (position > 0) {
            if (isChatModel(position) && isChatModel(position - 1)) {
                if (isMe(position) && isMe(position - 1) && messageTime(position) == messageTime(position - 1)) {
                    return false
                }
            }
        }
        return true
    }

    /**
     *  - Overriden function to bind view holder.
     *  - Decide to show the chat view or date view.
     *  - Populate data in views and show animation.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (isChatModel(position)) { // If it is chat model
            val chatMessage = chatMessage(position)
            val showTime = showTime(position)
            val messageTime = messageTime(position)
            val isMe = isMe(position)
            val animate = animation(position)

            // Bind data with viewholder
            holder.bindData(chatMessage, showTime, messageTime, isMe, colorMe(context), colorOther(context), otherName)


            // If previous message is from same person, hide the flag
            if (position > 0 && isChatModel(position - 1) && isMe == isMe(position - 1)) { //Hide Flag
                holder.apply {
                    name.visibility = View.GONE
                    flagPadding.visibility = View.GONE
                }
            } else { // Otherwise show flag
                if (animate) {// If flag is not animated before
                    holder.apply {
                        name.visibility = View.INVISIBLE
                        flagPadding.visibility = View.VISIBLE
                        flagAnimation(this, position)
                    }
                } else { // Do not animate flag if its animated before i.e. if user is scrolling list
                    holder.apply {
                        name.visibility = View.VISIBLE
                        flagPadding.visibility = View.VISIBLE
                    }
                }
            }
            holder.itemView.rootView.setOnLongClickListener {
                onMessageLongClicked(position)
                return@setOnLongClickListener true
            }
        } else {
            // If it is date, populate the textview
            holder.date.text = date(position)
        }
    }

    /** Item view type on basis of chat model. */
    override fun getItemViewType(position: Int): Int {
        return if (isChatModel(position)) chatView else dateView
    }

    /** Create view holder for both type of objects. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == chatView) {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.cell_chat, parent, false)
            ViewHolder(v)
        } else {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.cell_chat_date, parent, false)
            ViewHolder(v)
        }
    }

    /** Number of messages. */
    override fun getItemCount(): Int {
        return listSize
    }

    /**
     *  - ViewHolder to cache the cells to optimize performance
     *  - Populate the data, set colors, flags, animation etc.
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val line: FrameLayout by lazy { itemView.findViewById(R.id.line) }
        val name: TextView by lazy { itemView.findViewById(R.id.name) }
        val message: TextView by lazy { itemView.findViewById(R.id.message) }
        val time: TextView by lazy { itemView.findViewById(R.id.time) }
        val linearLayout: LinearLayout by lazy { itemView.findViewById(R.id.linearLayout) }
        val date: TextView by lazy { itemView.findViewById(R.id.date) }
        val flagPadding: View by lazy { itemView.findViewById(R.id.flagPadding) }

            fun bindData(message: String, showTime: Boolean = true, time: String, isMe: Boolean, colorMe: Int, colorOther: Int, otherName: String) {
            setSides(isMe, colorMe, colorOther)
            setMessage(message, showTime, time, isMe, otherName)
        }

        // Set sides, colors, and views according to sender
        private fun setSides(isMe: Boolean, colorMe: Int, colorOther: Int) {
            if (isMe) {

                line.apply {
                    (layoutParams as RelativeLayout.LayoutParams).addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                    setBackgroundColor(colorMe)
                }

                name.apply {
                    (layoutParams as RelativeLayout.LayoutParams).addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                    setBackgroundColor(colorMe)
                    gravity = Gravity.RIGHT
                }

                message.apply {
                    (layoutParams as LinearLayout.LayoutParams).apply {
                        gravity = Gravity.RIGHT
                        leftMargin = itemView.context.resources.getDimensionPixelSize(R.dimen.message_padding)
                        rightMargin = 0
                    }
                }

                Gravity.RIGHT.let {
                    linearLayout.gravity = it
                    time.gravity = it
                }

            } else {
                line.apply {
                    (layoutParams as RelativeLayout.LayoutParams).addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0)
                    setBackgroundColor(colorOther)
                }

                name.apply {
                    (layoutParams as RelativeLayout.LayoutParams).addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0)
                    setBackgroundColor(colorOther)
                    gravity = Gravity.LEFT
                }

                message.apply {
                    (layoutParams as LinearLayout.LayoutParams).apply {
                        gravity = Gravity.LEFT
                        rightMargin = itemView.context.resources.getDimensionPixelSize(R.dimen.message_padding)
                        leftMargin = 0
                    }
                }

                Gravity.LEFT.let {
                    linearLayout.gravity = it
                    time.gravity = it
                }

            }
        }

        // Populate message, date, sender in the views
        private fun setMessage(message: String, showTime: Boolean, time: String, isMe: Boolean, otherName: String) {
            this@ViewHolder.let {
                it.message.text = message
                it.time.text = time
                it.name.text = if (isMe) "Me" else otherName
                if (showTime) it.time.visibility = View.VISIBLE
                else it.time.visibility = View.GONE
            }
        }
    }

    /** Flag animation on the views. */
    private fun flagAnimation(itemView: ViewHolder, position: Int) {
        val translateAnim = AnimationUtils.loadAnimation(context, R.anim.item_animation_from_bottom)
        translateAnim.apply {
            fillAfter = true
            isFillEnabled = true
            fillBefore = false
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    itemView.apply {
                        linearLayout.alpha = 0f
                        name.visibility = View.VISIBLE
                    }
                }

                override fun onAnimationEnd(animation: Animation) {
                    itemView.linearLayout.animate().alpha(1.0f).duration = 1000
                    setAnimationStatus(position, false)
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
        }
        itemView.name.startAnimation(translateAnim)
    }


}
package sasliderdemo.salmaan.ahmsal.com.flagchatadapter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var isMeRandom = false

    private var chatMessages: ArrayList<Any> = ArrayList()
    private var chatAdapter: ChatAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        chatAdapter = ChatAdapter(this@MainActivity, chatMessages)
        recyclerView.adapter = chatAdapter

        tvMessage.afterTextChanged {
            btnSend.visibility = if (tvMessage.text.trim().isNotEmpty()) View.VISIBLE else View.INVISIBLE
        }

        btnSend.setOnClickListener {
            isMeRandom = !isMeRandom
            sendMessage(ChatModel(tvMessage.text.trim().toString(), Calendar.getInstance().toHHmma(), isMeRandom, true))
            tvMessage.text = null
        }

        loadDummyData()
    }

    private fun sendMessage(chatMessage: Any) {
        chatMessages.add(chatMessage)
        chatAdapter!!.notifyDataSetChanged()
        chatAdapter!!.notifyItemInserted(chatMessages.size - 1)
        Handler().postDelayed({ recyclerView.scrollToPosition(chatMessages.size - 1) }, 100)
    }

    private fun loadDummyData() {
        val yesterday: Calendar = Calendar.getInstance()
        yesterday.add(Calendar.DATE, -1)
        sendMessage(yesterday)
        sendMessage(ChatModel("Hello", "07:34 pm", false, false))
        sendMessage(ChatModel("Hi", "07:35 pm", true, false))
        sendMessage(ChatModel("Are you coming for dinner?", "07:35 pm", false, false))
        sendMessage(ChatModel("Yep, I am on my way :)", "07:40 pm", true, false))
        sendMessage(Calendar.getInstance())
    }

//    private fun addChatObjectToList(chat: ChatModel) {
//        if (chatMessages.isEmpty()) {
//            chatMessages.add(chat.time)
//        } else if (chatMessages.last() is ChatModel) {
//            if (!(chatMessages.last() as ChatModel).time.toCalender()!!.isSameDay(chat.time.toCalender()!!)) {
//                chatMessages.add(chat.time)
//            } else {
//                // Check date
//                if (chat.isMe == (chatMessages.last() as ChatModel).isMe && chat.time.toCalender()!!.isSameMinute(((chatMessages.last() as ChatModel).time).toCalender()!!)) {
//                    (chatMessages.last() as ChatModel).showTime = false
//                    chatAdapter!!.notifyItemChanged(chatMessages.size - 1)
//                }
//            }
//        }
//
//        chatMessages.add(chat)
//        chatAdapter!!.notifyDataSetChanged()
//        chatAdapter!!.notifyItemInserted(chatMessages.size - 1)
//        Handler().postDelayed({ recyclerView.scrollToPosition(chatMessages.size - 1) }, 100)
//    }
}

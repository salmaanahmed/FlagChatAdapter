package sasliderdemo.salmaan.ahmsal.com.flagchatadapter

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import java.util.*

/**
 * Main Activity for chat screen
 */
class MainActivity : AppCompatActivity() {

    private var isMeRandom = false // IsMe flag random

    private var chatMessages: ArrayList<Any> = ArrayList()  // Chat Array
    private var chatAdapter: ChatAdapter? = null            // Chat Adapter

    /**
     * Bind adapter with recycler view
     * Add view listeners
     * Load dummy data
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create and assign adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        chatAdapter = ChatAdapter(this@MainActivity, chatMessages)
        recyclerView.adapter = chatAdapter

        tvMessage.afterTextChanged {
            btnSend.visibility = if (tvMessage.text.trim().isNotEmpty()) View.VISIBLE else View.INVISIBLE
        }

        // Change Sender
        // Send Message
        // Clear edit text
        btnSend.setOnClickListener {
            isMeRandom = !isMeRandom
            sendMessage(ChatModel(tvMessage.text.trim().toString(), Calendar.getInstance().toHHmma(), isMeRandom, true))
            tvMessage.text = null
        }

        // Load dummy data
        loadDummyData()
    }

    /**
     * Add chat message, notify data set and scroll to bottom
     */
    private fun sendMessage(chatMessage: Any) {
        chatMessages.add(chatMessage)
        chatAdapter!!.notifyItemInserted(chatMessages.size - 1)
        Handler().postDelayed({ recyclerView.scrollToPosition(chatMessages.size - 1) }, 100)
    }

    /**
     * Load dummy data initially
     */
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
}

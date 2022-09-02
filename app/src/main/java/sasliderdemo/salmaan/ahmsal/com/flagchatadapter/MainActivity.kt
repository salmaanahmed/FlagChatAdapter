package sasliderdemo.salmaan.ahmsal.com.flagchatadapter

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * Main Activity for chat screen
 */
class MainActivity : AppCompatActivity() {

    private var isMeRandom = false // IsMe flag random

    private var chatMessages: ArrayList<Any> = ArrayList()  // Chat Array
    private var chatAdapter: ChatAdapter? = null            // Chat Adapter

    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    private val tvMessage by lazy { findViewById<EditText>(R.id.tvMessage) }
    private val btnSend by lazy { findViewById<ImageView>(R.id.btnSend) }


    /**
     * Bind adapter with recycler view
     * Add view listeners
     * Load dummy data
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create and assign adapter
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
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
        Handler(Looper.getMainLooper()).postDelayed({ recyclerView.scrollToPosition(chatMessages.size - 1) }, 100)
    }

    /**
     * Load dummy data initially
     */
    private fun loadDummyData() {
        val yesterday: Calendar = Calendar.getInstance()
        yesterday.add(Calendar.DATE, -1)
        sendMessage(yesterday)
        sendMessage(ChatModel("Hello", "07:34 pm", isMe = false, false))
        sendMessage(ChatModel("Hi", "07:35 pm", isMe = true, false))
        sendMessage(ChatModel("Are you coming for dinner?", "07:35 pm", isMe = false, false))
        sendMessage(ChatModel("Yep, I am on my way :)", "07:40 pm", isMe = true, false))
        sendMessage(Calendar.getInstance())
    }
}

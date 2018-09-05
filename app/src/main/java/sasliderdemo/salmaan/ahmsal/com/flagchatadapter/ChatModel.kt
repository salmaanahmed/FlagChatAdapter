package sasliderdemo.salmaan.ahmsal.com.flagchatadapter

/**
 * Created by salmaanahmed on 04/09/2018.
 * Data class for chat model
 */
data class ChatModel (
    var message: String = "",
    var time: String = "",
    var isMe: Boolean = false,
    var animate: Boolean = true
)
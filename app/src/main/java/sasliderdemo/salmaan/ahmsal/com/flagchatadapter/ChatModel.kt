//Created on 04/09/2018
package sasliderdemo.salmaan.ahmsal.com.flagchatadapter

/**
 * Data class for chat model.
 * @author  Salmaan Ahmed
 * @since  1.0.1
 */
data class ChatModel (
    var message: String = "",
    var time: String = "",
    var isMe: Boolean = false,
    var animate: Boolean = true
)
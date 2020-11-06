package weeidl.com.ui.fragment.single_chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.message_item.view.*
import weeidl.com.R
import weeidl.com.models.CommonModel
import weeidl.com.utilits.CURRENT_UID
import weeidl.com.utilits.asTime

class SingleChatAdapter: RecyclerView.Adapter<SingleChatAdapter.SinngleChatHolder>() {

    private var mlistMessagesCache = emptyList<CommonModel>()


    class SinngleChatHolder(view: View): RecyclerView.ViewHolder(view){
        val blockUserMessage:ConstraintLayout = view.bloc_user_message
        val chatUserMessage:TextView = view.chat_user_message
        val chatUserMessageTime:TextView = view.chat_user_time

        val blockReceivedMessage:ConstraintLayout = view.bloc_received_message
        val chatReceivedMessage:TextView = view.chat_received_message
        val chatReceivedMessageTime:TextView = view.chat_received_time

}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SinngleChatHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        return SinngleChatHolder(view)

    }

    override fun getItemCount(): Int = mlistMessagesCache.size

    override fun onBindViewHolder(holder: SinngleChatHolder, position: Int) {
        if(mlistMessagesCache[position].from == CURRENT_UID){
            holder.blockUserMessage.visibility = View.VISIBLE
            holder.blockReceivedMessage.visibility = View.GONE
            holder.chatUserMessage.text = mlistMessagesCache[position].text
            holder.chatUserMessageTime.text = mlistMessagesCache[position].timeStamp.toString().asTime()
        }else {
            holder.blockUserMessage.visibility = View.GONE
            holder.blockReceivedMessage.visibility = View.VISIBLE
            holder.chatReceivedMessage.text = mlistMessagesCache[position].text
            holder.chatReceivedMessageTime.text = mlistMessagesCache[position].timeStamp.toString().asTime()
        }

    }

    fun setList(list: List<CommonModel>){
        mlistMessagesCache = list
        notifyDataSetChanged()
    }

}



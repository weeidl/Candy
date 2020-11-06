package weeidl.com.ui.fragment.single_chat

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_single_chat.*
import kotlinx.android.synthetic.main.toolbar_info.view.*
import weeidl.com.R
import weeidl.com.models.CommonModel
import weeidl.com.models.User
import weeidl.com.ui.fragment.BaseFragment
import weeidl.com.utilits.*


class SingleChatFragment(private var contact: CommonModel) : BaseFragment(R.layout.fragment_single_chat) {

    private lateinit var mListenerInfoToolbar : AppValueEventListener
    private lateinit var mRecevingUser : User
    private lateinit var mToolvatInfo : View
    private lateinit var mRefUser : DatabaseReference
    private lateinit var mRefMessage : DatabaseReference
    private lateinit var mAdapter: SingleChatAdapter
    private lateinit var mRecycleView: RecyclerView
    private lateinit var mMessagesListener : AppValueEventListener
    private var mListMessages = emptyList<CommonModel>()



    override fun onResume() {
        super.onResume()
        initToolBar()
        initRecycleView()
    }

    private fun initRecycleView() {
        mRecycleView = chat_recycle_view
        mAdapter = SingleChatAdapter()
        mRefMessage = REF_DATABASE_ROOT.child(NODE_MESSAGES)
            .child(CURRENT_UID)
            .child(contact.id)
        mRecycleView.adapter = mAdapter
        mMessagesListener = AppValueEventListener { dataSnapshot ->
            mListMessages = dataSnapshot.children.map { it.getCommonModel() }
            mAdapter.setList(mListMessages)
            mRecycleView.smoothScrollToPosition(mAdapter.itemCount)
        }
        mRefMessage.addValueEventListener(mMessagesListener)
    }

    private fun initToolBar() {
        mToolvatInfo = APP_ACTIVITY.mToolbar.toolbar_info
        mToolvatInfo.visibility = View.VISIBLE
        mListenerInfoToolbar = AppValueEventListener {
            mRecevingUser = it.getUserModel()
            initInfoToolbar()
        }
        mRefUser = REF_DATABASE_ROOT.child(NODE_USERS).child(contact.id)
        mRefUser.addValueEventListener(mListenerInfoToolbar)
        chat_btn_send_message.setOnClickListener {
            val message = chatinputmessage.text.toString()
            if (message.isEmpty()) {
                showToast("Введите сообщение")
            } else sendMessage(message, contact.id, TYPE_TEXT) {
                chatinputmessage.setText("")
            }
        }
    }


    private fun initInfoToolbar() {
        if (mRecevingUser.fullname.isEmpty()){
            mToolvatInfo.toolbar_contact_chat_fullname.text = contact.fullname
        }else mToolvatInfo.toolbar_contact_chat_fullname.text = mRecevingUser.fullname
        mToolvatInfo.toolbar_chat_image.downloadAndSetImage(mRecevingUser.photoUrl)

        mToolvatInfo.toolbar_contact_chat_status.text = mRecevingUser.state


    }

    override fun onPause() {
        super.onPause()
        APP_ACTIVITY.mToolbar.toolbar_info.visibility = View.GONE
        mRefUser.removeEventListener(mListenerInfoToolbar)
        mRefMessage.removeEventListener(mMessagesListener)
    }
}
package weeidl.com.ui.fragment

import android.view.View
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.toolbar_info.view.*
import weeidl.com.R
import weeidl.com.models.CommonModel
import weeidl.com.models.User
import weeidl.com.utilits.*


class SingleChatFragment(private var contact: CommonModel) :BaseFragment(R.layout.fragment_single_chat) {

    private lateinit var mListenerInfoToolbar : AppValueEventListener
    private lateinit var mRecevingUser : User
    private lateinit var mToolvatInfo : View
    private lateinit var mRefUser : DatabaseReference


    override fun onResume() {
        super.onResume()
        mToolvatInfo = APP_ACTIVITY.mToolbar.toolbar_info
        mToolvatInfo.visibility = View.VISIBLE
        mListenerInfoToolbar = AppValueEventListener {
            mRecevingUser = it.getUserModel()
            initInfoToolbar()
        }
        mRefUser = REF_DATABASE_ROOT.child(NODE_USERS).child(contact.id)
        mRefUser.addValueEventListener(mListenerInfoToolbar)
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
    }
}
package weeidl.com.ui.fragment

import android.view.View
import kotlinx.android.synthetic.main.activity_main.view.*
import weeidl.com.R
import weeidl.com.models.CommonModel
import weeidl.com.utilits.APP_ACTIVITY


class SingleChatFragment(contact: CommonModel) :BaseFragment(R.layout.fragment_single_chat) {

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.mToolbar.toolbar_info.visibility = View.VISIBLE
    }

    override fun onPause() {
        super.onPause()
        APP_ACTIVITY.mToolbar.toolbar_info.visibility = View.GONE
    }
}
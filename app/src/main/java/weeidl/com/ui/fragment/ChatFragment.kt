package weeidl.com.ui.fragment

import weeidl.com.R
import weeidl.com.utilits.APP_ACTIVITY

class ChatFragment : BaseFragment(R.layout.fragment_chat) {

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "CANDY"

    }

}
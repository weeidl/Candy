package weeidl.com.ui.fragment

import weeidl.com.R
import weeidl.com.utilits.APP_ACTIVITY

class ContactsFragment : BaseFragment(R.layout.fragment_contacts) {
    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Контакты"
    }
}

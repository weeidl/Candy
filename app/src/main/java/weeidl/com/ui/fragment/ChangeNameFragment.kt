package weeidl.com.ui.fragment

import kotlinx.android.synthetic.main.fragment_change_name.*
import weeidl.com.R
import weeidl.com.utilits.*

class ChangeNameFragment : BaseChangeFragment(R.layout.fragment_change_name) {
    override fun onResume() {
        super.onResume()
        initFullnameList()
    }

    private fun initFullnameList() {
        val fullnameList = USER.fullname.split(" ")
        if (fullnameList.size > 1) {
            settings_imput_name.setText(fullnameList[0])
            settings_imput_surname.setText(fullnameList[1])
        } else settings_imput_name.setText(fullnameList[0])
    }

    override fun change() {
        val name = settings_imput_name.text.toString()
        val surname = settings_imput_surname.text.toString()
        if (name.isEmpty()){
            showToast(getString(R.string.settings_toast_name_is_empty))
        } else {
            val fullname = "$name $surname"
            REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_FULLNAME)
                .setValue(fullname).addOnCompleteListener {
                    if (it.isSuccessful){
                        showToast(getString(R.string.toast_data_update))
                        USER.fullname = fullname
                        APP_ACTIVITY.mAppDrawer.updateHeader()
                        fragmentManager?.popBackStack()
                    }
                }
        }
    }
}
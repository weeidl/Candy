package weeidl.com.ui.fragment

import androidx.fragment.app.Fragment
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_enter_code_ftagment.*
import weeidl.com.MainActivity
import weeidl.com.R
import weeidl.com.activities.RegisterActivity
import weeidl.com.utilits.*

class EnterCodeFtagment(val mPhoneNomber: String, val id: String?) :
    Fragment(R.layout.fragment_enter_code_ftagment) {


    override fun onStart() {
        super.onStart()
        (activity as RegisterActivity).title = mPhoneNomber
        register_input_code.addTextChangedListener(AppTextWatcher {

            val string = register_input_code.text.toString()
            if (string.length == 6) {
                enterCode()
            }


        })
    }

    private fun enterCode() {
        val code = register_input_code.text.toString()
        //------------------------------------------------------------------------------
        val credential = id?.let { PhoneAuthProvider.getCredential(it, code) }
        //------------------------------------------------------------------------------
        AUTH.signInWithCredential(credential!!).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val uid = AUTH.currentUser?.uid.toString()
                val dateMap = mutableMapOf<String, Any>()
                dateMap[CHILD_ID] = uid
                dateMap[CHILD_PHONE] = mPhoneNomber
                dateMap[CHILD_USERNAME] = uid

                REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dateMap)
                    .addOnCompleteListener { task2 ->
                        if (task2.isSuccessful) {
                            showToast("Добро пожаловать")
                            (activity as RegisterActivity).replaceActivity(MainActivity())
                        } else showToast(task2.exception?.message.toString())
                    }


            } else showToast(task.exception?.message.toString())
        }
    }
}
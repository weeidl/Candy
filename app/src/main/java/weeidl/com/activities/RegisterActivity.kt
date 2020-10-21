package weeidl.com.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import weeidl.com.R
import weeidl.com.databinding.ActivityRegisterBinding
import weeidl.com.ui.fragment.EnterPhoneNomperFragment
import weeidl.com.utilits.initFirebase
import weeidl.com.utilits.replaceFragment

class RegisterActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityRegisterBinding
    private lateinit var mToolbar : androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initFirebase()
    }

    override fun onStart() {
        super.onStart()
        mToolbar = mBinding.registerToolbar
        setSupportActionBar(mToolbar)
        title = getString(R.string.register_title_your_phone)

        replaceFragment(EnterPhoneNomperFragment())
    }
}
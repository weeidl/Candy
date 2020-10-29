package weeidl.com.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.contact_item.view.*
import kotlinx.android.synthetic.main.fragment_contacts.*
import weeidl.com.R
import weeidl.com.models.CommonModel
import weeidl.com.utilits.*

class ContactsFragment : BaseFragment(R.layout.fragment_contacts) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter:FirebaseRecyclerAdapter<CommonModel, ContactHolder>
    private lateinit var mRefContacts:DatabaseReference

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Контакты"
        initRecycleView()
    }

    private fun initRecycleView() {
        mRecyclerView = contacts_recycle_view
        mRefContacts = REF_DATABASE_ROOT.child(NODE_PHONES_CONTACTS).child(CURRENT_UID)

        val options = FirebaseRecyclerOptions.Builder<CommonModel>()
            .setQuery(mRefContacts, CommonModel::class.java)
            .build()

        mAdapter = object:FirebaseRecyclerAdapter<CommonModel, ContactHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
                return ContactHolder(view)
            }

            override fun onBindViewHolder(
                holder: ContactHolder,
                position: Int,
                model: CommonModel
            ) {
                holder.name.text = model.fullname
                holder.status.text = model.state
                holder.photo.downloadAndSetImage(model.photoUrl)

            }

        }
        mRecyclerView.adapter = mAdapter
        mAdapter.startListening()
    }
    class ContactHolder(view: View):RecyclerView.ViewHolder(view){
        val name:TextView = view.contact_fullname
        val status:TextView = view.contacts_status
        val photo:CircleImageView = view.contact_photo
    }

    override fun onPause() {
        super.onPause()
        mAdapter.startListening()
    }
}


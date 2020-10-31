package weeidl.com.ui.`object`

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.drawerlayout.widget.DrawerLayout
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerImageLoader
import weeidl.com.R
import weeidl.com.ui.fragment.ChatFragment
import weeidl.com.ui.fragment.ContactsFragment
import weeidl.com.ui.fragment.SettingsFragment
import weeidl.com.utilits.APP_ACTIVITY
import weeidl.com.utilits.USER
import weeidl.com.utilits.downloadAndSetImage
import weeidl.com.utilits.replaceFragment

class AppDrawer{

    private lateinit var mDrawer: Drawer
    private lateinit var mHeader: AccountHeader
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mCurrentProfile:ProfileDrawerItem

    fun create() {
        /* Создания бокового меню */
        initLoader()
        createHeader()
        createDrawer()
        mDrawerLayout = mDrawer.drawerLayout
    }

    private fun createDrawer() {
        mDrawer = DrawerBuilder()
            .withActivity(APP_ACTIVITY)
            .withToolbar(APP_ACTIVITY.mToolbar)
            .withActionBarDrawerToggle(true)
            .withSelectedItem(-1)
            .withAccountHeader(mHeader)
            .addDrawerItems(
                PrimaryDrawerItem().withIdentifier(100)
                    .withIconTintingEnabled(true)
                    .withName("Сообщения")
                    .withSelectable(true)
                    .withIcon(R.drawable.icon_chat),

                PrimaryDrawerItem().withIdentifier(101)
                    .withIconTintingEnabled(true)
                    .withName("Истории")
                    .withSelectable(true)
                    .withIcon(R.drawable.icon_histori_2),

                PrimaryDrawerItem().withIdentifier(102)
                    .withIconTintingEnabled(true)
                    .withName("Музыка")
                    .withSelectable(true)
                    .withIcon(R.drawable.icon_music),

                PrimaryDrawerItem().withIdentifier(103)
                    .withIconTintingEnabled(true)
                    .withName("Архив")
                    .withSelectable(true)
                    .withIcon(R.drawable.ic_menu_secret_chat),

                DividerDrawerItem(),

                PrimaryDrawerItem().withIdentifier(104)
                    .withIconTintingEnabled(true)
                    .withName("Контакты")
                    .withSelectable(true)
                    .withIcon(R.drawable.ic_menu_contacts),

                PrimaryDrawerItem().withIdentifier(105)
                    .withIconTintingEnabled(true)
                    .withName("Настройки")
                    .withSelectable(true)
                    .withIcon(R.drawable.ic_menu_settings),


                DividerDrawerItem(),

                PrimaryDrawerItem().withIdentifier(106)
                    .withIconTintingEnabled(true)
                    .withName("Пригласить друзей")
                    .withSelectable(true)
                    .withIcon(R.drawable.ic_menu_invate),

                PrimaryDrawerItem().withIdentifier(107)
                    .withIconTintingEnabled(true)
                    .withName("Помощь")
                    .withSelectable(true)
                    .withIcon(R.drawable.ic_menu_help)


            ).withOnDrawerItemClickListener(object :Drawer.OnDrawerItemClickListener{
                override fun onItemClick(
                    view: View?,
                    position: Int,
                    drawerItem: IDrawerItem<*>
                ): Boolean {
                    clickToItem(position)
                    return false
                }
            }).build()
    }

    private fun clickToItem(position:Int){
        when(position){
            1 -> APP_ACTIVITY.replaceFragment(ChatFragment())

            7 -> APP_ACTIVITY.replaceFragment(SettingsFragment())

            6 -> APP_ACTIVITY.replaceFragment(ContactsFragment())
        }
    }

    private fun createHeader() {
        /* Создание хедера*/
        mCurrentProfile = ProfileDrawerItem()
            .withName(USER.fullname)
            .withEmail(USER.phone)
            .withIcon(USER.photoUrl)
            .withIdentifier(200)
        mHeader = AccountHeaderBuilder()
            .withActivity(APP_ACTIVITY)
            .withHeaderBackground(R.drawable.toolbar_bar)
            .addProfiles(
                mCurrentProfile
            ).build()
    }

    fun updateHeader(){
        /* Обновления хедера */
        mCurrentProfile
            .withName(USER.fullname)
            .withEmail(USER.phone)
            .withIcon(USER.photoUrl)

        mHeader.updateProfile(mCurrentProfile)

    }

    private fun initLoader(){
        /* Инициализация лоадера для загрузки картинок в хедер */
        DrawerImageLoader.init(object :AbstractDrawerImageLoader(){
            override fun set(imageView: ImageView, uri: Uri, placeholder: Drawable) {
                imageView.downloadAndSetImage(uri.toString())
            }
        })
    }
}
package weeidl.com.utilits

import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import weeidl.com.R
import weeidl.com.models.CommonModel
import java.text.SimpleDateFormat
import java.util.*

fun showToast(message:String){
    /* Функция показывает сообщение */
    Toast.makeText(APP_ACTIVITY,message,Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.replaceActivity(activity: AppCompatActivity){
    /* Функция расширения для AppCompatActivity, позволяет запускать активити */
    val intent = Intent(this, activity::class.java)
    startActivity(intent)
    this.finish()
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, addStack:Boolean = true){
    /* Функция расширения для AppCompatActivity, позволяет устанавливать фрагменты */
    if (addStack){
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.data_Container,
                fragment
            ).commit()
    } else {
        supportFragmentManager.beginTransaction()
            .replace(R.id.data_Container,
                fragment
            ).commit()
    }

}

fun Fragment.replaceFragment(fragment: Fragment){
    /* Функция расширения для Fragment, позволяет устанавливать фрагменты */
    this.fragmentManager?.beginTransaction()
        ?.addToBackStack(null)
        ?.replace(R.id.data_Container,
            fragment
        )?.commit()
}

fun hideKeyboard() {
    /* Функция скрывает клавиатуру */
    val imm: InputMethodManager = APP_ACTIVITY.getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager
    imm.hideSoftInputFromWindow(APP_ACTIVITY.window.decorView.windowToken, 0)
}

fun ImageView.downloadAndSetImage(url:String){
    /* Функция раширения ImageView, скачивает и устанавливает картинку*/
    Picasso.get()
        .load(url)
        .fit()
        .placeholder(R.drawable.defouid_photo)
        .into(this)
}

fun initContacts() {
    /* Функция считывает контакты с телефонной книги, хаполняет массив arrayContacts моделями CommonModel */
    if (chtckPermissions(READ_CONTACTS)) {
        var arrayContacts = arrayListOf<CommonModel>()
        val cursor = APP_ACTIVITY.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        cursor?.let {
            while (it.moveToNext()) {
                /* Читаем телефонную книгу пока есть следующие элементы */
                val fullName =
                    it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phone =
                    it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val newModel = CommonModel()
                newModel.fullname = fullName
                newModel.phone = phone.replace(Regex("[\\s,-]"), " ")
                arrayContacts.add(newModel)
            }
        }
        cursor?.close()
        updatePhonesToDatabase(arrayContacts)
    }
}

fun String.asTime(): String {
    val time = Date(this.toLong())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return timeFormat.format(time)

}
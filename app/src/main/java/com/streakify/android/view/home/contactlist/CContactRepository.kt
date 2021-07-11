package com.streakify.android.view.home.contactlist

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import com.streakify.android.utils.ExceptionLoggerUtil
import com.streakify.android.utils.Logger
import com.streakify.android.utils.Util
import com.streakify.android.view.home.contactlist.data.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CContactRepository @Inject constructor(private val context: Context) : ContactRepository {

    override suspend fun getContacts(): List<Contact> {
        return withContext(Dispatchers.IO) {
            if (!Permissions(context).isContactPermissionGiven()) {
                return@withContext emptyList<Contact>()
            }

            val contacts = ArrayList<Contact>()
            var contactsCursor: Cursor? = null
            try {
                try {
                    contactsCursor = context.contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        null,
                        null,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
                    )
                } catch (e: SecurityException) {
                    Logger.log(e.message.toString())
//                    ExceptionLoggerUtil.logException(e)
                }

                val mobileContactMap: HashMap<String, Boolean> = HashMap()
                var i = 0
                while (contactsCursor != null && contactsCursor.moveToNext()) {
                    val name =
                        contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val mobileNumber =
                        contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    val mobile = Util.purifyMobileNumber(mobileNumber)
                    val photo =
                        contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI))
                    if (!Util.isBlank(name) && !Util.isBlank(mobileNumber)) {
//                    if (!Util.isBlank(name) && !Util.isBlank(mobile)) {
                        if (mobileContactMap[mobile] == null) {
                            contacts.add(Contact(name, mobileNumber, photo, mobile))
                            mobileContactMap[mobile] = true
                            i++
                        }
                    }
                }
            } catch (e: Exception) {
                ExceptionLoggerUtil.logException(e)
            } finally {
                contactsCursor?.close()
            }
            return@withContext contacts
        }
    }
}
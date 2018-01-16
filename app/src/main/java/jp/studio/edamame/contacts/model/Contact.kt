package jp.studio.edamame.contacts.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import io.reactivex.Single
import jp.studio.edamame.contacts.provider.ContactsProvider


/**
 * Created by Watanabe@Neopa on 2017/05/17.
 */
class Contact(var id: Long, var displayName: String) {
    val phoneList: MutableList<Phone> = mutableListOf()
    val emailAddressList: MutableList<MailAddress> = mutableListOf()

    private var photo: Bitmap? = null

    fun getPhoto() : Single<Bitmap> {
        return Single.create { emmitter ->
            this.photo?.let {
                emmitter.onSuccess(it)
                return@create
            }

            ContactsProvider.openPhoto(this.id)?.let {
                val photo = BitmapFactory.decodeStream(it)
                this.photo = photo
                emmitter.onSuccess(photo)

                return@create
            }

            emmitter.onError(Throwable("photo is null"))
        }
    }
}
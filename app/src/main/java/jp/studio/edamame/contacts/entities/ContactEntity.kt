package jp.studio.edamame.contacts.entities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import io.reactivex.Maybe
import jp.studio.edamame.contacts.provider.ContactsProvider


/**
 * Created by Watanabe@Neopa on 2017/05/17.
 */
class ContactEntity(var id: Long, var displayName: String, var sortKey : String, var photoUri: String = "") {
    val phoneEntityList: MutableList<PhoneEntity> = mutableListOf()
    val mailEntityList: MutableList<MailEntity> = mutableListOf()

    private var photo: Bitmap? = null

    fun getPhoto() : Maybe<Bitmap> {
        return Maybe.create { emitter ->
            this.photo?.let {
                emitter.onSuccess(it)
                return@create
            }

            ContactsProvider.openPhoto(this.id)?.let {
                val photo = BitmapFactory.decodeStream(it)
                this.photo = photo
                emitter.onSuccess(photo)

                return@create
            }

            emitter.onComplete()
        }
    }
}
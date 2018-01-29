package jp.studio.edamame.contacts.views.all

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import io.reactivex.Maybe
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import jp.studio.edamame.contacts.models.Contact
import jp.studio.edamame.contacts.provider.ContactsProvider

/**
 * Created by Watanabe on 2018/01/23.
 */
class ContactThumbnailViewModel(private var rxcontact: Contact) : ContactsRecyclerItemable {
    override var itemType: ContactsViewItemType = ContactsViewItemType.CONTACTS

    val disposable: CompositeDisposable = CompositeDisposable()

    var rx_id: BehaviorSubject<Long> = rxcontact.rx_id
    var rx_displayName: BehaviorSubject<String> = rxcontact.rx_displayName
    var hasPhoneNumber: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    var hasMail: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    var photoUri: BehaviorSubject<String> = rxcontact.rx_photoUri

    init {
        disposable.add(rxcontact.rx_phoneList.subscribe { list ->
            hasPhoneNumber.onNext(list.count() > 0)
        })

        disposable.add(rxcontact.rx_mailList.subscribe { list ->
            hasMail.onNext(list.count() > 0)
        })
    }

    fun dispose() {
        disposable.dispose()
    }

    fun getPhoto() : Maybe<Bitmap> {
        return Maybe.create { emitter ->
            ContactsProvider.openPhoto(this.rx_id.value)?.let {
                val photo = BitmapFactory.decodeStream(it)
                emitter.onSuccess(photo)

                return@create
            }

            emitter.onComplete()
        }
    }
}
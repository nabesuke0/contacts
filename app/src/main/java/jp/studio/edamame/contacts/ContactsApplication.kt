package jp.studio.edamame.contacts

import android.app.Application
import io.reactivex.subjects.BehaviorSubject
import jp.studio.edamame.contacts.models.ContactModel
import jp.studio.edamame.contacts.models.ContactScreenModel
import jp.studio.edamame.contacts.provider.ContactsProvider
import timber.log.Timber

/**
 * Created by Watanabe on 2017/05/12.
 */
class ContactsApplication : Application() {
//    val contactScreenModel: ContactScreenModel by lazy {
//        ContactScreenModel()
//    }
    lateinit var contactScreenModel: ContactScreenModel

    companion object {
        private var self : ContactsApplication? = null
        fun getApp() : ContactsApplication {
            return self!!
        }
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            // Log出力
            Timber.plant(Timber.DebugTree())
        }

        ContactsApplication.self = this

        this.contactScreenModel = ContactScreenModel()
    }
}
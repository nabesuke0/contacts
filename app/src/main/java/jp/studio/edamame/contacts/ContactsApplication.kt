package jp.studio.edamame.contacts

import android.app.Application
import jp.studio.edamame.contacts.models.ContactsModel
import timber.log.Timber

/**
 * Created by Watanabe on 2017/05/12.
 */
class ContactsApplication : Application() {
    companion object {
        private var self : ContactsApplication? = null
        fun getApp() : ContactsApplication {
            return self!!
        }
    }


    val contactsModel: ContactsModel by lazy {
        ContactsModel()
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            // Log出力
            Timber.plant(Timber.DebugTree())
        }

        ContactsApplication.self = this

        this.contactsModel
    }
}
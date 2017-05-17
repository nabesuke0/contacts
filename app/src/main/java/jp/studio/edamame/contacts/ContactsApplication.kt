package jp.studio.edamame.contacts

import android.app.Application

/**
 * Created by Watanabe@Neopa on 2017/05/12.
 */
class ContactsApplication : Application() {
    companion object {
        private var self : ContactsApplication? = null
        fun getApp() : ContactsApplication {
            return self!!
        }
    }

    override fun onCreate() {
        super.onCreate()

        ContactsApplication.self = this
    }
}
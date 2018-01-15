package jp.studio.edamame.contacts.views.all

import jp.studio.edamame.contacts.provider.ContactsProvider

/**
 * Created by Watanabe on 2017/05/16.
 */
class AllContactsViewModel {
    init {
        // TODO: 取得テスト用
        ContactsProvider.queryAll()
    }
}
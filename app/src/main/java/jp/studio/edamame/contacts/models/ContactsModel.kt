package jp.studio.edamame.contacts.models

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import jp.studio.edamame.contacts.extensions.katakanaToHiragana
import jp.studio.edamame.contacts.provider.ContactsProvider

/**
 * Created by Watanabe on 2018/01/23.
 */
class ContactsModel {
    private var isEnableAccessContacts: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    private val disposable: CompositeDisposable = CompositeDisposable()

    val contactModels: BehaviorSubject<MutableList<Contact>> = BehaviorSubject.create()

    companion object {
        fun distributeKey(key : String) : Pair<Int, String> {
            return when {
                key.length > 1 -> Pair(99, "#") // 2文字以上は不正
                key.matches(Regex("[0-9a-zA-Z]+")) -> Pair(10, key) // アルファベット
                key.matches(Regex("^[\\u3040-\\u309F]+$")) -> Pair(0, distributeKanaRow(key)) // ひらがな
                key.matches(Regex("^[\\\\u30A0-\\\\u30FF]+\$")) ->
                    Pair(0, distributeKanaRow(key.katakanaToHiragana())) // カタカナはひらがなに変換して返す
                else -> Pair(99, "#") // 読み仮名が漢字などその他のものは全て#で返す
            }
        }

        fun phoneLabelBySortKey(sortKey: String): String {
            val keyChar = sortKey.substring(0..0)
            return when {
                keyChar.matches(Regex("[0-9a-zA-Z]+")) -> keyChar
                keyChar.matches(Regex("^[\\u3040-\\u309F]+$")) -> distributeKanaRow(keyChar)
                keyChar.matches(Regex("^[\\\\u30A0-\\\\u30FF]+\$")) -> distributeKanaRow(keyChar.katakanaToHiragana())
                else -> "#"
            }
        }

        /**
         * @see <https://en.wikipedia.org/wiki/Hiragana_%28Unicode_block%29>
         */
        private fun distributeKanaRow(kana: String): String {
            return when {
                kana.length > 1  -> throw Exception(Throwable("One character restriction"))
                kana.matches(Regex("^[\\u3040-\\u304A]+$")) -> "あ"
                kana.matches(Regex("^[\\u304B-\\u3054]+$")) -> "か"
                kana.matches(Regex("^[\\u3055-\\u305E]+$")) -> "さ"
                kana.matches(Regex("^[\\u305F-\\u3069]+$")) -> "た"
                kana.matches(Regex("^[\\u306A-\\u306E]+$")) -> "な"
                kana.matches(Regex("^[\\u306F-\\u307D]+$")) -> "は"
                kana.matches(Regex("^[\\u307E-\\u3082]+$")) -> "ま"
                kana.matches(Regex("^[\\u3083-\\u3088]+$")) -> "や"
                kana.matches(Regex("^[\\u3089-\\u308D]+$")) -> "ら"
                kana.matches(Regex("^[\\u308E-\\u3093]+$")) -> "わ"
                else -> "#"
            }
        }

        fun sortContacts(rxcontactList: MutableList<Contact>): MutableMap<String, MutableList<Contact>> {
            val mutableMap = mutableMapOf<Int, MutableMap<String, MutableList<Contact>>>()
            rxcontactList.forEach { contactModel ->
                var sortKey = contactModel.rx_sortKey.value.substring(0..0)
                val categoryPair = ContactsModel.distributeKey(sortKey)

                val sortOrderPrimary = categoryPair.first
                val categoryKey = categoryPair.second

                if (mutableMap[sortOrderPrimary] == null) {
                    mutableMap[sortOrderPrimary] = mutableMapOf()
                }

                if (!mutableMap[sortOrderPrimary]!!.containsKey(categoryKey)) {
                    mutableMap[sortOrderPrimary]!!.put(categoryKey, mutableListOf())
                }

                mutableMap[sortOrderPrimary]!![categoryKey]!!.add(contactModel)
            }

            val sortedMap = mutableMapOf<String, MutableList<Contact>>()
            mutableMap.toSortedMap().values.forEach {
                it.toSortedMap().forEach { (k, v) ->
                    sortedMap.put(k, v)
                }
            }

            return sortedMap
        }
    }

    init {
        disposable.add(isEnableAccessContacts.filter { it }.subscribe {
            this.updateContacts()
        })
    }

    fun accessContactsState(isEnable: Boolean) {
        isEnableAccessContacts.onNext(isEnable)
    }

    fun updateContacts() {
        ContactsProvider.rx_queryAll()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { contacts ->
                    contactModels.onNext(
                            contacts.map {
                                Contact(it)
                            }.toMutableList()
                    )
                }
    }
}
package jp.studio.edamame.contacts.entity

/**
 * Created by Watanabe@Neopa on 2017/05/18.
 */
enum class ContactItemType(val type: Int) {
    Unknown(0),
    Mobile(1),
    Home(2),
    Work(3),
    Other(99);

    override fun toString(): String {
        when (this) {
            Unknown -> return "Unknown"
            Mobile -> return "Mobile"
            Home -> return "Home"
            Work -> return "Work"
            Other -> return "Other"
        }
    }

    companion object {
        fun convertEmailType(type: Int) {
            
        }
    }
}
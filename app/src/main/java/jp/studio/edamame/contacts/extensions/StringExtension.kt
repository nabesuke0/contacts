package jp.studio.edamame.contacts.extensions

/**
 * Created by Watanabe on 2018/01/18.
 */
fun String.katakanaToHiragana() : String {
    val buf = StringBuffer()

    this.forEach { code ->
        if (code.toInt() in 0x30a1..0x30f3) {
            buf.append((code.toInt() - 0x60).toChar())
        } else {
            buf.append(code)
        }
    }

    return buf.toString()
}
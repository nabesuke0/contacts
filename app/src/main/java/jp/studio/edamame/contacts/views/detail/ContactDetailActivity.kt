package jp.studio.edamame.contacts.views.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import jp.studio.edamame.contacts.R
import kotlinx.android.synthetic.main.activity_contact_detail.*

class ContactDetailActivity : AppCompatActivity() {

    companion object {
        val INTENT_KEY_CONTACT_ID = "INTENT_KEY_CONTACT_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)

        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val contactId = intent.getStringExtra(INTENT_KEY_CONTACT_ID)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            if (it.itemId == android.R.id.home) {
                this.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

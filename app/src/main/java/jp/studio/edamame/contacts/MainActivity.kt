package jp.studio.edamame.contacts

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.tbruyelle.rxpermissions2.RxPermissions
import jp.studio.edamame.contacts.views.all.AllContactsFragment
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var rxPermission : RxPermissions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.rxPermission = RxPermissions(this)
    }

    override fun onResume() {
        super.onResume()

        rxPermission.request(
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS)
                .subscribe(
                        { granted ->
                            if (granted) {
                                this.initializeTabFragments()
                            } else {
                                this.showConfirmationDialog()
                            }
                        },
                        {
                            Timber.e(it)
                        }
                )
    }

    private fun initializeTabFragments() {
        val pagerAdapter = ContactsPagerAdapter(supportFragmentManager)
        pagerAdapter.addFragment(AllContactsFragment())

        main_viewpager.adapter = pagerAdapter
        main_tab.setupWithViewPager(main_viewpager)
    }

    private fun showConfirmationDialog() {
        AlertDialog.Builder(this)
                .setMessage(R.string.permission_request_message)
                .setPositiveButton(android.R.string.ok) {dialog, which ->
                    this.finish()
                }
    }
}

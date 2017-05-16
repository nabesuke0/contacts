package jp.studio.edamame.contacts

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.OnPageChangeListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pagerAdapter = ContactsPagerAdapter(supportFragmentManager)
        main_viewpager.adapter = pagerAdapter
        main_tab.setupWithViewPager(main_viewpager)
    }
}

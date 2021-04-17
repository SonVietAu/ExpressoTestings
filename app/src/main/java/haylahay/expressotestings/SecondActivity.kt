package haylahay.expressotestings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.TextureView
import android.widget.TextView
import haylahay.expressotestings.ui.main.ForthFragment
import haylahay.expressotestings.ui.main.MainFragment
import haylahay.expressotestings.ui.main.SecondFragment

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SecondFragment())
                    .commitNow()
        }
    }

}
package haylahay.expressotestings

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import haylahay.expressotestings.ui.main.SecondFragment

// TODO: Move menuItem works into SecondFragment and remove this ActionBarTestActivity

class ActionBarTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SecondFragment())
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.firstMItem,
            R.id.secondMItem,
            R.id.thirdMItem,
            -> {
                findViewById<TextView>(R.id.menuResultTV).text = item.title
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
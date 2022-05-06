package co.ivanovpv.githubdata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.ivanovpv.githubdata.ui.main.MyAlertDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}
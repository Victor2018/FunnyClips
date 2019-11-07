package com.victor.clips

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.victor.clips.module.SchemaModule

class SchemaProxyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schema_proxy)

        val temp = intent.data
        dispatchScheme(temp)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        dispatchScheme(intent?.data)
    }

    private fun dispatchScheme(data: Uri?) {
        SchemaModule.dispatchSchema(this, data)
        finish()
    }
}

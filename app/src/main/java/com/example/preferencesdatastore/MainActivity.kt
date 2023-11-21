package com.example.preferencesdatastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.core.preferencesSetKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var button1 : Button
    private lateinit var button2 : Button
    private lateinit var textkey : TextInputEditText
    private lateinit var textvalue : TextInputEditText
    private lateinit var textread : TextInputEditText
    private lateinit var textstring : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1 = findViewById(R.id.btnSave)
        button2 = findViewById(R.id.btnRead)
        textkey = findViewById(R.id.etSaveKey)
        textvalue = findViewById(R.id.etSaveValue)
        textread = findViewById(R.id.etReadkey)
        textstring = findViewById(R.id.tvReadValue)

        button1.setOnClickListener{
            lifecycleScope.launch {
                save(
                    textkey.text.toString(),
                    textvalue.text.toString()
                )
            }
        }

        button2.setOnClickListener{
            lifecycleScope.launch {
                val value =  read(textread.text.toString())
                textstring.text = value ?: "No value found"
            }
        }



        dataStore = createDataStore(name = "Storage ")


    }

    suspend fun save (key : String, value: String){
        dataStore.edit { preferences ->
            preferences[preferencesKey(key)] = value
        }

    }

    suspend fun read(key:String) : String? {
        val dataStoreKey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]

    }
}
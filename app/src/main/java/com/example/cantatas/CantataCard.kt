package com.example.cantatas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.RatingBar

class CantataCard : AppCompatActivity() {
    lateinit var mCantataWebView: WebView
    lateinit var mCantataRating: RatingBar
    lateinit var mCantata: Cantata

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cantata_card)

        mCantataWebView = findViewById(R.id.cantata_web)
        mCantataRating = findViewById(R.id.rating)

        mCantata = intent.getParcelableExtra<Cantata>(MainActivity.CANTATA) as Cantata
        mCantataRating.rating = mCantata.rating

// Чтобы данные из интернета отображались не в стороннем браузере:
        mCantataWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return false
            }
        }
        mCantataWebView.loadUrl(mCantata.url1)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.card, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_back) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        // Формируем Intent, который будет возвращен в вызвавшую нас Activity
        val intent = Intent()
        mCantata.rating = mCantataRating.rating
        intent.putExtra(MainActivity.CANTATA, mCantata)

        setResult(RESULT_OK, intent)

        // Вызываем onBackPressed суперкласса, закрывая Activity
        super.onBackPressed()
    }
}
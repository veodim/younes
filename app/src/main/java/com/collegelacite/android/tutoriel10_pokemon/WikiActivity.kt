package com.collegelacite.android.tutoriel10_pokemon

import android.os.Bundle
import android.view.Menu
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.collegelacite.android.tutoriel10_pokemon.databinding.ActivityWikiBinding

class WikiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWikiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWikiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra(EXTRA_WIKI_URL)
        val nom = intent.getStringExtra(EXTRA_NOM)

        if (!nom.isNullOrBlank()) {
            title = nom
        }

        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = WebViewClient()
        if (!url.isNullOrBlank()) {
            binding.webView.loadUrl(url)
        }

        // Bouton Back : naviguer dans le WebView avant de quitter l'activité
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.webView.canGoBack()) {
                    binding.webView.goBack()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    companion object {
        const val EXTRA_WIKI_URL = "wiki_url"
        const val EXTRA_NOM = "nom_pokemon"
    }
}

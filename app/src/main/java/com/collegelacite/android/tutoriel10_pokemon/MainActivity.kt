package com.collegelacite.android.tutoriel10_pokemon

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.collegelacite.android.tutoriel10_pokemon.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var listeDePokemons: ArrayList<Pokemon>
    private lateinit var adaptateur: AdaptateurDePokemon

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Source des données
        listeDePokemons = Pokemon.lireDonnées(this)
        // Lier l'adaptateur à la source des données
        adaptateur = AdaptateurDePokemon(this, listeDePokemons)
        // Lier l'adaptateur au ListView
        binding.listViewId.adapter = adaptateur

        // Listener : Toast + intention explicite vers WikiActivity
        binding.listViewId.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val pokemon = listeDePokemons[position]
                Toast.makeText(this, pokemon.nomFrançais, Toast.LENGTH_SHORT).show()

                val intent = Intent(this, WikiActivity::class.java)
                intent.putExtra(WikiActivity.EXTRA_WIKI_URL, pokemon.wikiUrl)
                intent.putExtra(WikiActivity.EXTRA_NOM, pokemon.nomFrançais)
                startActivity(intent)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}

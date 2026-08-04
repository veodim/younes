package com.collegelacite.android.tutoriel10_pokemon

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView


//le constructeur de classe avec 2 parametres: contexte et la source des données
class AdaptateurDePokemon
    (private val context: Activity, private val pokemons: ArrayList<Pokemon>):
    ArrayAdapter<Pokemon>(context, R.layout.item_list_pokemon, pokemons) {
    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.item_list_pokemon, null, true)
        //definir un objet pour la recuperation dynamique des données
        var pokemon = pokemons[position]
        //récupérer les images depuis drawable pour les fournir aux itemVew de ma liste

        var image = rowView.findViewById<ImageView>(R.id.imageView) as ImageView
        pokemon.intoImageView(image)
        //récupérer les noms
        var nom = rowView.findViewById <TextView>(R.id.nomTextView) as TextView
        nom.text =pokemon.nomFrançais
        //récupérer les types
        var type = rowView.findViewById <TextView>(R.id.typeTextView) as TextView
       type.text = pokemon.type
        //récupérer la description
      var desc = rowView.findViewById<TextView>(R.id.description) as TextView
       desc.text = pokemon.description

        return rowView
    }
}
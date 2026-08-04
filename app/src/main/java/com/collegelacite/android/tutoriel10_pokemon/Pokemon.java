package com.collegelacite.android.tutoriel10_pokemon;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;

// Classe représentant un personnage de la série Pokémon.
public class Pokemon implements Comparable, Serializable {
    private int numéro;          // numéro du pokemon
    private String nomFrançais;  // nom du Pokemon en français
    private String nomAnglais;   // nom du Pokemon en anglais
    private String type;         // type du Pokemon
    private String description;  // brève description du pokémon
    private int impact;          // "hit points"
    private int attaque;         // force de l'attaque
    private int défense;         // résistance en défence
    private int vitesse;         // facteur de vitesse de déplacement
    private int spécial;         // attribut spécial (signification variante)
    private String wikiUrl;      // url vers la page Wikipédia du pokemon
    private String drawable;     // image du pokemon dans les ressources

    private static Context contexte = null;   // requis pour lire le JSON

    // Constructeur par défaut.
    public Pokemon() {
        this.setNuméro(0);
        this.setNomFrançais(null);
        this.setNomAnglais(null);
        this.setType(null);
        this.setWikiUrl(null);
        this.setdDrawable(null);
    }

    // Fonction de comparaison utilisée pour trier la liste de Pokemon après la
    // lecture du JSON.
    @Override
    public int compareTo(Object autrePokemon) {
        // Le collator permet de comparer des chaînes de caractères en ignorant
        // les accents (p.ex. "Éric" et "Eric" sont identiques).
        Collator c = Collator.getInstance();
        c.setStrength(Collator.PRIMARY);

        String s1 = this.getNomFrançais();
        String s2 = ((Pokemon)autrePokemon).getNomFrançais();

        return c.compare(s1, s2);
    }

    // Accesseur de l'attribut numéro.
    public int getNuméro() {
        return numéro;
    }

    // Mutateur de l'attribut numéro.
    public void setNuméro(int numéro) {
        this.numéro = numéro;
    }

    // Accesseur de l'attribut nomFrançais.
    public String getNomFrançais() {
        return nomFrançais;
    }

    // Mutateur de l'attribut nomFrançais.
    public void setNomFrançais(String nomFrançais) {
        this.nomFrançais = nomFrançais;
    }

    // Accesseur de l'attribut nomAnglais.
    public String getNomAnglais() {
        return nomAnglais;
    }

    // Mutateur de l'attribut nomAnglais.
    public void setNomAnglais(String nomAnglais) {
        this.nomAnglais = nomAnglais;
    }

    // Accesseur de l'attribut type.
    public String getType() {
        return type;
    }

    // Mutateur de l'attribut type.
    public void setType(String type) {
        this.type = type;
    }

    // Accesseur de l'attribut description.
    public String getDescription() {
        return description;
    }

    // Mutateur de l'attribut description.
    public void setDescription(String description) {
        this.description = description;
    }

    // Mutateur de l'attribut wikiUrl.
    public void setWikiUrl(String wikiUrl) {
        this.wikiUrl = wikiUrl;
    }

    // Accesseur de l'attribut impact.
    public int getImpact() {
        return impact;
    }

    // Mutateur de l'attribut impact.
    public void setImpact(int impact) {
        this.impact = impact;
    }

    // Accesseur de l'attribut attaque.
    public int getAttaque() {
        return attaque;
    }

    // Mutateur de l'attribut attaque.
    public void setAttaque(int attaque) {
        this.attaque = attaque;
    }

    // Accesseur de l'attribut défense.
    public int getDéfense() {
        return défense;
    }

    // Mutateur de l'attribut défense.
    public void setDéfense(int défense) {
        this.défense = défense;
    }

    // Accesseur de l'attribut vitesse.
    public int getVitesse() {
        return vitesse;
    }

    // Mutateur de l'attribut vitesse.
    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }

    // Accesseur de l'attribut spécial.
    public int getSpécial() {
        return spécial;
    }

    // Mutateur de l'attribut spécial.
    public void setSpécial(int spécial) {
        this.spécial = spécial;
    }

    // Accesseur de l'attribut drawable.
    public String getDrawable() {
        return drawable;
    }

    // Mutateur de l'attribut drawable.
    public void setdDrawable(String drawable) {
        this.drawable = drawable;
    }

    // Accesseur de l'attribut wikiUrl.
    public String getWikiUrl() {
        return wikiUrl;
    }

    // Retourne une chaîne décrivant le Pokemon.
    @Override
    public String toString() {
        return this.getNomFrançais();
    }

    // Fonction permettant d'insérer l'image du pokémon dans un
    // ImageView fourni. L'image du pokémon doit être dans res/drawable.
    public void intoImageView(ImageView iv) {
        String uri = "@drawable/" + this.getDrawable().toLowerCase();

        int imageResource = contexte.getResources().getIdentifier(uri, null, contexte.getPackageName());

        Drawable res = contexte.getDrawable(imageResource);
        iv.setImageDrawable(res);
    }

    // Désérialiser une liste de Pokemon d'un fichier JSON.
    public static ArrayList<Pokemon> lireDonnées(Context ctx) {
        final String nomFichier = "pokemon.json";

        // Sauvegarder le contexte pour la gestion des drawables.
        contexte = ctx;

        ArrayList<Pokemon> liste = new ArrayList<>();
        try {
            // Charger les données dans la liste.
            String jsonString = lireJson(nomFichier);
            JSONObject json   = new JSONObject(jsonString);
            JSONArray Pokemon = json.getJSONArray("pokemons");

            // Lire chaque Pokemon du fichier.
            for(int i = 0; i < Pokemon.length(); i++){
                Pokemon p = new Pokemon();

                p.numéro = Pokemon.getJSONObject(i).getInt("numero");

                p.nomFrançais = Pokemon.getJSONObject(i).getString("nom_francais");
                p.nomAnglais = Pokemon.getJSONObject(i).getString("nom_anglais");

                String type1 = Pokemon.getJSONObject(i).getString("type_1");
                String type2 = Pokemon.getJSONObject(i).getString("type_2");
                p.type = type1;
                if (type2.length() > 0)
                    p.type += " / " + type2;

                p.impact  = Pokemon.getJSONObject(i).getInt("impact");
                p.attaque = Pokemon.getJSONObject(i).getInt("attaque");
                p.défense = Pokemon.getJSONObject(i).getInt("defense");
                p.vitesse = Pokemon.getJSONObject(i).getInt("vitesse");
                p.spécial = Pokemon.getJSONObject(i).getInt("special");

                p.wikiUrl  = Pokemon.getJSONObject(i).getString("wiki_url");
                p.drawable = Pokemon.getJSONObject(i).getString("drawable");

                p.description = Pokemon.getJSONObject(i).getString("description");

                liste.add(p);
            }
        } catch (JSONException e) {
            // Une erreur s'est produite (on la journalise).
            e.printStackTrace();
            return null;
        }

        // Trier la liste.
        Collections.sort(liste);

        return liste;
    }

    // Retourne une balise lue d'un fichier JSON.
    private static String lireJson(String nomFichier) {
        String json = null;

        try {
            InputStream is = contexte.getAssets().open(nomFichier);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            // Une erreur s'est produite (on la journalise).
            ex.printStackTrace();
            return null;
        }

        return json;
    }
}

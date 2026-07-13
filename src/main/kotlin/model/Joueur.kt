package model

import iut.info1.flip7.IJoueur
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class Joueur(
    val nom: String,
    var nb1erePlace: Int = 0,
    var nbParties: Int = 0
) : IJoueur {

    override fun donneNom(): String {
        return nom
    }

    fun ajouterUnePartie(){
        nbParties++
    }

    fun ajouterUne1erePlace(){
        nb1erePlace++
    }

    override fun toString(): String {
        return "${ this.donneNom() } | ${this.nbParties} | ${this.nb1erePlace}"
    }
}

class OutilsDataJoueurs(fichierSauvegarde : String = "src/main/resources/sauvegardeJoueur/dataJoueurs.json") {

    private val fichierSauvegarde = File(fichierSauvegarde)

    fun lireJson() : List<Joueur>{
        if (!fichierSauvegarde.exists()) {
            println("Le fichier n'existe pas.")
            return emptyList()
        }

        val listeJoueurs = Json.decodeFromString<List<Joueur>>(fichierSauvegarde.readText())

        return listeJoueurs
    }

    fun ecrireJson(infoJoueurs: List<Joueur>) {
        val jsonFormat = Json { prettyPrint = true }

        val texteJson = jsonFormat.encodeToString(infoJoueurs)

        fichierSauvegarde.writeText(texteJson)
    }

}

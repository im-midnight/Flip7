package model

import iut.info1.flip7.Flip7
import iut.info1.flip7.IJoueur
import iut.info1.flip7.cartes.*
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList

class Flip7Model {

    val joueursSauvegardes: ObservableList<Joueur>
    val joueursSelectionnes: ObservableList<Joueur>

    var scoreMax = SimpleIntegerProperty(100)
    var tour : Int = 0
    var mancheActuelle: Int = 0
    val scoreManches: MutableMap<Int, List<Int>> = mutableMapOf()

    val themes: Array<String>
    var theme: SimpleStringProperty

    lateinit var flip7 : Flip7 private set

    val ODJ = OutilsDataJoueurs()

    init {

        this.joueursSauvegardes = FXCollections.observableArrayList(
            ODJ.lireJson()
        )

        this.joueursSelectionnes= FXCollections.observableArrayList()

        this.themes = arrayOf("flip7", "excalibur")
        this.theme = SimpleStringProperty(themes[0])
    }

    var pioche = listOf(CarteNum(0), CarteBonusMultiplie()) + (1..12).flatMap {num -> List(num) { CarteNum(num) }} +
            (1..5).map { CarteBonusPlus(it*2) } + List(3) { Carte2ndeChance() } +
            List(3) { Carte3aLaSuite() } + List(3) { CarteStop() }
        private set


    fun initialisationFlip7() {
        try {
            flip7 = Flip7(
                joueursSelectionnes.size,
                joueursSelectionnes,
                pioche,
                scoreFinPartie = scoreMax.get())
            this.scoreManches.clear()
            this.mancheActuelle = 0

        }catch (e: Exception){
            println(e)
        }

    }

//    val joueursSauvegardes: MutableList<Joueur> = preremplirJoueurPourTester(2).toMutableList()
//
//    fun ajouterJoueurALaBase(joueur: Joueur) {
//        joueursSauvegardes.add(joueur)
//    }


    fun verifierContientJoueur(joueur: Joueur): Boolean {
        this.joueursSauvegardes.forEach { if (it.donneNom() == joueur.donneNom()) return true }
        this.joueursSelectionnes.forEach { if (it.donneNom() == joueur.donneNom()) return true }
        return false
    }

    fun getJoueurSauvegarde(joueur: Joueur?): Joueur? =
        joueursSauvegardes.firstOrNull { it.donneNom() == joueur?.donneNom() }

    fun getJoueurSelectionne(joueur: Joueur?): Joueur? =
        joueursSelectionnes.firstOrNull { it.donneNom() == joueur?.donneNom() }

    fun sauvegarderListeJoueursActuel() {
        ODJ.ecrireJson(joueursSauvegardes+joueursSelectionnes)
    }

    fun updateDataJoueurFinJeu(){
        flip7.joueurs.forEachIndexed { i, joueur ->
            joueur as Joueur
            joueur.ajouterUnePartie()
            if (flip7.score[i] == flip7.score.values.max()) joueur.ajouterUne1erePlace()
        }

        sauvegarderListeJoueursActuel()
    }
    fun calculeScoreMancheActuelle(): List<Int> {
        val outil = OutilsCarte()
        val main = flip7.main
        var scoreJoueurs= mutableListOf<Int>()
        for( i in 0 until main.size){
            if (main[i] == null){
                scoreJoueurs.add(0)
            }
            else{
                scoreJoueurs.add(outil.calculScore(main[i]!!))
            }
        }
        return scoreJoueurs
    }

    fun sauvegarderManche() {
        mancheActuelle++
        scoreManches[mancheActuelle] = calculeScoreMancheActuelle()
    }

    fun getClassement(): Pair<List<IJoueur>, MutableList<Int>> {
        val joueurs = flip7.joueurs.toMutableList()
        val points = mutableListOf<Int>()

        for (i in 0 until joueurs.size) {
            points.add(flip7.score[i] ?: 0)
        }

        for (i in 0 until points.size - 1) {
            for (j in 0 until points.size - i - 1) {
                if (points[j] < points[j + 1]) {
                    var tempScore = points[j]
                    points[j] = points[j + 1]
                    points[j + 1] = tempScore

                    var tempJoueur = joueurs[j]
                    joueurs[j] = joueurs[j + 1]
                    joueurs[j + 1] = tempJoueur
                }
            }
        }

        return Pair(joueurs, points)
    }

}
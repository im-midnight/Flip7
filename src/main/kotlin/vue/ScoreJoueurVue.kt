package vue

import iut.info1.flip7.IJoueur
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.HBox

class ScoreJoueurVue(joueurs : List<IJoueur>) : HBox(15.0) { // 15.0 pour un espacement vertical agréable

    var colorIndex = 0

    var colors = listOf<String>("red","orange", "green", "blue")

    val nomJoueurs = joueurs.map { joueur ->
        val nomJoueur = Label(joueur.donneNom())
        nomJoueur.style = "-fx-text-fill: ${colors[colorIndex]}; -fx-font-weight: bold; -fx-font-size: 18px;"
        colorIndex++
        nomJoueur }

    init {
        this.children += nomJoueurs
        this.alignment = Pos.CENTER

    }
}
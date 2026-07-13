package vue

import iut.info1.flip7.IJoueur
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.VBox


class ScoreMancheVboxVue(joueurs: List<IJoueur>, titre: String, scoreMancheJoueur : List<Int>) : VBox() {
    var joueurs: List<IJoueur> = joueurs
    var colorIndex = 0
    val colors = listOf<String>("red","orange", "green", "blue")

    val labelManche = Label(titre)
    val barreNoir = Label("───────")

    init {
        this.setPrefSize(250.0, 300.0)
        this.alignment = Pos.CENTER
        this.styleClass.add("box-solid")
        this.spacing = 5.0

        labelManche.styleClass.add("titreManche")
        this.children.addAll(labelManche, barreNoir)

        this.alignment = Pos.CENTER
        this.scaleX = 0.9
        this.scaleY = 0.9

        updateScoreMancheVboxVue(scoreMancheJoueur)
    }

    fun updateScoreMancheVboxVue(scoreMancheJoueur : List<Int>){
        this.children.subList(2, this.children.size).clear()
        colorIndex = 0
        for (i in 0 until this.joueurs.size) {
            val score = scoreMancheJoueur[i]

            val labelScore = Label(score.toString())
            labelScore.styleClass.add("fontSizeNormal")
            labelScore.alignment = Pos.CENTER

            val color = colors[colorIndex % colors.size]
            labelScore.styleClass.add(color)
            colorIndex++

            this.children.add(labelScore)

            if (joueurs[i] != joueurs.last()) {
                this.children.add(Label("- - - - - - - - "))
            }
        }
    }
}
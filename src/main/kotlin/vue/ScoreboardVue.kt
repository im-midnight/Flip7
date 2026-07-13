package vue



import iut.info1.flip7.IJoueur
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.chart.NumberAxis
import javafx.scene.control.Button
import javafx.scene.control.ContentDisplay.CENTER
import javafx.scene.control.ScrollPane
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import java.io.FileInputStream

class ScoreboardVue(joueurs : List<IJoueur>,scoresTotal: Map<Int, Int>): VBox() {

    val borderPaneTop = BorderPane()
    val borderPaneBottom = BorderPane()
    val hboxNomJoueur = ScoreJoueurVue(joueurs)

    val scrollPane = ScrollPane()

    val hboxManche = HBox()
    val scoreTotal = scoresTotal.values.toList()
    val vboxTotal = ScoreMancheVboxVue(joueurs, "Total",scoreTotal)

    val boutonProchaineManche = Button()


    val xAxis = NumberAxis()
    val yAxis = NumberAxis()

    val lineChart = LineChartVue(xAxis, yAxis)

    init {
        // NOM JOUEUR
        hboxNomJoueur.alignment = Pos.BOTTOM_CENTER
        hboxNomJoueur.spacing = 30.0
        borderPaneTop.top = hboxNomJoueur


        // Tous les Manches
        scrollPane.vbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        scrollPane.hvalue = scrollPane.hmax
        scrollPane.content = hboxManche
        scrollPane.style = "-fx-background-color: white;"
        borderPaneTop.center = scrollPane


        // colonne droite

        borderPaneTop.right = vboxTotal
        borderPaneTop.padding = Insets(20.0)

        // Button
        val inputButton = FileInputStream("assets/button.png")
        val imgButton = Image(inputButton)
        val viewMenu = ImageView(imgButton)
        viewMenu.fitHeight = 250.0
        viewMenu.fitWidth = 220.0
        viewMenu.isPreserveRatio = true

        boutonProchaineManche.graphic = viewMenu
        boutonProchaineManche.contentDisplay = CENTER
        boutonProchaineManche.style = "-fx-background-color: transparent;"
        boutonProchaineManche.styleClass.add("grand-bouton")
        boutonProchaineManche.prefWidth = 300.0

        boutonProchaineManche.setOnMouseEntered{
            boutonProchaineManche.scaleX = 1.1
            boutonProchaineManche.scaleY = 1.1
        }
        boutonProchaineManche.setOnMouseExited{
            boutonProchaineManche.scaleX = 1.0
            boutonProchaineManche.scaleY = 1.0
        }


        boutonProchaineManche.graphic = viewMenu
        boutonProchaineManche.contentDisplay = CENTER
        boutonProchaineManche.style = "-fx-background-color: transparent; -fx-text-fill: #1971c2;"
        boutonProchaineManche.styleClass.add("grand-bouton")
        boutonProchaineManche.styleClass.add("change-font-lilitaone")

        borderPaneBottom.right = boutonProchaineManche
        borderPaneBottom.padding = Insets(20.0)

        // Graphic

        borderPaneBottom.center = lineChart

        //La Vue
        this.children.addAll(borderPaneTop,borderPaneBottom)


        BorderPane.setMargin(hboxNomJoueur, Insets(0.0, 20.0, 0.0, 0.0))
        BorderPane.setMargin(scrollPane, Insets(0.0, 20.0, 0.0, 50.0))
        BorderPane.setMargin(vboxTotal, Insets(0.0, 0.0, 0.0, 20.0))
    }


    fun fixeControleurBouton(bouton: Button, action: EventHandler<ActionEvent>) {
        bouton.onAction = action
    }

    fun updateScoreBoardVue(joueurs : List<IJoueur>, scoreManches: Map<Int, List<Int>>, scoresTotal: Map<Int, Int>, manche : Int, scoreMax : Int) {

        val nouveauScoreJoueurMax = scoresTotal.values.max()

        hboxManche.children.clear()
        for ((numManche, scores) in scoreManches) {
            val mancheVue = ScoreMancheVboxVue(joueurs, "Manche $numManche", scores)
            hboxManche.children += mancheVue
        }

        val scoreMancheJoueur = scoresTotal.values.toList()
        vboxTotal.updateScoreMancheVboxVue(scoreMancheJoueur)

        lineChart.updateLineChartVue(scoreManches, joueurs, manche, nouveauScoreJoueurMax)

        if (nouveauScoreJoueurMax >= scoreMax) {
            boutonProchaineManche.text = "Fin Partie"
        } else {
            boutonProchaineManche.text = "Prochain \n Manche"
        }
    }

}
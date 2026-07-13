package vue



import iut.info1.flip7.IJoueur
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.geometry.VPos.BOTTOM
import javafx.scene.control.Button
import javafx.scene.control.ContentDisplay.CENTER
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.BorderPane
import javafx.scene.layout.FlowPane
import javafx.scene.layout.VBox

import java.io.FileInputStream

class PodiumVue(classement : Pair<List<IJoueur>, MutableList<Int>>): BorderPane() {


    val nomJoueurs = classement.first
    val points = classement.second

    val borderPaneTop = BorderPane()
    val buttonMenu = Button("Menu")
    val labelCongratulations = Label("Congratulations")
    val buttonRejouer = Button("Rejouer")

    val flowPaneCenter = FlowPane()
    val podium1 = Label()
    val podium2 = Label()
    val podium3 = Label()
    val podium4 = Label()

    val vboxPodium1 = VBox()
    val vboxPodium2 = VBox()
    val vboxPodium3 = VBox()
    val vboxPodium4 = VBox()

    var player4 = Label()

    init {

        this.style = "-fx-background-color: white;"

        fun creerBoiteGrand() : ImageView{
            val input = FileInputStream("assets/boiteGrand.png")
            val img: Image = Image(input)
            val view: ImageView = ImageView(img)
            view.setFitHeight(60.0)
            view.setPreserveRatio(true)
            return view
        }

        fun creerBoitePetit() : ImageView{
            val inputMenu = FileInputStream("assets/boitePetit.png")
            val imgMenu = Image(inputMenu)
            val viewMenu = ImageView(imgMenu)
            viewMenu.fitHeight = 60.0
            viewMenu.isPreserveRatio = true
            return viewMenu

        }

        labelCongratulations.graphic = creerBoiteGrand()
        labelCongratulations.contentDisplay = CENTER // Aligne le texte au centre de l'image
        labelCongratulations.styleClass.add("grand-bouton")


        buttonMenu.graphic = creerBoitePetit()
        buttonMenu.contentDisplay = CENTER
        buttonMenu.style = "-fx-background-color: transparent;"
        buttonMenu.styleClass.add("grand-bouton")

        buttonRejouer.graphic = creerBoitePetit()
        buttonRejouer.contentDisplay = CENTER
        buttonRejouer.style = "-fx-background-color: transparent;"
        buttonRejouer.styleClass.add("grand-bouton")

        buttonMenu.setOnMouseEntered {
            buttonMenu.scaleX = 1.05
            buttonMenu.scaleY = 1.05
        }

        buttonMenu.setOnMouseExited {
            buttonMenu.scaleX = 1.0
            buttonMenu.scaleY = 1.0
        }

        buttonRejouer.setOnMouseEntered {
            buttonRejouer.scaleX = 1.05
            buttonRejouer.scaleY = 1.05
        }

        buttonRejouer.setOnMouseExited {
            buttonRejouer.scaleX = 1.0
            buttonRejouer.scaleY = 1.0
        }
        // PODIUM 1-2-3

        val inputPodium1 = FileInputStream("assets/podium1.png")
        val imgpodium1 = Image(inputPodium1)
        val viewPodium1 = ImageView(imgpodium1)

        viewPodium1.fitHeight = 300.0
        viewPodium1.isPreserveRatio = true
        podium1.graphic = viewPodium1
        podium1.style = "-fx-background-color: transparent;"

        val inputPodium2 = FileInputStream("assets/podium2.png")
        val imgpodium2 = Image(inputPodium2)
        val viewPodium2 = ImageView(imgpodium2)

        viewPodium2.fitHeight = 190.0
        viewPodium2.isPreserveRatio = true
        podium2.graphic = viewPodium2
        podium2.style = "-fx-background-color: transparent;"

        val inputPodium3 = FileInputStream("assets/podium3.png")
        val imgpodium3 = Image(inputPodium3)
        val viewPodium3 = ImageView(imgpodium3)

        viewPodium3.fitHeight = 100.0
        viewPodium3.isPreserveRatio = true
        podium3.graphic = viewPodium3
        podium3.style = "-fx-background-color: transparent;"

        // joueur sur podium
        var vboxPodiums = listOf(vboxPodium1, vboxPodium2, vboxPodium3,vboxPodium4)
        var podiums = listOf(podium1,podium2,podium3,podium4)

        for (i in 0 until nomJoueurs.size) {
            var nomLabel = Label(nomJoueurs[i].donneNom())
            var scoreLabel = Label("${points[i]} pts")
            var vbox = VBox(nomLabel, scoreLabel).apply {
                alignment = Pos.CENTER
                prefWidth = 120.0
                padding = Insets(10.0)
            }
            if (i == 0){
                vbox.styleClass.add("vbox-podium1")
                val inputCrown = FileInputStream("assets/crown.png")
                val imgCrown = Image(inputCrown)
                val viewCrown = ImageView(imgCrown)
                viewCrown.fitHeight = 100.0
                viewCrown.fitWidth = 110.0
                viewCrown.isPreserveRatio = false

                vboxPodiums[i].alignment = Pos.BOTTOM_CENTER
                vboxPodiums[i].spacing = 10.0
                vboxPodiums[i].children.addAll(viewCrown,vbox, podiums[i])


            }
            else{
                vbox.styleClass.add("vbox-podium")
                vboxPodiums[i].alignment = Pos.BOTTOM_CENTER
                vboxPodiums[i].spacing = 10.0
                vboxPodiums[i].children.addAll(vbox, podiums[i])
            }



        }

        //
        borderPaneTop.left = buttonMenu
        borderPaneTop.center = labelCongratulations
        borderPaneTop.right = buttonRejouer

        borderPaneTop.padding = Insets(10.0)

        flowPaneCenter.children.addAll(vboxPodium3,vboxPodium1,vboxPodium2,vboxPodium4)
        flowPaneCenter.alignment = Pos.CENTER
        flowPaneCenter.hgap = 10.0
        flowPaneCenter.padding = Insets(100.0)
        flowPaneCenter.rowValignment = BOTTOM //Aligne le bas des éléments sur la même ligne

        this.top = borderPaneTop
        this.bottom = flowPaneCenter


    }

    fun fixeControleurBouton(bouton: Button, action: EventHandler<ActionEvent?>) {
        bouton.onAction = action
    }

}
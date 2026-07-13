package vue


import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.ContentDisplay.CENTER
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox
import java.io.FileInputStream


class AccueilVue : VBox(){

    val labelFlip7 = Label("Flip7")
    val buttonPlay = Button("Play")
    val buttonRegles = Button("Règles")
    val buttonExit = Button("Exit")

    init {
        this.children.addAll(
            labelFlip7,
            buttonPlay,
            buttonRegles,
            buttonExit
        )

        fun creerIconeBoite(): ImageView {
            val inputBoite = FileInputStream("assets/boitePetit.png")
            val imgBoite = Image(inputBoite)
            val view = ImageView(imgBoite)
            view.fitHeight = 60.0
            view.isPreserveRatio = true
            return view
        }

        fun animationBoutton(bouton: Button){
            bouton.setOnMouseEntered{
                bouton.scaleX = 1.1
                bouton.scaleY = 1.1
            }
            bouton.setOnMouseExited{
                bouton.scaleX = 1.0
                bouton.scaleY = 1.0
            }
        }

        this.alignment = Pos.CENTER

        animationBoutton(buttonRegles)
        animationBoutton(buttonExit)
        animationBoutton(buttonPlay)

        val inputFlip7 = FileInputStream("assets/boiteFlip7.png")
        val imgFlip7 = Image(inputFlip7)
        val view = ImageView(imgFlip7)
        view.fitHeight = 110.0
        view.isPreserveRatio = true

        labelFlip7.graphic = view
        labelFlip7.style = "-fx-background-color: transparent;"
        labelFlip7.styleClass.add("grand-bouton")
        labelFlip7.styleClass.add("flip7")
        labelFlip7.contentDisplay = CENTER

        VBox.setMargin(labelFlip7, Insets(0.0, 20.0, 100.0, 20.0))

        buttonPlay.graphic = creerIconeBoite()
        buttonPlay.contentDisplay = CENTER
        buttonPlay.style = "-fx-background-color: transparent;"
        buttonPlay.styleClass.add("grand-bouton")


        buttonRegles.graphic = creerIconeBoite()
        buttonRegles.contentDisplay = CENTER
        buttonRegles.style = "-fx-background-color: transparent;"
        buttonRegles.styleClass.add("grand-bouton")

        buttonExit.graphic = creerIconeBoite()
        buttonExit.contentDisplay = CENTER
        buttonExit.style = "-fx-background-color: transparent;"
        buttonExit.styleClass.add("grand-bouton")
    }

    fun fixeControleurBouton(bouton: Button, action: EventHandler<ActionEvent>) {
        bouton.onAction = action
    }
}
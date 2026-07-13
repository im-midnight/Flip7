package vue


import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.ContentDisplay.CENTER
import javafx.scene.control.ScrollPane
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox

import java.io.FileInputStream


class RegleVue : ScrollPane() {

    val buttonRetour = Button("Retour")

    init {
        //Relge
        val inputRegle = FileInputStream("assets/regle.png")
        val imgRegle = Image(inputRegle)
        val viewRegle = ImageView(imgRegle)
        viewRegle.scaleX = 0.9
        viewRegle.scaleY = 0.9

        //Button Retour
        val inputRetour = FileInputStream("assets/boiteGrand.png")
        val imgRetour = Image(inputRetour)
        val viewRetour = ImageView(imgRetour)
        viewRetour.fitHeight = 60.0
        viewRetour.isPreserveRatio = true

        buttonRetour.graphic = viewRetour
        buttonRetour.contentDisplay = CENTER
        buttonRetour.styleClass.add("change-font-lilitaone")
        buttonRetour.style = "-fx-background-color: transparent;"
        buttonRetour.styleClass.add("grand-bouton")

        buttonRetour.setOnMouseEntered {
            buttonRetour.scaleX = 1.1
            buttonRetour.scaleY = 1.1
        }
        buttonRetour.setOnMouseExited {
            buttonRetour.scaleX = 1.0
            buttonRetour.scaleY = 1.0
        }
        // la vue
        val conteneur = VBox(20.0)
        conteneur.children.addAll(viewRegle, buttonRetour)
        conteneur.alignment = Pos.CENTER

        this.content = conteneur
        this.isFitToWidth = true
        this.hbarPolicy = ScrollBarPolicy.NEVER
        this.vbarPolicy = ScrollBarPolicy.AS_NEEDED


    }

    fun fixeControleurBouton(bouton: Button, action: EventHandler<ActionEvent>) {
        bouton.onAction = action
    }
}
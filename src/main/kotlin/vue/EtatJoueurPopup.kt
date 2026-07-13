package vue

import Main
import iut.info1.flip7.etats.EtatJoueur
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ContentDisplay.CENTER
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.stage.Modality
import javafx.stage.Stage

class EtatJoueurPopup(etat : EtatJoueur? = null) {

    private val stage = Stage()

    val vuePopup = VBox()
    val text = Label(if (etat == EtatJoueur.PERDU) "WASTED !" else "FLIP7 !")
    val boutonRetour = Button("Ok")

    init {
        stage.initModality(Modality.APPLICATION_MODAL)
        stage.setAlwaysOnTop(true)
        stage.title = "C'est la fin !"

        text.style = "-fx-font-weight: bold; -fx-font-size: 50pt;"

        boutonRetour.setOnAction { stage.close() }

        vuePopup.children.addAll(text,boutonRetour)
        vuePopup.style = if (etat == EtatJoueur.PERDU) "-fx-background: lightpink;" else "-fx-background: lightgreen;"
        vuePopup.alignment = Pos.CENTER
        vuePopup.spacing = 40.0

        boutonRetour.contentDisplay = CENTER
        boutonRetour.styleClass.add("bouton-popUp")
        boutonRetour.styleClass.add("grand-bouton")

        boutonRetour.setOnMouseEntered {
            boutonRetour.scaleX = 1.05
            boutonRetour.scaleY = 1.05
        }
        boutonRetour.setOnMouseExited{
            boutonRetour.scaleX = 1.0
            boutonRetour.scaleY = 1.0
        }

        val scene = Scene(vuePopup,500.0,300.0)
        scene.stylesheets.add(Main::class.java.getResource("/css/style.css").toExternalForm())
        stage.scene = scene

    }

    fun afficher(){
        stage.showAndWait()
    }
}
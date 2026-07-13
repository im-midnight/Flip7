package controleur

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.ButtonType
import model.Flip7Model
import vue.ChoixCiblePopup

class ChoixCibleControleurBoutonChoixJoueur (vue : ChoixCiblePopup, model : Flip7Model) : EventHandler<ActionEvent> {

    private val vue = vue
    private val model = model

    override fun handle(event: ActionEvent) {

        val source = event.source as Button

        var nombre = 0
        var bouton = vue.listeBouton[0]

        when (event.source) {
            vue.listeBouton[0] -> {nombre = 0
                bouton = vue.listeBouton[0]}
            vue.listeBouton[1] -> {nombre = 1
                bouton = vue.listeBouton[1]}
            vue.listeBouton[2] -> {nombre = 2
                bouton = vue.listeBouton[2]}
            vue.listeBouton[3] -> {nombre = 3
                bouton = vue.listeBouton[3]}
        }

        val (etatButton, nombreChoisie) = dialogue(bouton, nombre, model, source)

        if (etatButton == ButtonType.OK) {
            vue.selectionnerCibleEtFermer(nombreChoisie)
        }

        bouton.isDisable = false

    }

    private fun dialogue(bouton : Button, nombre : Int, model: Flip7Model, source : Button) : Pair<ButtonType,Int>{
        bouton.isDisable = true
        val dialog = Alert(Alert.AlertType.CONFIRMATION)
        dialog.initOwner(source.scene.window)
        dialog.title = "Joueur ciblé : ${model.flip7.joueurs[nombre].donneNom()}"
        dialog.headerText = null
        dialog.contentText = "${model.flip7.joueurs[nombre].donneNom()} a été ciblé, il va recevoir la carte spéciale et son effet."

        return Pair(dialog.showAndWait().orElse(ButtonType.CANCEL), nombre)
    }
}


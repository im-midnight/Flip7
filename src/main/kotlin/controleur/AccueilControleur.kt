package controleur

import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Node
import model.Flip7Model
import vue.AccueilVue
import vue.RegleVue

class AccueilControleurBoutonExit (vue: AccueilVue, model : Flip7Model) : EventHandler<ActionEvent> {

    private val vue = vue
    private val model = model

    override fun handle(event : ActionEvent?) {
        Platform.exit()
    }
}

class AccueilControleurBoutonRegles (vue: AccueilVue, model: Flip7Model) : EventHandler<ActionEvent> {

    private val vue = vue
    private val model = model

    override fun handle(event: ActionEvent?) {
        val source = event?.source as Node
        val regleVue = RegleVue()

        source.scene.root = regleVue
        regleVue.fixeControleurBouton(regleVue.buttonRetour, RegleControleurBoutonRetour(regleVue,model))
    }
}

class AccueilControleurBoutonPlay (vue: AccueilVue, model: Flip7Model) : EventHandler<ActionEvent> {

    private val vue = vue
    private val model = model

    override fun handle(event: ActionEvent?) {
        ouvrirMenuVue(event?.source as Node, model)
    }
}
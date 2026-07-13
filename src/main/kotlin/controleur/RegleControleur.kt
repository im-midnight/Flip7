package controleur

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Node
import model.Flip7Model
import vue.AccueilVue
import vue.RegleVue

class RegleControleurBoutonRetour(vue : RegleVue, model : Flip7Model) : EventHandler<ActionEvent> {

    private val vue = vue
    private val model = model

    override fun handle(event : ActionEvent?) {
        val source = event?.source as Node
        val accueilVue = AccueilVue()

        source.scene.root = accueilVue
        accueilVue.fixeControleurBouton(accueilVue.buttonPlay, AccueilControleurBoutonPlay(accueilVue,model))
        accueilVue.fixeControleurBouton(accueilVue.buttonRegles, AccueilControleurBoutonRegles(accueilVue,model))
        accueilVue.fixeControleurBouton(accueilVue.buttonExit, AccueilControleurBoutonExit(accueilVue,model))
    }
}
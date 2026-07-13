package controleur

import iut.info1.flip7.etats.EtatPartie
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Node
import model.Flip7Model
import vue.PodiumVue
import vue.ScoreboardVue

class ScoreboardControleurBoutonSuivant (vue: ScoreboardVue, model: Flip7Model) : EventHandler<ActionEvent> {

    private val vue = vue
    private val model = model

    override fun handle(event: ActionEvent?) {
        val source = event?.source as Node
        if (model.flip7.etatPartie == EtatPartie.NOUVELLE_MANCHE){
            model.flip7.nouvelleManche()
            model.tour = 1

            ouvrirJeuVue(source, model)

        } else {
            var classement = model.getClassement()
            val podiumVue = PodiumVue(classement)

            model.updateDataJoueurFinJeu()

            source.scene.root = podiumVue

            podiumVue.fixeControleurBouton(podiumVue.buttonRejouer, PodiumControleurBoutonRejouer(podiumVue,model))
            podiumVue.fixeControleurBouton(podiumVue.buttonMenu, PodiumControleurBoutonMenu(podiumVue,model))

        }

    }
}
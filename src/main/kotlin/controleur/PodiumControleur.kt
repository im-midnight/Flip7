package controleur

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Node
import model.Flip7Model
import vue.JeuVue
import vue.PodiumVue

class PodiumControleurBoutonRejouer (vue: PodiumVue,model: Flip7Model) : EventHandler<ActionEvent?> {

    private val vue = vue
    private val model = model

    override fun handle(event : ActionEvent?) {
        model.initialisationFlip7()

        model.tour = 0

        val source = event?.source as Node
        val jeuVue = JeuVue(model.flip7.joueurs,model.calculeScoreMancheActuelle())

        source.scene.root = jeuVue

        jeuVue.fixeControleurBouton(jeuVue.buttonStop,JeuControleurBoutonStop(jeuVue,model))
        jeuVue.fixeControleurBouton(jeuVue.buttonPiocher, JeuControleurBoutonPioche(jeuVue,model))

        jeuVue.setComboTheme(jeuVue.comboTheme, model.themes, model.theme)
        val JCCT = JeuControleurComboTheme(jeuVue, model)
        jeuVue.comboTheme.onAction = JCCT

        jeuVue.updateAllJeuVue(
            joueurCourant = model.flip7.joueurCourant,
            etatJoueurs = model.flip7.etatJoueur,
            mains = model.flip7.main,
            nbCartesPioche = model.flip7.taillePioche,
            nbCartesDefausse = model.flip7.defausse.size,
            scoresMancheActuelle = model.calculeScoreMancheActuelle(),
            mancheActuelle = model.mancheActuelle,
            tour = model.tour,
            scoreMax = model.scoreMax.value
        )
    }
}

class PodiumControleurBoutonMenu (vue: PodiumVue,model: Flip7Model) : EventHandler<ActionEvent?> {

    private val vue = vue
    private val model = model

    override fun handle(event: ActionEvent?) {
        ouvrirMenuVue(event?.source as Node, model)
    }
}
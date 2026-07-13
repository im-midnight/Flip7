package controleur

import iut.info1.flip7.etats.EtatJoueur
import iut.info1.flip7.etats.EtatPartie
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Node
import model.Flip7Model
import vue.ChoixCiblePopup
import vue.EtatJoueurPopup
import vue.JeuVue
import vue.ScoreboardVue

fun ouvrirJeuVue(source: Node, model: Flip7Model) {
    val jeuVue = JeuVue(model.flip7.joueurs,model.calculeScoreMancheActuelle())

    jeuVue.fixeControleurBouton(jeuVue.buttonStop,JeuControleurBoutonStop(jeuVue,model))
    jeuVue.fixeControleurBouton(jeuVue.buttonPiocher, JeuControleurBoutonPioche(jeuVue,model))

    source.scene.root = jeuVue


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

    jeuVue.setComboTheme(jeuVue.comboTheme, model.themes, model.theme)

    val JCCT = JeuControleurComboTheme(jeuVue, model)
    jeuVue.comboTheme.onAction = JCCT
}


class JeuControleurBoutonPioche (vue : JeuVue, model : Flip7Model) : EventHandler<ActionEvent> {

    private val vue = vue
    private val model = model

    override fun handle(event: ActionEvent) {
        if (vue.animationEnCours) {
            return
        }


        val joueurQuiPioche = model.flip7.joueurCourant
        val ancienJoueur = model.flip7.joueurCourant

        val carte = model.flip7.joueurCourantPiocheUneCarte()
        val newJoueur = model.flip7.joueurCourant

        vue.annimationCartePioche(carte)


        val etatPartie = model.flip7.etatPartie
        val etatJoueurQuiPioche = model.flip7.etatJoueur[joueurQuiPioche]

        val source = event.source as Node
        if(newJoueur == ancienJoueur) {

            model.tour++
        }
        if (newJoueur < ancienJoueur) {
            model.tour++
        }

        when (etatPartie) {

            EtatPartie.ATTENTE_CHOIX_JOUEUR -> {

                vue.updateAllJeuVue(
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

                val popup = EtatJoueurPopup(etatJoueurQuiPioche)
                if (etatJoueurQuiPioche == EtatJoueur.PERDU) popup.afficher()

            }
            EtatPartie.ATTENTE_CIBLE_STOP -> {
                val choixStop = ChoixCiblePopup(model.flip7.joueurs,model.flip7.etatJoueur,carte)

                val CCCBCJ = ChoixCibleControleurBoutonChoixJoueur(choixStop, model)
                choixStop.listeBouton.forEach { bouton ->
                    choixStop.fixeControleurBouton(bouton, CCCBCJ)
                }

                val joueurCible = choixStop.afficher()

                model.flip7.joueurCourantCibleStop(carte,joueurCible)

                vue.updateAllJeuVue(
                    joueurCourant = model.flip7.joueurCourant,
                    etatJoueurs = model.flip7.etatJoueur,
                    mains = model.flip7.main,
                    nbCartesPioche = model.flip7.taillePioche,
                    nbCartesDefausse = model.flip7.defausse.size,
                    scoresMancheActuelle = model.calculeScoreMancheActuelle(),
                    mancheActuelle = model.mancheActuelle,
                    tour = model.tour,
                    scoreMax = model.scoreMax.value)

                val etatJoueurCible = model.flip7.etatJoueur[joueurCible]

                val popupPerdu = EtatJoueurPopup(etatJoueurCible)
                if (etatJoueurCible == EtatJoueur.PERDU) popupPerdu.afficher()

                val etatPartie = model.flip7.etatPartie

                if (etatPartie != EtatPartie.ATTENTE_CHOIX_JOUEUR) {
                    model.flip7.scoreManche()
                    model.sauvegarderManche()

                    val scoreboardVue = ScoreboardVue(model.flip7.joueurs, model.flip7.score)
                    scoreboardVue.updateScoreBoardVue(model.flip7.joueurs,model.scoreManches,model.flip7.score,model.mancheActuelle,model.scoreMax.value)

                    source.scene.root = scoreboardVue

                    scoreboardVue.fixeControleurBouton(scoreboardVue.boutonProchaineManche,ScoreboardControleurBoutonSuivant(scoreboardVue,model))
                }

            }
            EtatPartie.ATTENTE_CIBLE_3SUITE -> {
                val choix3Suite = ChoixCiblePopup(model.flip7.joueurs,model.flip7.etatJoueur,carte)

                val CCCBCJ = ChoixCibleControleurBoutonChoixJoueur(choix3Suite, model)
                choix3Suite.listeBouton.forEach { bouton ->
                    choix3Suite.fixeControleurBouton(bouton, CCCBCJ)
                }

                val joueurCible = choix3Suite.afficher()

                model.flip7.joueurCourantCible3aLaSuite(carte,joueurCible)

                vue.updateAllJeuVue(
                    joueurCourant = model.flip7.joueurCourant,
                    etatJoueurs = model.flip7.etatJoueur,
                    mains = model.flip7.main,
                    nbCartesPioche = model.flip7.taillePioche,
                    nbCartesDefausse = model.flip7.defausse.size,
                    scoresMancheActuelle = model.calculeScoreMancheActuelle(),
                    mancheActuelle = model.mancheActuelle,
                    tour = model.tour,
                    scoreMax = model.scoreMax.value)

                val etatJoueurCible = model.flip7.etatJoueur[joueurCible]

                val popupPerdu = EtatJoueurPopup(etatJoueurCible)
                if (etatJoueurCible == EtatJoueur.PERDU) popupPerdu.afficher()

                val etatPartie = model.flip7.etatPartie

                if (etatPartie != EtatPartie.ATTENTE_CHOIX_JOUEUR) {
                    if (model.flip7.main.map { main -> model.flip7.outilsCarte.estFlip7(main.value)}.filter { it == true }.isNotEmpty() ) {
                        val popupFlip7 = EtatJoueurPopup()
                        popupFlip7.afficher()
                    }

                    model.flip7.scoreManche()
                    model.sauvegarderManche()

                    val scoreboardVue = ScoreboardVue(model.flip7.joueurs, model.flip7.score)
                    scoreboardVue.updateScoreBoardVue(model.flip7.joueurs,model.scoreManches,model.flip7.score,model.mancheActuelle,model.scoreMax.value)

                    source.scene.root = scoreboardVue
                    scoreboardVue.fixeControleurBouton(scoreboardVue.boutonProchaineManche,ScoreboardControleurBoutonSuivant(scoreboardVue,model))
                }
            }
            else ->{
                vue.updateAllJeuVue(
                    joueurCourant = model.flip7.joueurCourant,
                    etatJoueurs = model.flip7.etatJoueur,
                    mains = model.flip7.main,
                    nbCartesPioche = model.flip7.taillePioche,
                    nbCartesDefausse = model.flip7.defausse.size,
                    scoresMancheActuelle = model.calculeScoreMancheActuelle(),
                    mancheActuelle = model.mancheActuelle,
                    tour = model.tour,
                    scoreMax = model.scoreMax.value)

                if (model.flip7.main.map { main -> model.flip7.outilsCarte.estFlip7(main.value)}.filter { it == true }.isNotEmpty() ) {
                    val popupFlip7 = EtatJoueurPopup()
                    popupFlip7.afficher()
                } else if (etatJoueurQuiPioche == EtatJoueur.PERDU) {
                    val popupFlip7 = EtatJoueurPopup(etatJoueurQuiPioche)
                    popupFlip7.afficher()
                }

                model.flip7.scoreManche()
                model.sauvegarderManche()

                var scoreboardVue= ScoreboardVue(model.flip7.joueurs, model.flip7.score)

                scoreboardVue.updateScoreBoardVue(model.flip7.joueurs,model.scoreManches,model.flip7.score,model.mancheActuelle,model.scoreMax.value)

                source.scene.root = scoreboardVue
                scoreboardVue.fixeControleurBouton(scoreboardVue.boutonProchaineManche,ScoreboardControleurBoutonSuivant(scoreboardVue,model))
            }
        }
    }
}

class JeuControleurBoutonStop (vue : JeuVue, model : Flip7Model) : EventHandler<ActionEvent> {

    private val vue = vue
    private val model = model

    override fun handle(event: ActionEvent){
        if (vue.animationEnCours) {
            return
        }


        model.flip7.joueurCourantDitStop()
        val etatPartie = model.flip7.etatPartie

        val source = event.source as Node

        when (etatPartie) {
            EtatPartie.ATTENTE_CHOIX_JOUEUR -> {
                vue.updateAllJeuVue(
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
            else -> {
                model.flip7.scoreManche()
                model.sauvegarderManche()

                val scoreboardVue = if (source.scene.root is ScoreboardVue) {
                    source.scene.root as ScoreboardVue
                } else {
                    ScoreboardVue(model.flip7.joueurs, model.flip7.score)
                }
                scoreboardVue.updateScoreBoardVue(model.flip7.joueurs,model.scoreManches,model.flip7.score,model.mancheActuelle,model.scoreMax.value)

                source.scene.root = scoreboardVue
                scoreboardVue.fixeControleurBouton(scoreboardVue.boutonProchaineManche,ScoreboardControleurBoutonSuivant(scoreboardVue,model))
            }
        }
    }
}


class JeuControleurComboTheme(
    private val vue: JeuVue,
    private val model: Flip7Model
): EventHandler<ActionEvent> {

    override fun handle(event: ActionEvent?) {

        val selected = vue.comboTheme.value
        if (selected != null) {
            model.theme.set(selected)
        }

        vue.updateAllJeuVue(
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
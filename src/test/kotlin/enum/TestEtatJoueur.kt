package enum

import model.Joueur
import iut.info1.flip7.Flip7
import iut.info1.flip7.IJoueur
import iut.info1.flip7.cartes.Carte
import iut.info1.flip7.cartes.CarteNum
import iut.info1.flip7.cartes.CarteStop
import iut.info1.flip7.etats.EtatJoueur
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TestEtatJoueur {
    private lateinit var joueur1: IJoueur
    private lateinit var joueur2: IJoueur

    private lateinit var listeJoueurs: List<IJoueur>

    @BeforeEach
    fun setup() {
        joueur1 = Joueur("Noe")
        joueur2 = Joueur("Liam")
        listeJoueurs = listOf(joueur1, joueur2,)
    }

    private fun creerJeu( cartes: List<Carte>,nbJoueurs : Int = 2, score : Int = 200): Flip7 {
        return Flip7(nbJoueurs, listeJoueurs.subList(0,nbJoueurs), cartes, true,score)
    }

    @Test
    fun CT1_EtatJoueurApresCreation(){
        val flip = creerJeu(listOf(CarteNum(1), CarteNum(2), CarteNum(3)))
        val joueur = flip.joueurCourant
        assertTrue(flip.etatJoueur[joueur] == EtatJoueur.JOUE_ENCORE)
    }


    @Test
    fun CT1_EtatJoueurApresDoublon(){
        val flip = creerJeu(listOf(CarteNum(1), CarteNum(2), CarteNum(1)))
        val joueur = flip.joueurCourant
        flip.joueurCourantPiocheUneCarte()
        flip.joueurCourantPiocheUneCarte()
        flip.joueurCourantPiocheUneCarte()

        assertTrue(flip.etatJoueur[joueur] == EtatJoueur.PERDU)
    }

    @Test
    fun CT1_EtatJoueurPerduApresNouvelleManche() {
        val flip = creerJeu(listOf(CarteNum(1), CarteNum(2), CarteNum(1)))
        val joueur = flip.joueurCourant
        flip.joueurCourantPiocheUneCarte()
        flip.joueurCourantPiocheUneCarte()
        flip.joueurCourantPiocheUneCarte()

        assertTrue(flip.etatJoueur[joueur] == EtatJoueur.PERDU)

        flip.joueurCourantDitStop()

        flip.scoreManche()

        flip.nouvelleManche()

        assertEquals(flip.etatJoueur[joueur], EtatJoueur.JOUE_ENCORE)
    }

    @Test
    fun CT1_EtatJoueurStopApresNouvelleManche() {
        val flip = creerJeu(listOf(CarteNum(1), CarteNum(2), CarteNum(1)))
        val joueur = flip.joueurCourant

        flip.joueurCourantDitStop()
        flip.joueurCourantDitStop()

        assertTrue(flip.etatJoueur[joueur] == EtatJoueur.STOP)

        flip.scoreManche()

        flip.nouvelleManche()

        assertEquals(flip.etatJoueur[joueur], EtatJoueur.JOUE_ENCORE)
    }

    @Test
    fun testEtatJoueur_joueurDitStop (){
        val flip = creerJeu(listOf(CarteNum(1), CarteNum(2)))
        val joueur = flip.joueurCourant

        assertEquals(EtatJoueur.JOUE_ENCORE, flip.etatJoueur[joueur])

        flip.joueurCourantDitStop()

        assertEquals(EtatJoueur.STOP, flip.etatJoueur[joueur])
    }

    @Test
    fun testEtatJoueur_joueurEstStopee (){
        val CarteStop = CarteStop()
        val flip = creerJeu(listOf(CarteStop, CarteNum(2)))
        val joueur = flip.joueurCourant

        assertEquals(EtatJoueur.JOUE_ENCORE, flip.etatJoueur[joueur])

        flip.joueurCourantPiocheUneCarte()

        flip.joueurCourantCibleStop(CarteStop,joueur)

        assertEquals(EtatJoueur.STOP, flip.etatJoueur[joueur])
    }


}
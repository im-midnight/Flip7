package enum
import model.Joueur
import iut.info1.flip7.Flip7
import iut.info1.flip7.IJoueur
import iut.info1.flip7.cartes.Carte
import iut.info1.flip7.cartes.Carte2ndeChance
import iut.info1.flip7.cartes.Carte3aLaSuite
import iut.info1.flip7.cartes.CarteBonusPlus
import iut.info1.flip7.cartes.CarteNum
import iut.info1.flip7.cartes.CarteStop
import iut.info1.flip7.etats.EtatPartie
import iut.info1.flip7.exceptions.EtatPartieInvalideException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class TestEtatPartie {
    private lateinit var joueur1: IJoueur
    private lateinit var joueur2: IJoueur
    private lateinit var joueur3: IJoueur
    private lateinit var joueur4: IJoueur
    private lateinit var listeJoueurs: List<IJoueur>

    @BeforeEach
    fun setup() {
        joueur1 = Joueur("Bob")
        joueur2 = Joueur("Paul")
        joueur3 = Joueur("Noe")
        joueur4 = Joueur("Liam")
        listeJoueurs = listOf(joueur1, joueur2,joueur3,joueur4)
    }

    private fun creerJeu( cartes: List<Carte>,nbJoueurs : Int = 2, score : Int = 200,listeJoueurs : List<IJoueur> = this.listeJoueurs.subList(0,nbJoueurs)): Flip7 {
        return Flip7(nbJoueurs,listeJoueurs, cartes, true,score)
    }

    @Test
    fun CT_ATTENTE_CHOIX_JOUEUR() {
        val flip = creerJeu(listOf(CarteNum(0)))
        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, flip.etatPartie)
    }

    @Test
    fun CT1_ETAT_CIBLE_STOP_Pioche() {
        val flip = creerJeu(listOf(CarteStop()))

        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, flip.etatPartie)

        val carte = flip.joueurCourantPiocheUneCarte()

        assertEquals(EtatPartie.ATTENTE_CIBLE_STOP, flip.etatPartie)
    }

    @Test
    fun CT2_ETAT_CIBLE_STOP_Cible() {
        val flip = creerJeu(listOf(CarteStop()))
        val carte = flip.joueurCourantPiocheUneCarte()

        assertEquals(EtatPartie.ATTENTE_CIBLE_STOP, flip.etatPartie)

        flip.joueurCourantCibleStop(carte, 1)

        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, flip.etatPartie)
    }

    @Test
    fun CT1_ETAT_MANCHE_TERMINEE() {
        val flip = creerJeu(listOf(CarteNum(2), CarteNum(2), CarteNum(1)))
        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, flip.etatPartie)
        flip.joueurCourantDitStop()

        flip.joueurCourantPiocheUneCarte()
        flip.joueurCourantPiocheUneCarte()
        
        assertEquals(EtatPartie.MANCHE_TERMINEE, flip.etatPartie)
    }

    @Test
    fun CT2_ETAT_MANCHE_TERMINEE() {
        val flip = creerJeu(listOf(CarteStop()))
        flip.joueurCourantDitStop()
        val carte = flip.joueurCourantPiocheUneCarte()

        assertEquals(EtatPartie.ATTENTE_CIBLE_STOP, flip.etatPartie)

        flip.joueurCourantCibleStop(carte, 1)

        assertEquals(EtatPartie.MANCHE_TERMINEE, flip.etatPartie)
    }

    @Test
    fun CT3_ETAT_MANCHE_TERMINEE() {
        val flip = creerJeu(listOf(CarteNum(0)))

        flip.joueurCourantDitStop()
        flip.joueurCourantDitStop()

        assertEquals(EtatPartie.MANCHE_TERMINEE, flip.etatPartie)
    }

    @Test
    fun CT4_ETAT_MANCHE_TERMINEE() {
        val flip = creerJeu(listOf(CarteNum(0), CarteNum(1), CarteNum(2), CarteNum(3), CarteNum(4), CarteNum(5), CarteNum(6), CarteNum(7)))

        flip.joueurCourantDitStop()

        for (i in 0..6) flip.joueurCourantPiocheUneCarte()

        assertEquals(EtatPartie.MANCHE_TERMINEE, flip.etatPartie)
    }

    @Test
    fun CT5_ETAT_MANCHE_TERMINEE() {
        val flip = creerJeu(listOf(Carte3aLaSuite(), CarteNum(0), CarteNum(2), CarteNum(2)))

        flip.joueurCourantDitStop()
        
        val carte = flip.joueurCourantPiocheUneCarte()

        assertEquals(EtatPartie.ATTENTE_CIBLE_3SUITE, flip.etatPartie)

        flip.joueurCourantCible3aLaSuite(carte, 1)

        assertEquals(EtatPartie.MANCHE_TERMINEE, flip.etatPartie)
    }


    @Test
    fun TestPiocheInvalideQuandATTENTE_CIBLE_STOP() {
        val flip = creerJeu(listOf(CarteStop(), CarteNum(1)))

        flip.joueurCourantPiocheUneCarte()

        assertThrows<EtatPartieInvalideException> {
            flip.joueurCourantPiocheUneCarte()
        }
    }

    @Test
    fun TestATTENTE_CHOIX_JOUEURStop() {
        val flip = creerJeu(listOf(CarteStop()))
        val carte = flip.joueurCourantPiocheUneCarte()

        flip.joueurCourantCibleStop(carte, 1)

        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, flip.etatPartie)
    }

    @Test
    fun testEtat_attenteCible3Suite() {
        val flip = creerJeu(listOf(Carte3aLaSuite()))

        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, flip.etatPartie)

        flip.joueurCourantPiocheUneCarte()

        assertEquals(EtatPartie.ATTENTE_CIBLE_3SUITE, flip.etatPartie)
    }

    @Test
    fun testEtat_nouvelleManche() {
        val flip = creerJeu((1..7).map { CarteNum(it)})
        flip.joueurCourantDitStop()
        repeat(7,{flip.joueurCourantPiocheUneCarte()})
        assertEquals(EtatPartie.MANCHE_TERMINEE, flip.etatPartie)

        flip.scoreManche()

        assertEquals(EtatPartie.NOUVELLE_MANCHE, flip.etatPartie)
    }

    @Test
    fun testEtat_partieTerminee() {
        val flip = creerJeu(cartes = (1..5).map {CarteBonusPlus(it*2)} + (1..7).map { CarteNum(it) }, score = 50)

        flip.joueurCourantDitStop()

        repeat(12,{flip.joueurCourantPiocheUneCarte()})

        assertEquals(EtatPartie.MANCHE_TERMINEE, flip.etatPartie)

        flip.scoreManche()

        assertEquals(EtatPartie.PARTIE_TERMINEE, flip.etatPartie)
    }

    @Test
    fun CT1_testEtat_attenteChoixJoueur() {
        val flip = creerJeu(listOf(CarteNum(1)))

        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, flip.etatPartie)

        flip.joueurCourantPiocheUneCarte()

        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, flip.etatPartie)
    }

    @Test
    fun CT2_testEtat_attenteChoixJoueur() {
        val flip = creerJeu(listOf(CarteNum(1),CarteNum(1), Carte2ndeChance(),CarteNum(2),CarteNum(1)))

        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, flip.etatPartie)

        repeat(5,{flip.joueurCourantPiocheUneCarte()})

        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, flip.etatPartie)
    }

    @Test
    fun CT3_testEtat_attenteChoixJoueur() {
        val flip = creerJeu(listOf(CarteNum(1)))

        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, flip.etatPartie)

        flip.joueurCourantDitStop()

        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, flip.etatPartie)
    }

    @Test
    fun CT4_testEtat_attenteChoixJoueur() {
        val flip = creerJeu((1..7).map { CarteNum(it)})
        flip.joueurCourantDitStop()
        repeat(7,{flip.joueurCourantPiocheUneCarte()})
        flip.scoreManche()

        assertEquals(EtatPartie.NOUVELLE_MANCHE, flip.etatPartie)

        flip.nouvelleManche()

        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, flip.etatPartie)
    }

    @Test
    fun CT5_testEtat_attenteChoixJoueur() {
        val carte3suite = Carte3aLaSuite()
        val flip = creerJeu(listOf(carte3suite, CarteNum(1),CarteNum(2),CarteNum(3)))
        flip.joueurCourantPiocheUneCarte()

        assertEquals(EtatPartie.ATTENTE_CIBLE_3SUITE, flip.etatPartie)

        flip.joueurCourantCible3aLaSuite(carte3suite,1)

        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, flip.etatPartie)

    }

    @Test
    fun CT6_testEtat_attenteChoixJoueur() {
        val carteStop = CarteStop()
        val flip = creerJeu(listOf(carteStop, CarteNum(1),CarteNum(2),CarteNum(3)))
        flip.joueurCourantPiocheUneCarte()

        assertEquals(EtatPartie.ATTENTE_CIBLE_STOP, flip.etatPartie)

        flip.joueurCourantCibleStop(carteStop,1)

        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, flip.etatPartie)

    }
}
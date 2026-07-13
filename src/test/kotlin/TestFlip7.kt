import model.Joueur
import iut.info1.flip7.Flip7
import iut.info1.flip7.IJoueur
import iut.info1.flip7.cartes.Carte
import iut.info1.flip7.cartes.Carte2ndeChance
import iut.info1.flip7.cartes.Carte3aLaSuite
import iut.info1.flip7.cartes.CarteBonusMultiplie
import iut.info1.flip7.cartes.CarteBonusPlus
import iut.info1.flip7.cartes.CarteNum
import iut.info1.flip7.cartes.CarteStop
import iut.info1.flip7.cartes.OutilsCarte
import iut.info1.flip7.etats.EtatJoueur
import iut.info1.flip7.etats.EtatPartie
import iut.info1.flip7.exceptions.CarteInvalideException
import iut.info1.flip7.exceptions.EtatPartieInvalideException
import iut.info1.flip7.exceptions.IndiceJoueurInvalideException
import iut.info1.flip7.exceptions.JoueurNonActifException
import iut.info1.flip7.exceptions.ListeJoueursInvalideException
import iut.info1.flip7.exceptions.MainInvalideException
import iut.info1.flip7.exceptions.NombreJoueursInvalideException
import iut.info1.flip7.exceptions.PiocheInvalideException
import iut.info1.flip7.exceptions.ScoreFinPartieInvalideException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream



class TestFlip7 {
    private lateinit var joueur1: IJoueur
    private lateinit var joueur2: IJoueur
    private lateinit var joueur3: IJoueur
    private lateinit var joueur4: IJoueur
    private lateinit var listeJoueurss: List<IJoueur>

    @BeforeEach
    fun setup() {
        joueur1 = Joueur("Bob")
        joueur2 = Joueur("Paul")
        joueur3 = Joueur("Noe")
        joueur4 = Joueur("Liam")
        listeJoueurss = listOf(joueur1, joueur2,joueur3,joueur4)
    }

    private fun creerJeu( cartes: List<Carte>,nbJoueurs : Int = 2, score : Int = 200,listeJoueurs : List<IJoueur> = listeJoueurss.subList(0,nbJoueurs)): Flip7 {
        return Flip7(nbJoueurs,listeJoueurs, cartes, true,score)
    }

    //joueurCourantPiocheUneCarte()
    @Test
    fun CT1_joueurCourantPiocheUneCarteNumSansDoublon() {
        val flip = creerJeu(listOf(CarteNum(1), CarteNum(2), CarteNum(3)))
        val joueurInitial = flip.joueurCourant

        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, flip.etatPartie)
        var mainJoueur1 = flip.main[0] // Deck du Joueur1
        val cartePiochee = flip.joueurCourantPiocheUneCarte()

        assertTrue(mainJoueur1!!.contains(cartePiochee))
        assertTrue(joueurInitial+1 == flip.joueurCourant)

    }

    @Test
    fun CT2_joueurCourantPiocheUneCarteNumDoublon() {
        var flip = creerJeu(listOf(CarteNum(1), CarteNum(1),CarteNum(1)))

        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, flip.etatPartie)
        val joueurInitial = flip.joueurCourant

        flip.joueurCourantPiocheUneCarte()
        assertTrue(joueurInitial+1 == flip.joueurCourant)
        flip.joueurCourantPiocheUneCarte()
        flip.joueurCourantPiocheUneCarte()
        var outilsCarte = OutilsCarte()
        var mainJoueur1 = flip.main[0] // Deck du Joueur1

        assertThrows<MainInvalideException> {
            outilsCarte.estFlip7(mainJoueur1!!)
        }
        assertEquals(EtatJoueur.PERDU, flip.etatJoueur[0])

    }

    //RAjout Test Round ROBIN
    @Test
    fun CT3_joueurCourantPiocheUneCarte3aLaSuiteNormale() {
        val flip = creerJeu(listOf(Carte3aLaSuite(), CarteNum(1),CarteNum(3),CarteNum(2)))
        var cartePioche = flip.joueurCourantPiocheUneCarte()
        assertEquals(EtatPartie.ATTENTE_CIBLE_3SUITE, flip.etatPartie)
        flip.joueurCourantCible3aLaSuite(cartePioche,0)

        var deckJoueur1 = flip.main[0]
        var outilsCarte = OutilsCarte()
        outilsCarte.verifieMainCorrecte(deckJoueur1!!)
    }
/*
    //BUG

    @Test
    fun CT3_joueurCourantPiocheUneCarte3aLaSuiteCarteStopInvalide() {

        var pioche = listOf(Carte3aLaSuite(), CarteStop(),CarteNum(3))

        val flip = creerJeu(pioche)

        var cartePioche = flip.joueurCourantPiocheUneCarte()

        assertEquals(EtatPartie.ATTENTE_CIBLE_3SUITE, flip.etatPartie) // True

        assertThrows<PiocheInvalideException> {
            flip.joueurCourantCible3aLaSuite(cartePioche,0)
        }
    }

    //Bug

    @Test
    fun CT3_joueurCourantPiocheUneCarte3aLaSuiteCarte3aLaSuiteInvalide() {

        var pioche = listOf(Carte3aLaSuite(), Carte3aLaSuite(),CarteNum(3))

        val flip = creerJeu(pioche)

        var cartePioche = flip.joueurCourantPiocheUneCarte()

        assertEquals(EtatPartie.ATTENTE_CIBLE_3SUITE, flip.etatPartie) // True

        assertThrows<PiocheInvalideException> {
            flip.joueurCourantCible3aLaSuite(cartePioche,0)
        }
    }
*/
    @Test
    fun CT4_joueurCourantPiocheUneCarte3aLaSuiteCarteStopPiocheValide() {

        var pioche = listOf(Carte3aLaSuite(), CarteStop(),CarteNum(3),CarteNum(2),CarteNum(1))

        val flip = creerJeu(pioche)

        var cartePioche = flip.joueurCourantPiocheUneCarte()

        assertEquals(EtatPartie.ATTENTE_CIBLE_3SUITE, flip.etatPartie) // True
        flip.joueurCourantCible3aLaSuite(cartePioche,0)

        var deckJoueur1 = flip.main[0]
        assertEquals(4,deckJoueur1!!.size)
    }

    @Test
    fun CT5_joueurCourantPiocheUneCarte3aLaSuiteCarte3aLaSuitePiocheValide() {

        var pioche = listOf(Carte3aLaSuite(), Carte3aLaSuite(),CarteNum(3),CarteNum(2),CarteNum(1))

        val flip = creerJeu(pioche)
        var deckJoueur1 = flip.main[0]

        var cartePioche = flip.joueurCourantPiocheUneCarte()

        assertEquals(EtatPartie.ATTENTE_CIBLE_3SUITE, flip.etatPartie) // True

        flip.joueurCourantCible3aLaSuite(cartePioche,0)

        assertEquals(4,deckJoueur1!!.size)
    }
    @Test
    fun CT6_joueurCourantPiocheUneCarte3aLaSuiteCarte2ndeChanceValide() {

        var pioche = listOf(Carte3aLaSuite(), Carte2ndeChance(),CarteNum(3),CarteNum(2),CarteNum(1))

        val flip = creerJeu(pioche)
        var deckJoueur1 = flip.main[0]

        var cartePioche = flip.joueurCourantPiocheUneCarte()

        assertEquals(EtatPartie.ATTENTE_CIBLE_3SUITE, flip.etatPartie) // True

        flip.joueurCourantCible3aLaSuite(cartePioche,0)

        assertEquals(4,deckJoueur1!!.size)
    }

    @Test
    fun CT7_joueurCourantPiocheUneCarte3aLaSuitePiocheVide() {

        var pioche = listOf(Carte3aLaSuite(),CarteNum(1))

        val flip = creerJeu(pioche)

        var cartePioche = flip.joueurCourantPiocheUneCarte()

        assertEquals(EtatPartie.ATTENTE_CIBLE_3SUITE, flip.etatPartie) // True

        assertThrows<PiocheInvalideException>{
            flip.joueurCourantCible3aLaSuite(cartePioche,0)
        }
    }


    @Test
    fun CT8_joueurCourantPiocheUneCarte2ndeChance() {
        val flip = creerJeu(listOf(Carte2ndeChance()))
        var cartePioche = flip.joueurCourantPiocheUneCarte()

        var mainJoueur1 = flip.main[0] // Deck du Joueur1
        assertTrue(mainJoueur1!!.contains(cartePioche))
    }

    @Test
    fun CT9_joueurCourantPiocheUneCarte2ndeChancePerdu() {
        var pioche = listOf(
            Carte2ndeChance(),CarteNum(1),
            CarteNum(1), CarteNum(2),
            CarteNum(1)
        )

        val flip = creerJeu(pioche)

        for( i in 0 until pioche.size){
            flip.joueurCourantPiocheUneCarte()
        }
        assertEquals(EtatJoueur.JOUE_ENCORE,flip.etatJoueur[0])
    }

    @Test
    fun CT10_joueurCourantPiocheUneCarteStop(){
        val flip = creerJeu(listOf(CarteStop()))
        flip.joueurCourantPiocheUneCarte()

        assertEquals(EtatPartie.ATTENTE_CIBLE_STOP, flip.etatPartie)
    }

    @Test
    fun CT11_joueurCourantPiocheUneCarteStopIndiceJoueurValide() {
        val flip = creerJeu(listOf(CarteStop()))

        var carte = flip.joueurCourantPiocheUneCarte()

        assertEquals(EtatPartie.ATTENTE_CIBLE_STOP, flip.etatPartie)
        flip.joueurCourantCibleStop(carte,1)

        assertEquals(EtatJoueur.STOP,flip.etatJoueur[1])
    }

    @Test
    fun CT12_joueurCourantPiocheUneCarteStopIndiceJoueurInvalide() {
        val flip = creerJeu(listOf(CarteStop()))

        var carte = flip.joueurCourantPiocheUneCarte()

        assertEquals(EtatPartie.ATTENTE_CIBLE_STOP, flip.etatPartie)
        assertThrows<IndiceJoueurInvalideException> { flip.joueurCourantCibleStop(carte,-1)  }

    }

    @Test
    fun CT13_joueurCourantPiocheUneCarteBonusPlus() {
        val flip = creerJeu(listOf(CarteBonusPlus(2)))
        val joueurInitial = flip.joueurCourant

        var cartePioche = flip.joueurCourantPiocheUneCarte()

        var mainJoueur1 = flip.main[0] // Deck du Joueur1
        assertTrue(mainJoueur1!!.contains(cartePioche))
        assertTrue(joueurInitial+1 == flip.joueurCourant)
    }

    @Test
    fun CT14_joueurCourantPiocheUneCarteEtatInvalide() {
        val flip = creerJeu(listOf(CarteNum(1), CarteNum(1), CarteNum(1)))

        flip.joueurCourantDitStop()
        flip.joueurCourantDitStop()

        assertThrows<EtatPartieInvalideException> { flip.joueurCourantPiocheUneCarte() }
    }
    //joueurCourantCibleCarteStop()

    @Test
    fun CT1_joueurCourantCibleCarteStopJoueurInvalideInferieur0(){
        val flip = creerJeu(listOf(CarteStop()))
        var cartePioche = flip.joueurCourantPiocheUneCarte()

        assertEquals(EtatPartie.ATTENTE_CIBLE_STOP, flip.etatPartie)
        assertEquals(EtatJoueur.JOUE_ENCORE, flip.etatJoueur[1])
        assertThrows<IndiceJoueurInvalideException> {
            flip.joueurCourantCibleStop(cartePioche,-1)
        }
    }

    @Test
    fun CT2_joueurCourantCibleCarteStopJoueurInvalideSuperieur3(){
        val flip = creerJeu(listOf(CarteStop()),4)
        var cartePioche = flip.joueurCourantPiocheUneCarte()
        assertEquals(EtatPartie.ATTENTE_CIBLE_STOP, flip.etatPartie)
        assertEquals(EtatJoueur.JOUE_ENCORE, flip.etatJoueur[1])
        assertThrows<IndiceJoueurInvalideException> {
            flip.joueurCourantCibleStop(cartePioche,4)
        }
    }

    @Test
    fun CT3_joueurCourantCibleCarteStopJoueurValide(){
        val flip = creerJeu(listOf(CarteStop()),4)
        var cartePioche = flip.joueurCourantPiocheUneCarte()
        assertEquals(EtatPartie.ATTENTE_CIBLE_STOP, flip.etatPartie)
        assertEquals(EtatJoueur.JOUE_ENCORE, flip.etatJoueur[3])

        assertDoesNotThrow {
            flip.joueurCourantCibleStop(cartePioche,3)
        }
    }

    @Test
    fun CT4_joueurCourantCibleCarteStopJoueurNonActif(){
        val flip = creerJeu(listOf(CarteNum(1),CarteNum(1),CarteNum(1),CarteStop()),2)
        flip.joueurCourantPiocheUneCarte()
        flip.joueurCourantPiocheUneCarte()
        flip.joueurCourantPiocheUneCarte()

        assertEquals(EtatJoueur.PERDU,flip.etatJoueur[0])

        var carteStop = flip.joueurCourantPiocheUneCarte()
        assertEquals(EtatPartie.ATTENTE_CIBLE_STOP, flip.etatPartie)

        assertThrows<JoueurNonActifException> {
            flip.joueurCourantCibleStop(carteStop ,0)
        }
    }

    @Test
    fun CT5_joueurCourantCibleCarteStopCarteNum(){
        val flip = creerJeu(listOf(CarteStop()),2)
        var carteNum = CarteNum(1)
        var cartePioche = flip.joueurCourantPiocheUneCarte()

        assertThrows<CarteInvalideException> {
            flip.joueurCourantCibleStop(carteNum,1)
        }
    }
    @Test
    fun CT6_joueurCourantCibleCarteStopCarte2ndeChance(){
        val flip = creerJeu(listOf(CarteStop()),2)
        var carte = Carte2ndeChance()
        var cartePioche = flip.joueurCourantPiocheUneCarte()

        assertThrows<CarteInvalideException> {
            flip.joueurCourantCibleStop(carte,1)
        }
    }
    @Test
    fun CT1_joueurCourantCibleCarteStopCarte3aLaSuite(){
        val flip = creerJeu(listOf(CarteStop()),2)
        var carte = Carte3aLaSuite()
        var cartePioche = flip.joueurCourantPiocheUneCarte()

        assertThrows<CarteInvalideException> {
            flip.joueurCourantCibleStop(carte,1)
        }
    }
    @Test
    fun CT2_joueurCourantCibleCarteStopCarteBonusPlus(){
        val flip = creerJeu(listOf(CarteStop()),2)
        var carte = CarteBonusPlus(2)
        var cartePioche = flip.joueurCourantPiocheUneCarte()

        assertThrows<CarteInvalideException> {
            flip.joueurCourantCibleStop(carte,1)
        }
    }
    @Test
    fun CT3_joueurCourantCibleCarteStopCarteBonusMultiplie(){
        val flip = creerJeu(listOf(CarteStop()),2)
        var carte = CarteBonusMultiplie()
        var cartePioche = flip.joueurCourantPiocheUneCarte()

        assertThrows<CarteInvalideException> {
            flip.joueurCourantCibleStop(carte,1)
        }
    }


    @Test
    fun CT4_joueurCourantCibleCarteStopEtatAttentChoixJoueur(){
        val flip = creerJeu(listOf(CarteNum(1)),2)
        var cartePioche = flip.joueurCourantPiocheUneCarte()
        assertThrows<EtatPartieInvalideException> {
            flip.joueurCourantCibleStop(cartePioche,1)
        }
    }
    @Test
    fun CT5_joueurCourantCibleCarteStopEtatAttentCible3aLaSuite(){
        val flip = creerJeu(listOf(Carte3aLaSuite()),2)
        var cartePioche = flip.joueurCourantPiocheUneCarte()
        assertThrows<EtatPartieInvalideException> {
            flip.joueurCourantCibleStop(cartePioche,1)
        }
    }
    @Test
    fun CT6_joueurCourantCibleCarteStopMancheTermine(){
        val flip = creerJeu(listOf(CarteNum(1),CarteNum(1),CarteNum(1),CarteNum(1)),2)
        for (i in 0..3){
            flip.joueurCourantPiocheUneCarte()
        }

        assertThrows<EtatPartieInvalideException> {
            flip.joueurCourantCibleStop(flip.joueurCourantPiocheUneCarte(),1)
        }
    }

    @Test
    fun CT7_joueurCourantCibleCarteStopAjouteMainCible() {
        val flip = creerJeu(listOf(CarteStop()),2)
        val carte = flip.joueurCourantPiocheUneCarte()
        flip.joueurCourantCibleStop(carte, 1)
        assertEquals(0, flip.main[0]?.size)
        assertTrue(flip.main[1]!!.contains(carte))
        assertEquals(1, flip.main[1]?.size)
        assertEquals(0, flip.defausse.size)
    }

    //joueurCourantCible3aLaSuite
    @Test
    fun CT1_joueurCourantCible3aLaSuiteJoueurInvalideInferieur0(){
        val flip = creerJeu(listOf(Carte3aLaSuite()))
        var cartePioche = flip.joueurCourantPiocheUneCarte()

        assertEquals(EtatPartie.ATTENTE_CIBLE_3SUITE, flip.etatPartie)
        assertEquals(EtatJoueur.JOUE_ENCORE, flip.etatJoueur[1])

        assertThrows<IndiceJoueurInvalideException> {
            flip.joueurCourantCible3aLaSuite(cartePioche,-1)
        }

    }

    @Test
    fun CT2_joueurCourantCible3aLaSuiteJoueurInvalideSuperieur3(){
        val flip = creerJeu(listOf(Carte3aLaSuite()),4)
        var cartePioche = flip.joueurCourantPiocheUneCarte()
        assertEquals(EtatPartie.ATTENTE_CIBLE_3SUITE, flip.etatPartie)
        assertEquals(EtatJoueur.JOUE_ENCORE, flip.etatJoueur[1])
        assertThrows<IndiceJoueurInvalideException> {
            flip.joueurCourantCible3aLaSuite(cartePioche,4)
        }

    }

    @Test
    fun CT3_joueurCourantCible3aLaSuiteJoueurValide(){
        val flip = creerJeu(listOf(Carte3aLaSuite(), CarteNum(1),CarteNum(1),CarteNum(1)),4)
        var cartePioche = flip.joueurCourantPiocheUneCarte()
        assertEquals(EtatPartie.ATTENTE_CIBLE_3SUITE, flip.etatPartie)
        assertEquals(EtatJoueur.JOUE_ENCORE, flip.etatJoueur[3])

        assertDoesNotThrow {
            flip.joueurCourantCible3aLaSuite(cartePioche,3)
        }

    }

    @Test
    fun CT4_joueurCourantCible3aLaSuiteJoueurNonActif(){
        val flip = creerJeu(listOf(CarteNum(1),CarteNum(1),CarteNum(1), Carte3aLaSuite()),2)
        flip.joueurCourantPiocheUneCarte()
        flip.joueurCourantPiocheUneCarte()
        flip.joueurCourantPiocheUneCarte()

        assertEquals(EtatJoueur.PERDU,flip.etatJoueur[0])

        var carteStop = flip.joueurCourantPiocheUneCarte()
        assertEquals(EtatPartie.ATTENTE_CIBLE_3SUITE, flip.etatPartie)

        assertThrows<JoueurNonActifException> {
            flip.joueurCourantCible3aLaSuite(carteStop ,0)
        }

    }

    @Test
    fun CT5_joueurCourantCible3aLaSuiteCarteNum(){
        val flip = creerJeu(listOf(Carte3aLaSuite()),2)
        var carteNum = CarteNum(1)
        flip.joueurCourantPiocheUneCarte()

        assertThrows<CarteInvalideException> {
            flip.joueurCourantCible3aLaSuite(carteNum,1)
        }

    }
    @Test
    fun CT5_joueurCourantCible3aLaSuiteCarte2ndeChance(){
        val flip = creerJeu(listOf(Carte3aLaSuite()),2)
        var carte = Carte2ndeChance()
        flip.joueurCourantPiocheUneCarte()

        assertThrows<CarteInvalideException> {
            flip.joueurCourantCible3aLaSuite(carte,1)
        }
    }

    @Test
    fun CT5_joueurCourantCible3aLaSuiteCarteStop(){
        val flip = creerJeu(listOf(Carte3aLaSuite()),2)
        flip.joueurCourantPiocheUneCarte()
        var carte = CarteStop()

        assertThrows<CarteInvalideException> {
            flip.joueurCourantCible3aLaSuite(carte,1)
        }
    }

    @Test
    fun CT5_joueurCourantCible3aLaSuiteCarteBonusPlus(){
        val flip = creerJeu(listOf(Carte3aLaSuite()),2)
        var carte = CarteBonusPlus(2)
        flip.joueurCourantPiocheUneCarte()

        assertThrows<CarteInvalideException> {
            flip.joueurCourantCible3aLaSuite(carte,1)
        }
    }
    @Test
    fun CT5_joueurCourantCible3aLaSuiteCarteBonusMultiplie(){
        val flip = creerJeu(listOf(Carte3aLaSuite()),2)
        var carte = CarteBonusMultiplie()
        flip.joueurCourantPiocheUneCarte()

        assertThrows<CarteInvalideException> {
            flip.joueurCourantCible3aLaSuite(carte,1)
        }
    }


    @Test
    fun CT6_joueurCourantCible3aLaSuiteEtatAttentChoixJoueur(){
        val flip = creerJeu(listOf(CarteNum(1)),2)
        var cartePioche = flip.joueurCourantPiocheUneCarte()
        assertThrows<EtatPartieInvalideException> {
            flip.joueurCourantCible3aLaSuite(cartePioche,1)
        }

    }
    @Test
    fun CT6_joueurCourantCible3aLaSuiteEtatAttentCibleStop(){
        val flip = creerJeu(listOf(CarteStop()),2)
        var cartePioche = flip.joueurCourantPiocheUneCarte()
        assertThrows<EtatPartieInvalideException> {
            flip.joueurCourantCible3aLaSuite(cartePioche,1)
        }

    }
    @Test
    fun CT6_joueurCourantCible3aLaSuiteMancheTermine(){
        val flip = creerJeu(listOf(CarteNum(1),CarteNum(1),CarteNum(1),CarteNum(1)),2)
        for (i in 0..3){
            flip.joueurCourantPiocheUneCarte()
        }

        assertThrows<EtatPartieInvalideException> {
            flip.joueurCourantCible3aLaSuite(flip.joueurCourantPiocheUneCarte(),1)
        }


    }

    @Test
    fun CT7_joueurCourantCible3aLaSuiteAjouteMainCible() {
        val flip = creerJeu(listOf(Carte3aLaSuite(), CarteNum(0), CarteNum(1), CarteNum(2)), 2)
        val carte = flip.joueurCourantPiocheUneCarte()
        flip.joueurCourantCible3aLaSuite(carte, 1)
        assertEquals(0, flip.main[0]?.size)
        assertTrue(flip.main[1]!!.contains(carte))
        assertEquals(4, flip.main[1]?.size)
        assertEquals(0, flip.defausse.size)
    }

    //joueurCourantDitStop()
    @Test
    fun CT1_joueurCourantDitStopEtatValide(){
        var flip7 = creerJeu(listOf(CarteStop()),2)
        var joueur1 = flip7.joueurCourant

        flip7.joueurCourantDitStop()

        assertEquals(EtatJoueur.STOP,flip7.etatJoueur[0])
        assertTrue(joueur1 != flip7.joueurCourant)
        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, flip7.etatPartie)
    }

    @Test
    fun CT2_joueurCourantDitStopEtatAttenteCibleStop(){
        val flip7 = creerJeu(listOf(CarteStop()),2)
        flip7.joueurCourantPiocheUneCarte()

        assertThrows<EtatPartieInvalideException> {
            flip7.joueurCourantDitStop()
        }

    }

    @Test
    fun CT2_joueurCourantDitStopEtatAttente3aLaSuite(){
        val flip7 = creerJeu(listOf(Carte3aLaSuite()),2)
        flip7.joueurCourantPiocheUneCarte()

        assertThrows<EtatPartieInvalideException> {
            flip7.joueurCourantDitStop()
        }

    }

    //NouvelleManche
    @Test
    fun CT1_nouvelleMancheValide(){
        val flip7 = creerJeu(listOf(Carte3aLaSuite()),2)
        flip7.joueurCourantDitStop()
        flip7.joueurCourantDitStop()

        var joueurAvantNouvelleManche = flip7.joueurCourant

        flip7.scoreManche()
        assertEquals(EtatPartie.NOUVELLE_MANCHE,flip7.etatPartie)

        flip7.nouvelleManche()
        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR,flip7.etatPartie)
        assertTrue(flip7.joueurCourant == joueurAvantNouvelleManche-1)

        var toutesVides = true
        for (i in 0..flip7.nbJoueurs) {
            if (!flip7.main[i].isNullOrEmpty()) {
                toutesVides = false
                break
            }
        }
        assertTrue(toutesVides)

    }

    @Test
    fun CT2_nouvelleManchePremierJoueur0(){
        val flip7 = creerJeu(listOf(Carte3aLaSuite()),4)

        flip7.joueurCourantDitStop()
        flip7.joueurCourantDitStop()
        flip7.joueurCourantDitStop()
        flip7.joueurCourantDitStop()


        flip7.scoreManche()
        assertEquals(EtatPartie.NOUVELLE_MANCHE,flip7.etatPartie)

        flip7.nouvelleManche()
        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR,flip7.etatPartie)
        assertTrue(flip7.joueurCourant == 0)

        var touteJoueEncore= true
        for (i in 0 until flip7.nbJoueurs) {
            if (flip7.etatJoueur[i] != EtatJoueur.JOUE_ENCORE) {
                touteJoueEncore = false
                break
            }
        }
        assertTrue(touteJoueEncore)

            var toutesVides = true
        for (i in 0 until flip7.nbJoueurs) {
            if (!flip7.main[i].isNullOrEmpty()) {
                toutesVides = false
                break
            }
        }
        assertTrue(toutesVides)

    }

    @Test
    fun CT3_nouvelleMancheMainJoueurVide(){
        val flip7 = creerJeu(listOf(CarteNum(1)),4)

        flip7.joueurCourantPiocheUneCarte()
        flip7.joueurCourantDitStop()
        flip7.joueurCourantDitStop()
        flip7.joueurCourantDitStop()
        assertTrue(flip7.joueurCourant == 0)
        flip7.joueurCourantDitStop()

        assertTrue(flip7.main[0]!!.isNotEmpty())
        flip7.scoreManche()
        assertEquals(EtatPartie.NOUVELLE_MANCHE,flip7.etatPartie)

        flip7.nouvelleManche()
        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR,flip7.etatPartie)

        var toutesVides = true
        for (i in 0 until flip7.nbJoueurs) {
            if (!flip7.main[i].isNullOrEmpty()) {
                toutesVides = false
                break
            }
        }
        assertTrue(toutesVides)

        var touteJoueEncore= true
        for (i in 0 until flip7.nbJoueurs) {
            if (flip7.etatJoueur[i] != EtatJoueur.JOUE_ENCORE) {
                touteJoueEncore = false
                break
            }
        }
        assertTrue(touteJoueEncore)

    }

    @Test
    fun CT4_nouvelleMancheMainJoueurVideEtPremierJoueur0(){
        val flip7 = creerJeu(listOf(CarteNum(1)),4)

        flip7.joueurCourantPiocheUneCarte()
        flip7.joueurCourantDitStop()
        flip7.joueurCourantDitStop()
        flip7.joueurCourantDitStop()

        assertTrue(flip7.joueurCourant == 0)
        flip7.joueurCourantDitStop()


        flip7.scoreManche()
        assertEquals(EtatPartie.NOUVELLE_MANCHE,flip7.etatPartie)

        flip7.nouvelleManche()
        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR,flip7.etatPartie)

        var touteJoueEncore= true
        for (i in 0 until flip7.nbJoueurs) {
            if (flip7.etatJoueur[i] != EtatJoueur.JOUE_ENCORE) {
                touteJoueEncore = false
                break
            }
        }
        assertTrue(touteJoueEncore)

        var toutesVides = true
        for (i in 0 until flip7.nbJoueurs) {
            if (!flip7.main[i].isNullOrEmpty()) {
                toutesVides = false
                break
            }
        }
        assertTrue(toutesVides)
    }
    @Test
    fun CT5_nouvelleMancheAutreEtat(){
        val flip7 = creerJeu(listOf(CarteNum(1)),4)

        flip7.joueurCourantPiocheUneCarte()
        flip7.joueurCourantDitStop()
        flip7.joueurCourantDitStop()
        flip7.joueurCourantDitStop()

        assertTrue(flip7.joueurCourant == 0)
        flip7.joueurCourantDitStop()


        flip7.scoreManche()
        assertEquals(EtatPartie.NOUVELLE_MANCHE,flip7.etatPartie)

        flip7.nouvelleManche()
        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR,flip7.etatPartie)

        var touteJoueEncore= true
        for (i in 0 until flip7.nbJoueurs) {
            if (flip7.etatJoueur[i] != EtatJoueur.JOUE_ENCORE) {
                touteJoueEncore = false
                break
            }
        }
        assertTrue(touteJoueEncore)

        var toutesVides = true
        for (i in 0 until flip7.nbJoueurs) {
            if (!flip7.main[i].isNullOrEmpty()) {
                toutesVides = false
                break
            }
        }
        assertTrue(toutesVides)
    }

    // Score Manche
    @Test
    fun CT1_ScoreMancheScoreAtteintQueCarteNum(){
        var cartes =listOf(
            CarteNum(12),CarteNum(1),
            CarteNum(11), CarteNum(2),
            CarteNum(10), CarteNum(3),
            CarteNum(9),CarteNum(4),
            CarteNum(8),CarteNum(5),
            CarteNum(7),CarteNum(6),
            CarteNum(6))

        var flip = creerJeu(cartes, 2,50)

        for (i in 0 until cartes.size){
            flip.joueurCourantPiocheUneCarte()
        }

        assertEquals(EtatPartie.MANCHE_TERMINEE, flip.etatPartie) // renvoie true

        flip.scoreManche()
        assertEquals(EtatPartie.PARTIE_TERMINEE, flip.etatPartie)

        var deckJoueur1 = flip.main[0]
        var scoreJoueur1 = deckJoueur1!!.sumOf { it.valeur }
        assertEquals(scoreJoueur1+15, flip.score[0])

    }
    @Test
    fun CT2_ScoreMancheScoreAtteintCarteBonusMultiplie() {

        var cartes = listOf(
            CarteBonusMultiplie(), Carte2ndeChance(),
            CarteNum(2),CarteNum(2),
            CarteNum(3),CarteNum(3),
            CarteNum(4),CarteNum(4),
            CarteNum(5),CarteNum(5),
            CarteNum(6),CarteNum(6),
            CarteNum(7),CarteNum(7),
            CarteNum(8)

        )
        var flip = creerJeu(cartes, 2, 50)

        for (i in 0 until cartes.size) {
            flip.joueurCourantPiocheUneCarte()
        }
        assertEquals(EtatPartie.MANCHE_TERMINEE, flip.etatPartie)

        flip.scoreManche()
        assertEquals(EtatPartie.PARTIE_TERMINEE, flip.etatPartie)

        var outilsCarte = OutilsCarte()
        var deckJoueur1 = flip.main[0]
        var scoreJoueur1 =  outilsCarte.calculScore(deckJoueur1!!)
        // bonus x2 appliqué + bonus Flip7 +15
        assertEquals(scoreJoueur1, flip.score[0])
    }

    @Test
    fun CT3_ScoreMancheScoreAtteintCarteBonusPlus() {
        var cartes = listOf(
            CarteBonusPlus(2), Carte2ndeChance(),
            CarteNum(12),CarteNum(2),
            CarteNum(11),CarteNum(3),
            CarteNum(10),CarteNum(4),
            CarteNum(9),CarteNum(5),
            CarteNum(8),CarteNum(6),
            CarteNum(7),CarteNum(7),
            CarteNum(6)
        )
        var flip = creerJeu(cartes, 2, 50)

        for (i in 0 until cartes.size) {
            flip.joueurCourantPiocheUneCarte()
        }

        assertEquals(EtatPartie.MANCHE_TERMINEE, flip.etatPartie)

        flip.scoreManche()
        assertEquals(EtatPartie.PARTIE_TERMINEE, flip.etatPartie)

        var outilsCarte = OutilsCarte()
        var deckJoueur1 = flip.main[0]
        var scoreJoueur1 =  outilsCarte.calculScore(deckJoueur1!!)
        // bonus x2 appliqué + bonus Flip7 +15
        assertEquals(scoreJoueur1, flip.score[0])
    }

    @Test
    fun CT4_ScoreMancheScoreAtteintExactement7CarteNum() {
        var cartes = listOf(
            CarteNum(10), CarteNum(1),
            CarteNum(9), CarteNum(2),
            CarteNum(8), CarteNum(3),
            CarteNum(7), CarteNum(4),
            CarteNum(6), CarteNum(5),
            CarteNum(5), CarteNum(6),
            CarteNum(11)
        )
        var flip = creerJeu(cartes, 2, 50)

        for (i in 0 until cartes.size) {
            flip.joueurCourantPiocheUneCarte()
        }

        assertEquals(EtatPartie.MANCHE_TERMINEE, flip.etatPartie)

        flip.scoreManche()
        assertEquals(EtatPartie.PARTIE_TERMINEE, flip.etatPartie)

        var deckJoueur1 = flip.main[0]
        var scoreJoueur1 = deckJoueur1!!.sumOf { it.valeur }
        assertEquals(scoreJoueur1 + 15, flip.score[0])
    }

    @Test
    fun CT5_ScoreMancheScoreNonAtteint() {
        var cartes = listOf(
            CarteNum(1), CarteNum(1),
            CarteNum(2), CarteNum(2),
            CarteNum(3), CarteNum(3),
            CarteNum(4), CarteNum(4),
            CarteNum(5), CarteNum(5),
            CarteNum(6), CarteNum(6),
            CarteNum(7)
        )
        var flip = creerJeu(cartes, 2, 200)

        for (i in 0 until cartes.size) {
            flip.joueurCourantPiocheUneCarte()
        }

        assertEquals(EtatPartie.MANCHE_TERMINEE, flip.etatPartie)

        flip.scoreManche()

        var deckJoueur1 = flip.main[0]
        var scoreJoueur1 = deckJoueur1!!.sumOf { it.valeur }
        assertEquals(scoreJoueur1 + 15, flip.score[0])

        assertTrue(EtatPartie.PARTIE_TERMINEE != flip.etatPartie)
        flip.nouvelleManche()

        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, flip.etatPartie)
    }

    @Test
    fun CT6_ScoreMancheEtatInvalide() {
        var flip = creerJeu(listOf(CarteNum(5), CarteNum(3)), 2, 50)

        assertThrows<EtatPartieInvalideException> {
            flip.scoreManche()
        }
    }

    //CONSTRUCTEUR

    fun piocheValide(): List<Carte> {
        val pioche = mutableListOf<Carte>()
        pioche.add(CarteNum(0))
        for (i in 1..12) {
            for (j in 1..i){
                pioche.add(CarteNum(i))
            }
        }

        for (i in 1 .. 5){
            pioche.add(CarteBonusPlus(i*2))
        }
        pioche.add(CarteBonusMultiplie())
        for(i in 0 until 3){
            pioche.add(CarteStop())
            pioche.add(Carte2ndeChance())
            pioche.add(Carte3aLaSuite())
        }

        return pioche.shuffled()
    }

    @Test
    fun CT1_CreerJeuValide_2Joueurs_Score50() {
        var flip = creerJeu(listOf(CarteNum(1)), 2, 50)

        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, flip.etatPartie)
        assertEquals(0, flip.joueurCourant)
        assertEquals(2, flip.joueurs.size)
        assertEquals(0, flip.joueurCourant)

        for(i in 0 until flip.joueurs.size) {
            assertEquals(EtatJoueur.JOUE_ENCORE, flip.etatJoueur[i])
            assertEquals(listOf<Carte>(), flip.main[i])
            assertEquals(0, flip.score[i])
        }
    }


    @Test
    fun CT1b_CreerJeuValide_3Joueurs_Score200() {
        var flip = creerJeu(piocheValide(), 3, 200)

        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, flip.etatPartie)
        assertEquals(0, flip.joueurCourant)
        assertEquals(3, flip.joueurs.size)

        for(i in 0 until flip.joueurs.size) {
            assertEquals(EtatJoueur.JOUE_ENCORE, flip.etatJoueur[i])
            assertEquals(listOf<Carte>(), flip.main[i])
            assertEquals(0, flip.score[i])
        }
    }

    @Test
    fun CT1b_CreerJeuValide_4Joueurs_Score200() {
        var flip = creerJeu(piocheValide(), 4, 200)

        assertEquals(EtatPartie.ATTENTE_CHOIX_JOUEUR, flip.etatPartie)
        assertEquals(0, flip.joueurCourant)
        assertEquals(4, flip.joueurs.size)

        for(i in 0 until flip.joueurs.size) {
            assertEquals(EtatJoueur.JOUE_ENCORE, flip.etatJoueur[i])
            assertEquals(listOf<Carte>(), flip.main[i])
            assertEquals(0, flip.score[i])
        }
    }

    @Test
    fun CT2_CreerJeu_ScoreFinPartie0() {
        assertThrows<ScoreFinPartieInvalideException> {
            creerJeu(piocheValide(), 2, 0)
        }
    }

    @Test
    fun CT2_CreerJeu_ScoreFinPartie49() {
        assertThrows<ScoreFinPartieInvalideException> {
            creerJeu(piocheValide(), 4, 49)
        }
    }

    @Test
    fun CT3_CreerJeu_ScoreFinPartieNegatif() {
        assertThrows<ScoreFinPartieInvalideException> {
            creerJeu(piocheValide(), 2, -1)
        }
    }
    @Test
    fun CT3_CreerJeu_ScoreFinPartieMinValue() {
        assertThrows< ScoreFinPartieInvalideException> {
            creerJeu(piocheValide(), 2, Int.MIN_VALUE)
        }
    }

    @Test
    fun CT4_CreerJeu_ScoreFinPartie201_Invalide() {
        assertThrows<ScoreFinPartieInvalideException> {
            creerJeu(piocheValide(), 2, 201)
        }
    }

    @Test
    fun CT4_CreerJeu_ScoreFinPartieMaxValue_Valide() {
        assertThrows<ScoreFinPartieInvalideException> {
            creerJeu(piocheValide(), 2, Int.MIN_VALUE)
        }
    }

    @ParameterizedTest
    @MethodSource("Test")
    fun CreerJeu_NbJoueurInvalide_Exception(nbJoueurs: Int,listeJoueurs: List<IJoueur>) {
        assertThrows<NombreJoueursInvalideException> {
            Flip7(nbJoueurs,listeJoueurs,piocheValide())
        }
    }

    companion object {
        @JvmStatic
        fun Test(): Stream<Arguments?>? {
            val j1 = Joueur("j1")
            val j2 = Joueur("j2")
            val list2Joueur = listOf<IJoueur>(j1,j2)
            return Stream.of(
                Arguments.of(1, list2Joueur),
                Arguments.of(5, list2Joueur),
                Arguments.of(6, list2Joueur),
                Arguments.of(7, list2Joueur),
                Arguments.of(8, list2Joueur),
                Arguments.of(9, list2Joueur),
                Arguments.of(10, list2Joueur),
                Arguments.of(Int.MIN_VALUE, list2Joueur),
                Arguments.of(Int.MAX_VALUE, list2Joueur),
            )
        }

    }

    @Test
    fun CT10_CreerJeu_PiocheVide_Exception() {
        assertThrows<PiocheInvalideException> {
            creerJeu(emptyList(), 2, 50)
        }
    }

    @Test
    fun CT_RoundRobinRot(){
        val flip = creerJeu(piocheValide(),4)
        println(flip.joueurCourant)
        for (i in 0..5){

            assertEquals(i%flip.nbJoueurs, flip.joueurCourant)
            repeat(4){
                flip.joueurCourantDitStop()
            }
            flip.scoreManche()
            flip.nouvelleManche()
        }
    }
}

class TestListeJoueurInvalide{
    fun piocheValide(): List<Carte> {
        val pioche = mutableListOf<Carte>()
        for (i in 1..12) {
            for (i in 0 until 6){
                pioche.add(CarteNum(i))
                pioche.add(CarteNum(i))
                pioche.add(CarteNum(i))
            }
        }

        for (i in 1 .. 5){
            pioche.add(CarteBonusPlus(i*2))
        }
        pioche.add(CarteBonusMultiplie())

        for(i in 0 until 3){
            pioche.add(CarteStop())
            pioche.add(Carte2ndeChance())
            pioche.add(Carte3aLaSuite())
        }

        return pioche.shuffled()
    }

    @ParameterizedTest
    @MethodSource("Test")
    fun CreerJeu_ListeJoueur_Exception(nbJoueurs: Int,listeJoueurs: List<IJoueur>) {
        assertThrows<ListeJoueursInvalideException> {
            Flip7(nbJoueurs,listeJoueurs,piocheValide())
        }
    }

    companion object {
        @JvmStatic
        fun Test(): Stream<Arguments?>? {
            val j1 = Joueur("j1")
            val list1Joueur = listOf<IJoueur>(j1)
            return Stream.of(
                Arguments.of(2, list1Joueur),
                Arguments.of(3, list1Joueur),
                Arguments.of(4, list1Joueur),
                )
        }

    }

}
package cartes

import iut.info1.flip7.IJoueur
import iut.info1.flip7.cartes.Carte
import iut.info1.flip7.cartes.Carte2ndeChance
import iut.info1.flip7.cartes.Carte3aLaSuite
import iut.info1.flip7.cartes.CarteBonusMultiplie
import iut.info1.flip7.cartes.CarteBonusPlus
import iut.info1.flip7.cartes.CarteNum
import iut.info1.flip7.cartes.CarteStop
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream


class TestCarte2ndeChance {

    private lateinit var joueur1: IJoueur
    private lateinit var joueur2: IJoueur
    private lateinit var listeJoueurs: List<IJoueur>

    @Test
    fun CT0_constructeur(){
        assertDoesNotThrow {
            Carte2ndeChance()
        }
    }

    @Test
    fun CT1_Valeur() {
        val carte = Carte2ndeChance()
        assertEquals(0, carte.valeur)
    }


    @Test
    fun CT1_estCarte2ndChance(){
        val carte = Carte2ndeChance()
        assertTrue(carte.estCarte2ndeChance(), "CT1 Échoué : il faut True.")
    }


    @ParameterizedTest
    @MethodSource("carteBonusProvider")
    fun CT2_testEstCarte2ndeChance(carte: Carte, expected: Boolean) {
        assertEquals(expected, carte.estCarte2ndeChance())
    }

    companion object {
        @JvmStatic
        fun carteBonusProvider(): Stream<Arguments> = Stream.of(
            Arguments.of(CarteNum(4), false),
            Arguments.of(Carte3aLaSuite(), false),
            Arguments.of(CarteStop(), false),
            Arguments.of(CarteBonusMultiplie(), false),
            Arguments.of(CarteBonusPlus(2), false)
        )
    }


    @Test
    fun CT1_equals(){
        val carte1 = Carte2ndeChance()
        var carte2 = carte1
        assertTrue(carte1 == carte2)
    }

    @Test
    fun CT2_equals(){
        val carte1 = Carte2ndeChance()
        var carte2 = null
        assertTrue(carte1 != carte2)
    }

    @Test
    fun CT3_equals(){
        val carte1 = Carte2ndeChance()
        var carte2 = Carte2ndeChance()
        assertTrue(carte1 == carte2)
    }


    @Test
    fun CT1_toString() {
        val carte = Carte2ndeChance()
        assertEquals("[Carte 2nde chance]"  , carte.toString(), "CT1 Échoué : Le texte renvoyé n'est pas le bon.")
    }

}


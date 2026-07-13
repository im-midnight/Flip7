package cartes

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

class TestCarteBonusMultiplie {

    @Test
    fun CT0_constructeur(){
        assertDoesNotThrow {
            CarteBonusMultiplie()
        }
    }

    @Test
    fun CT1_Valeur() {
        val carte = CarteBonusMultiplie()
        assertEquals(2, carte.valeur)
    }

    @Test
    fun CT1_estCarteBonusMultiplie(){
        val carte = CarteBonusMultiplie()
        assertTrue(carte.estCarteBonusMultiplie(), "CT1 Échoué : il faut True.")
    }

    @ParameterizedTest
    @MethodSource("carteBonusProvider")
    fun testEstCarteBonusMultiplie(carte: Carte, expected: Boolean) {
        assertEquals(expected, carte.estCarteBonusMultiplie())
    }

    companion object {
        @JvmStatic
        fun carteBonusProvider(): Stream<Arguments> = Stream.of(
            Arguments.of(CarteNum(4), false),
            Arguments.of(Carte3aLaSuite(), false),
            Arguments.of(CarteStop(), false),
            Arguments.of(Carte2ndeChance(), false),
            Arguments.of(CarteBonusPlus(2), false)
        )
    }

    @Test
    fun CT1_equals(){
        val carte1 = CarteBonusMultiplie()
        var carte2 = carte1
        assertTrue(carte1 == carte2)
    }

    @Test
    fun CT2_equals(){
        val carte1 = CarteBonusMultiplie()
        var carte2 = null
        assertTrue(carte1 != carte2)
    }

    @Test
    fun CT3_equals(){
        val carte1 = CarteBonusMultiplie()
        var carte2 = CarteBonusMultiplie()
        assertTrue(carte1 == carte2)
    }


    @Test
    fun CT1_toString() {
        val carte = CarteBonusMultiplie()
        assertEquals("[Carte Bonus x2]"  , carte.toString(), "CT1 Échoué : Le texte renvoyé n'est pas le bon.")
    }
}
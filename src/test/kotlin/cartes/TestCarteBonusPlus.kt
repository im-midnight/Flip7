package cartes

import iut.info1.flip7.cartes.Carte2ndeChance
import iut.info1.flip7.cartes.CarteBonusPlus
import iut.info1.flip7.cartes.CarteNum
import iut.info1.flip7.cartes.CarteStop
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class TestCarteBonusPlus {
    val carte = CarteBonusPlus(4)

    @Test
    fun CT0_constructeur(){
        assertDoesNotThrow {
            CarteBonusPlus(2)
        }
    }

    @Test
    fun CT1_CreateInvalideInferieur() {
        assertThrows<IllegalArgumentException> {
            CarteBonusPlus(0)
        }
    }

    @ParameterizedTest
    @CsvSource(
        "2, 2",
        "4, 4",
        "6, 6",
        "8, 8",
        "10, 10"
    )
    fun CT2_CreateValide(dt: Int, oracle: Int) {
        val carte = CarteBonusPlus(dt)
        assertEquals(oracle, carte.valeur)
    }

    @ParameterizedTest
    @CsvSource("0",
        "1",
        "3",
        "5",
        "7",
        "9",
        "11"
    )
    fun CT3_CreateInvalideBetween(dt: Int) {
        assertThrows<IllegalArgumentException> {
            CarteBonusPlus(dt)
        }
    }

    @Test
    fun CT4_CreateInvalideSuperieur() {
        assertThrows<IllegalArgumentException> {
            CarteBonusPlus(12)
        }
    }

    @Test
    fun CT5_EstCarteNum() {
        assertFalse(carte.estCarteNum())
    }

    @Test
    fun CT6_EstCarteBonusPlus() {
        assertTrue(carte.estCarteBonusPlus())
    }

    @Test
    fun CT7_EstCarteBonusMultiple() {
        assertFalse(carte.estCarteBonusMultiplie())
    }

    @Test
    fun CT8_EstCarteStop() {
        assertFalse(carte.estCarteStop())
    }

    @Test
    fun CT9_EstCarte2ndeChance() {
        assertFalse(carte.estCarte2ndeChance())
    }

    @Test
    fun CT10_EstCarte3ALaSuite() {
        assertFalse(carte.estCarte3aLaSuite())
    }

    @Test
    fun CT11_EqualsMemeInstance() {
        assertTrue(carte.equals(carte))
    }

    @Test
    fun CT12_EqualsMemeTypeEtValeur() {
        assertTrue(carte == CarteBonusPlus(4))
    }

    @Test
    fun CT13_EqualsMemeTypeDiffValeur() {
        assertFalse(carte == CarteBonusPlus(2))
    }

    @Test
    fun CT14_EqualsNull() {
        assertFalse(carte.equals(null))
    }

    @Test
    fun CT15_EqualsTypeDifferent() {
        assertFalse(carte == CarteStop())
    }

    @Test
    fun CT16_toString() {
        assertEquals("[Carte Bonus +4]", carte.toString())
    }
}
package cartes

import iut.info1.flip7.cartes.Carte2ndeChance
import iut.info1.flip7.cartes.CarteNum
import iut.info1.flip7.cartes.CarteStop
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class TestCarteNum {

    private val carte = CarteNum(0)

    @Test
    fun CT0_constructeur(){
        assertDoesNotThrow {
            CarteNum(0)
        }
    }

    @Test
    fun CT1_CreateInvalideInferieure() {
        assertThrows<IllegalArgumentException> {
            CarteNum(-1)
        }
    }

    @ParameterizedTest
    @CsvSource(
        "0, 0",
        "1, 1",
        "2, 2",
        "3, 3",
        "4, 4",
        "5, 5",
        "6, 6",
        "7, 7",
        "8, 8",
        "9, 9",
        "10, 10",
        "11, 11",
        "12, 12"
    )
    fun CT2_CreateValide(dt: Int, oracle: Int) {
        val carte = CarteNum(dt)
        assertEquals(oracle, carte.valeur)
    }

    @Test
    fun CT3_Create13() {
        assertThrows<IllegalArgumentException> {
            CarteNum(13)
        }
    }

    @Test
    fun CT3_CreateNegative() {
        assertThrows<IllegalArgumentException> {
            CarteNum(-1)
        }
    }

    @Test
    fun CT4_EstCarteNum() {
        assertEquals(true, carte.estCarteNum())
    }

    @Test
    fun CT5_EstCarteBonusPlus() {
        assertEquals(false, carte.estCarteBonusPlus())
    }

    @Test
    fun CT6_EstCarteBonusMultiple() {
        assertEquals(false, carte.estCarteBonusMultiplie())
    }

    @Test
    fun CT7_EstCarteStop() {
        assertEquals(false, carte.estCarteStop())
    }

    @Test
    fun CT8_EstCarte2ndeChance() {
        assertEquals(false, carte.estCarte2ndeChance())
    }

    @Test
    fun CT9_EstCarte3ALaSuite() {
        assertEquals(false, carte.estCarte3aLaSuite())
    }

    @Test
    fun CT10_EqualsTypeDifferent() {
        assertEquals(false, carte == CarteStop())
    }

    @Test
    fun CT11_EqualsValeurDifferente() {
        assertEquals(false, carte == CarteNum(12))
    }

    @Test
    fun CT12_EqualsMemeValeur() {
        assertEquals(true, carte == CarteNum(0))
    }

    @Test
    fun CT13_toString() {
        assertEquals("[Carte n°0]", carte.toString())
    }
}
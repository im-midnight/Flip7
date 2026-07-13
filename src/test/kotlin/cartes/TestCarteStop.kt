package cartes

import iut.info1.flip7.cartes.Carte2ndeChance
import iut.info1.flip7.cartes.CarteNum
import iut.info1.flip7.cartes.CarteStop
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class TestCarteStop {

    val carte = CarteStop()

    @Test
    fun CT0_constructeur(){
        assertDoesNotThrow {
            CarteStop()
        }
    }

    @Test
    fun CT1_CreateCarteStop() {
        assertEquals(0, carte.valeur)
    }

    @Test
    fun CT2_EstCarteNum() {
        assertEquals(false, carte.estCarteNum())
    }

    @Test
    fun CT3_EstCarteBonusPlus() {
        assertEquals(false, carte.estCarteBonusPlus())
    }

    @Test
    fun CT4_EstCarteBonusMultiple() {
        assertEquals(false, carte.estCarteBonusMultiplie())
    }

    @Test
    fun CT5_EstCarteStop() {
        assertEquals(true, carte.estCarteStop())
    }

    @Test
    fun CT6_EstCarte2ndeChance() {
        assertEquals(false, carte.estCarte2ndeChance())
    }

    @Test
    fun CT7_EstCarte3ALaSuite() {
        assertEquals(false, carte.estCarte3aLaSuite())
    }

    @Test
    fun CT8_EqualsTypeDifferent() {
        assertEquals(false, carte == Carte2ndeChance())
    }

    @Test
    fun CT9_MemeType() {
        assertEquals(true, carte == CarteStop())
    }

    @Test
    fun CT10_toString() {
        assertEquals("[Carte Stop]", carte.toString())
    }
}
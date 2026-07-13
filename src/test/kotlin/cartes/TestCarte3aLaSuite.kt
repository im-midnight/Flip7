package cartes

import iut.info1.flip7.cartes.Carte2ndeChance
import iut.info1.flip7.cartes.Carte3aLaSuite
import iut.info1.flip7.cartes.CarteStop
import iut.info1.flip7.exceptions.CarteInvalideException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class TestCarte3aLaSuite {

    val carte = Carte3aLaSuite()

    @Test
    fun CT0_constructeur(){
        assertDoesNotThrow {
            Carte3aLaSuite()
        }
    }

    @Test
    fun CT1_Valeur() {
        assertEquals(0, carte.valeur)
    }

    @Test
    fun CT2_EstCarteNum() {
        assertFalse(carte.estCarteNum())
    }

    @Test
    fun CT3_EstCarteBonusPlus() {
        assertFalse(carte.estCarteBonusPlus())
    }

    @Test
    fun CT4_EstCarteBonusMultiple() {
        assertFalse(carte.estCarteBonusMultiplie())
    }

    @Test
    fun CT5_EstCarteStop() {
        assertFalse(carte.estCarteStop())
    }

    @Test
    fun CT6_EstCarte2ndeChance() {
        assertFalse(carte.estCarte2ndeChance())
    }

    @Test
    fun CT7_EstCarte3ALaSuite() {
        assertTrue(carte.estCarte3aLaSuite())
    }

    @Test
    fun CT8_EqualsMemeInstance() {
        assertTrue(carte.equals(carte))
    }

    @Test
    fun CT9_EqualsMemeType() {
        assertTrue(carte == Carte3aLaSuite())
    }

    @Test
    fun CT10_EqualsNull() {
        assertFalse(carte.equals(null))
    }

    @Test
    fun CT11_EqualsTypeDifferent() {
        assertFalse(carte == CarteStop())
    }

    @Test
    fun CT12_toString() {
        assertEquals("[Carte 3 à la suite]", carte.toString())
    }
}

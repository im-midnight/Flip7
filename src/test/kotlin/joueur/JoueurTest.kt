package joueur

import model.Joueur
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class TestJoueur {

    @Test
    fun CT0_constructeur() {
        assertDoesNotThrow {
            Joueur("test")
        }
    }

    @Test
    fun CT1_donneNom() {
        val joueur = Joueur("test")

        assertEquals("test",joueur.donneNom())
    }

}
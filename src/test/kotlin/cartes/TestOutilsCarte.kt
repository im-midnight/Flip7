package cartes

import iut.info1.flip7.cartes.*
import iut.info1.flip7.exceptions.MainInvalideException
import iut.info1.flip7.exceptions.PiocheInvalideException
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class TestOutilsCarte {

    val o = OutilsCarte()

    @Test
    fun CT0_Constructeur(){
        assertDoesNotThrow {
            OutilsCarte()
        }
    }

    @Test
    fun CT1_estFlip7() {
        assertTrue(
            o.estFlip7((0..6).map { CarteNum(it) })
        )
    }

    @Test
    fun CT2_estFlip7() {
        assertFalse(
            o.estFlip7(listOf())
        )
    }

    @Test
    fun CT3_estFlip7() {
        assertFalse(
            o.estFlip7(listOf(CarteNum(2)) + (2..7).map { CarteNum(it) })
        )
    }

    @Test
    fun CT4_estFlip7() {
        assertThrows<MainInvalideException> {
            o.estFlip7((0..7).map { CarteNum(it) })
        }
    }

}

class TestOutilsCarte_verfiePiocheInitiale {

    val o = OutilsCarte()

    @Test
    fun CT1_verifiePiocheInitiale(){
        assertDoesNotThrow {
            o.verifiePiocheInitiale(listOf(CarteNum(0), CarteBonusMultiplie()) + (1..12).flatMap {num -> List(num) { CarteNum(num) }} +
                    (1..5).map { CarteBonusPlus(it*2) } + List(3) { Carte2ndeChance() } +
                    List(3) { Carte3aLaSuite() } + List(3) { CarteStop() }
            )
        }
    }

    @ParameterizedTest
    @MethodSource("argumentsProvider")
    fun CT2a14_verifiePiocheInitiale(pioche : List<Carte>){
        assertThrows<PiocheInvalideException> {
            o.verifiePiocheInitiale(pioche)
        }
    }

    companion object {
        @JvmStatic
        fun argumentsProvider(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.of(listOf(CarteBonusMultiplie()) + (1..12).flatMap {num -> List(num) { CarteNum(num) }} +
                        (1..5).map { CarteBonusPlus(it*2) } + List(3) { Carte2ndeChance() } +
                        List(3) { Carte3aLaSuite() } + List(3) { CarteStop() }
                ),
                Arguments.of( listOf(CarteNum(0),CarteNum(0), CarteBonusMultiplie()) + (1..12).flatMap {num -> List(num) { CarteNum(num) }} +
                        (1..5).map { CarteBonusPlus(it*2) } + List(3) { Carte2ndeChance() } +
                        List(3) { Carte3aLaSuite() } + List(3) { CarteStop() }
                ),
                Arguments.of(listOf(CarteNum(0),CarteNum(12), CarteBonusMultiplie()) + (2..12).flatMap {num -> List(num) { CarteNum(num) }} +
                        (1..5).map { CarteBonusPlus(it*2) } + List(3) { Carte2ndeChance() } +
                        List(3) { Carte3aLaSuite() } + List(3) { CarteStop() }
                ),
                Arguments.of(listOf(CarteNum(0)) + (1..12).flatMap {num -> List(num) { CarteNum(num) }} +
                        (1..5).map { CarteBonusPlus(it*2) } + List(3) { Carte2ndeChance() } +
                        List(3) { Carte3aLaSuite() } + List(4) { CarteStop() }
                ),
                Arguments.of(listOf(CarteNum(0), CarteBonusMultiplie(), CarteBonusMultiplie()) + (1..12).flatMap {num -> List(num) { CarteNum(num) }} +
                        (1..5).map { CarteBonusPlus(it*2) } + List(3) { Carte2ndeChance() } +
                        List(3) { Carte3aLaSuite() } + List(2) { CarteStop() }
                ),
                Arguments.of(listOf(CarteNum(0), CarteBonusMultiplie()) + (1..12).flatMap {num -> List(num) { CarteNum(num) }} +
                        (1..4).map { CarteBonusPlus(it*2) } + List(4) { Carte2ndeChance() } +
                        List(3) { Carte3aLaSuite() } + List(3) { CarteStop() }
                ),
                Arguments.of(listOf(CarteNum(0), CarteBonusMultiplie(),CarteBonusPlus(8)) + (1..12).flatMap {num -> List(num) { CarteNum(num) }} +
                        (1..4).map { CarteBonusPlus(it*2) } + List(3) { Carte2ndeChance() } +
                        List(3) { Carte3aLaSuite() } + List(3) { CarteStop() }
                ),
                Arguments.of(listOf(CarteNum(0), CarteBonusMultiplie()) + (1..12).flatMap {num -> List(num) { CarteNum(num) }} +
                        (1..5).map { CarteBonusPlus(it*2) } + List(2) { Carte2ndeChance() } +
                        List(3) { Carte3aLaSuite() } + List(3) { CarteStop() }
                ),
                Arguments.of(listOf(CarteNum(0), CarteBonusMultiplie()) + (1..12).flatMap {num -> List(num) { CarteNum(num) }} +
                        (1..5).map { CarteBonusPlus(it*2) } + List(4) { Carte2ndeChance() } +
                        List(3) { Carte3aLaSuite() } + List(2) { CarteStop() }
                ),
                Arguments.of(listOf(CarteNum(0), CarteBonusMultiplie()) + (1..12).flatMap {num -> List(num) { CarteNum(num) }} +
                        (1..5).map { CarteBonusPlus(it*2) } + List(3) { Carte2ndeChance() } +
                        List(2) { Carte3aLaSuite() } + List(4) { CarteStop() }
                ),
                Arguments.of(listOf(CarteNum(0), CarteBonusMultiplie()) + (1..12).flatMap {num -> List(num) { CarteNum(num) }} +
                        (1..5).map { CarteBonusPlus(it*2) } + List(3) { Carte2ndeChance() } +
                        List(4) { Carte3aLaSuite() } + List(2) { CarteStop() }
                ),
                Arguments.of(listOf(CarteNum(0), CarteBonusMultiplie()) + (1..12).flatMap {num -> List(num) { CarteNum(num) }} +
                        (1..5).map { CarteBonusPlus(it*2) } + List(3) { Carte2ndeChance() } +
                        List(3) { Carte3aLaSuite() } + List(2) { CarteStop() }
                ),
                Arguments.of(listOf(CarteNum(0), CarteBonusMultiplie()) + (1..12).flatMap {num -> List(num) { CarteNum(num) }} +
                        (1..5).map { CarteBonusPlus(it*2) } + List(3) { Carte2ndeChance() } +
                        List(3) { Carte3aLaSuite() } + List(4) { CarteStop() }
                )
            )
        }
    }
}

class TestOutilsCarte_verfieMainCorrect {

    val o = OutilsCarte()

    @Test
    fun CT1_verfieMainCorrect(){
        assertDoesNotThrow {
            o.verifieMainCorrecte((1..7).map { CarteNum(it) } + (1..5).map { CarteBonusPlus(it*2) } + (1..3).map { Carte2ndeChance() } + (1..3).map { Carte3aLaSuite() } + listOf(CarteBonusMultiplie() , CarteStop()))
        }
    }

    @ParameterizedTest
    @MethodSource("argumentsProvider")
    fun CT2a9_verfieMainCorrect(main : List<Carte>){
        assertThrows<MainInvalideException> {
            o.verifieMainCorrecte(main)
        }
    }

    companion object {
        @JvmStatic
        fun argumentsProvider(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.of(listOf( CarteBonusMultiplie(), CarteBonusMultiplie())),
                Arguments.of(listOf( CarteBonusPlus(2) ) + (1..5).map { CarteBonusPlus(it*2) }),
                Arguments.of(listOf( CarteBonusPlus(2), CarteBonusPlus(2) )),
                Arguments.of((1..4).map { Carte2ndeChance() }),
                Arguments.of((1..8).map { CarteNum(it) } ),
                Arguments.of(listOf(CarteNum(0), CarteNum(0), CarteNum(0)) + (1..4).map { CarteNum(it) }),
                Arguments.of(listOf( CarteStop(), CarteStop() )),
                Arguments.of((1..4).map { Carte3aLaSuite() })
            )
        }
    }
}

class TestOutilsCarte_calculScore {

    val o = OutilsCarte()

    @ParameterizedTest
    @MethodSource("argumentsProvider")
    fun CT1a6_calculScore(main : List<Carte>, oracle : Int){
        assertEquals(
            oracle,
            o.calculScore(main)
        )
    }

    companion object {
        @JvmStatic
        fun argumentsProvider(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.of((1..6).map { CarteNum(it) },21),
                Arguments.of(listOf(CarteBonusMultiplie()) + (1..6).map { CarteNum(it) },42),
                Arguments.of((1..5).map {CarteBonusPlus(it*2)} + (1..6).map { CarteNum(it) },51),
                Arguments.of(listOf(CarteBonusMultiplie()) + (1..5).map {CarteBonusPlus(it*2)} + (1..6).map { CarteNum(it) },72),
                Arguments.of((0..6).map { CarteNum(it) },36)
            )
        }
    }

    @Test
    fun CT7_calculScore(){
        assertThrows<MainInvalideException> {
            o.calculScore(
                (0..7).map { CarteNum(it) }
            )
        }
    }
}
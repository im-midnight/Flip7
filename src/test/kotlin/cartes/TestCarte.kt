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
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream


class TestCarte_compareToInferior {

    @ParameterizedTest
    @MethodSource("cardsProvider")
    fun CT1_compareToInferior(dt1: Carte, dt2: Carte){
        assertEquals(-1, dt1.compareTo(dt2))
    }
    @ParameterizedTest
    @MethodSource("cardsProvider")
    fun CT2_compareToSuperior(dt1: Carte, dt2: Carte){
        assertEquals(1, dt2.compareTo(dt1))
    }
    @ParameterizedTest
    @MethodSource("cardsProvider")
    fun CT2_compareToEqual(dt1: Carte, dt2: Carte){
        assertEquals(0, dt1.compareTo(dt1))
        assertEquals(0, dt2.compareTo(dt2))
    }
    companion object {
        @JvmStatic
        fun cardsProvider(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.of(CarteNum(0), CarteNum(12)),
                Arguments.of(CarteNum(12), CarteBonusMultiplie()),
                Arguments.of(CarteBonusMultiplie(), CarteBonusPlus(2)),
                Arguments.of(CarteBonusPlus(2), CarteBonusPlus(10)),
                Arguments.of(CarteBonusPlus(10), Carte2ndeChance()),
                Arguments.of(Carte2ndeChance(), Carte3aLaSuite()),
                Arguments.of(Carte3aLaSuite(), CarteStop())
                )
        }
    }
}

class TestCarte_compareToSuperior{

    @ParameterizedTest
    @MethodSource("cardsProvider")
    fun CT1_compareToSuperior(dt1: Carte, dt2: Carte){
        assertEquals(1, dt2.compareTo(dt1))
    }
    companion object {
        @JvmStatic
        fun cardsProvider(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.of(CarteNum(0), CarteNum(12)),
                Arguments.of(CarteNum(12), CarteBonusMultiplie()),
                Arguments.of(CarteBonusMultiplie(), CarteBonusPlus(2)),
                Arguments.of(CarteBonusPlus(2), CarteBonusPlus(10)),
                Arguments.of(CarteBonusPlus(10), Carte2ndeChance()),
                Arguments.of(Carte2ndeChance(), Carte3aLaSuite()),
                Arguments.of(Carte3aLaSuite(), CarteStop())
            )
        }
    }
}
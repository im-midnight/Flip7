package vue

import iut.info1.flip7.IJoueur
import javafx.scene.chart.LineChart
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart

class LineChartVue(xAxis: NumberAxis, yAxis: NumberAxis) : LineChart<Number, Number>(xAxis, yAxis) {

    var xAxis = xAxis
    var yAxis = yAxis

    init {
        this.style = "-fx-background-color: white;"
        this.styleClass.add("box-solid")
        this.xAxis.label = "Manche"
        this.xAxis.isAutoRanging = false
        this.xAxis.lowerBound = 0.0
        this.xAxis.tickUnit = 1.0

        this.yAxis.label = "Points"
        this.yAxis.isAutoRanging = false
        this.yAxis.tickUnit = 10.0
        this.yAxis.lowerBound = 0.0

        this.title = "Progression des scores"
        this.isLegendVisible = false
        this.styleClass.add("selection")
        this.scaleX = 0.8
        this.scaleY = 0.8

    }
    fun updateLineChartVue(scoreManche:Map<Int, List<Int>>, joueurs: List<IJoueur>, manche:Int, score:Int){
        this.data.clear()
        this.xAxis.upperBound = manche.toDouble()
        this.yAxis.upperBound = score.toDouble()


        for (i in 0 until joueurs.size) {

            val serie = XYChart.Series<Number, Number>()
            serie.name = joueurs[i].donneNom()
            serie.data.add(XYChart.Data(0,  0))

            var scoreTotal = 0
            scoreManche.keys.forEach { manche ->
                scoreTotal += scoreManche[manche]!![i]
                serie.data.add(XYChart.Data(manche,  scoreTotal))
            }
            data.add(serie)
        }
    }

}
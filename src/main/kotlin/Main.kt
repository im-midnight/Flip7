
import controleur.AccueilControleurBoutonExit
import controleur.AccueilControleurBoutonPlay
import controleur.AccueilControleurBoutonRegles
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import model.Flip7Model
import vue.AccueilVue

class Main: Application() {

    override fun start(primaryStage: Stage) {

        val vue = AccueilVue()
        val model = Flip7Model()

        vue.fixeControleurBouton(vue.buttonPlay, AccueilControleurBoutonPlay(vue, model))
        vue.fixeControleurBouton(vue.buttonRegles, AccueilControleurBoutonRegles(vue, model))
        vue.fixeControleurBouton(vue.buttonExit, AccueilControleurBoutonExit(vue, model))

        val scene = Scene(vue)
        scene.stylesheets.add(Main::class.java.getResource("/css/style.css").toExternalForm()) // Pour le Font

        primaryStage.title="FLIP7"
        primaryStage.scene=scene
        primaryStage.isMaximized = true
        primaryStage.show()
        // Source - https://stackoverflow.com/a/12828404
        // Posted by user1736233, modified by community. See post 'Timeline' for change history
        // Retrieved 2026-06-22, License - CC BY-SA 3.0
        primaryStage.getIcons().add(Image((Main::class.java.getResourceAsStream("icon.png")))) // icon joueur
    }
}

fun main(){
    Application.launch(Main::class.java)
}
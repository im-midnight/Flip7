package vue

import Main
import iut.info1.flip7.IJoueur
import iut.info1.flip7.cartes.Carte
import iut.info1.flip7.etats.EtatJoueur
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Cursor
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ContentDisplay.CENTER
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.FlowPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Modality
import javafx.stage.Stage
import model.Flip7Model
import java.io.FileInputStream

class ChoixCiblePopup(joueurs : List<IJoueur>, etatJoueur: Map<Int, EtatJoueur>,carte: Carte) {

    private val stage = Stage()

    var indiceJoeurChoisi: Int = 0
        private set

    val vueChoixCible = VBox(10.0)

    val listeBouton = joueurs.map { nomJoueur ->
        Button(nomJoueur.donneNom())
    }

    val textTitre = if (carte.estCarteStop()) "A qui voulez-vous donner la carte Stop ?" else if (carte.estCarte3aLaSuite()) "A qui voulez-vous donner la carte 3 à la suite ?" else "Choisir un joueur : "

    val titre = Label(textTitre).apply { style =  "-fx-font-size: 22px;" }
    val ctitre = FlowPane()
    val joueur12 = HBox()
    val joueur34 = HBox()

    val imgMenu = (1..4).map {i -> Image(FileInputStream("assets/choixJoueur$i.png"))}
    val viewMenu = (imgMenu).map { imgMenu -> ImageView(imgMenu).apply { fitWidth = 300.0; fitHeight = 200.0; isPreserveRatio = false }}

    init {
        stage.initModality(Modality.APPLICATION_MODAL)
        stage.setAlwaysOnTop(true)
        stage.title = "Choix de la cible"

        titre.styleClass.add("titreChoixJoueur")
        val scene = Scene(vueChoixCible,800.0,650.0)
        vueChoixCible.padding = Insets(15.0, 20.0, 10.0, 20.0)

        ctitre.children.addAll(titre)
        ctitre.alignment = Pos.CENTER
        ctitre.padding = Insets(50.0)

        var joueur1 = VBox(listeBouton[0]).apply { prefWidth = 50.0
        }
        var joueur2 = VBox(listeBouton[1]).apply { prefWidth = 50.0 }
        joueur12.children.addAll(joueur1,joueur2)
        joueur12.spacing = 20.0


        if (joueurs.size >= 3) {
            var joueur3 = VBox(listeBouton[2]).apply { prefWidth = 50.0 }
            joueur34.children.add(joueur3)
        }
        if (joueurs.size == 4) {
            var joueur4 = VBox(listeBouton[3]).apply { prefWidth = 50.0 }
            joueur34.children.add(joueur4)
        }

        joueur34.spacing = 20.0

        vueChoixCible.alignment = Pos.CENTER
        joueur12.alignment = Pos.CENTER
        joueur34.alignment = Pos.CENTER


        listeBouton.forEachIndexed { i, bouton ->
            bouton.graphic = viewMenu[i]
            bouton.contentDisplay = CENTER
            bouton.setOnMouseEntered {
                bouton.scaleX = 1.05
                bouton.scaleY = 1.05
            }
            bouton.setOnMouseExited{
                bouton.scaleX = 1.0
                bouton.scaleY = 1.0
            }
            bouton.padding = Insets(10.0)
            bouton.contentDisplay = CENTER
            bouton.style = "-fx-background-color: transparent; -fx-font-size: 18px;" }


        vueChoixCible.children.addAll(ctitre,joueur12,joueur34)

        for (i in 0 until joueurs.size) {
            if (etatJoueur[i] != EtatJoueur.JOUE_ENCORE){
                listeBouton[i].isDisable = true
            }
            else {
                listeBouton[i].isDisable = false
            }
        }


        scene.stylesheets.add(Main::class.java.getResource("/css/style.css").toExternalForm())
        stage.scene = scene
    }

    fun fixeControleurBouton(bouton: Button, action: EventHandler<ActionEvent>) {
        bouton.onAction = action
    }

    fun selectionnerCibleEtFermer(nombreJoueur: Int) {
        this.indiceJoeurChoisi = nombreJoueur
        stage.close()
    }

    fun afficher() : Int {
        stage.showAndWait()
        return indiceJoeurChoisi
    }
}
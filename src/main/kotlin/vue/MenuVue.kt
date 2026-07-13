package vue


import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.BorderPane
import javafx.scene.layout.FlowPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import model.Joueur
import java.io.FileInputStream

class MenuVue: VBox() {

    val listeJoueursSauvegardes: ListView<Joueur?> = ListView<Joueur?>()
    val listeJoueursSelectionnes: ListView<Joueur?> = ListView<Joueur?>()



    val boutonRetour = Button("Retour")
    val scoreLabel = Label("Score max:")
    val slider = Slider()
    val scoreIndicator = Label("50")
    val boutonJouer = Button("Jouer")
    val boutonsBorderPane = BorderPane()

    val boutonSelectGauche = Button("<")
    val boutonSelectDroite = Button(">")

    val boutonAjouterJoueur = Button("Ajouter joueur")
    val boutonModifierJoueur = Button("Modifier joueur")
    val boutonSupprimerJoueur = Button("Supprimer joueur")

    init {

        this.spacing = 20.0
        fun couleurBouton(button: Button,chemin : String) {
            val input = FileInputStream(chemin)
            val img: Image = Image(input)
            val view: ImageView = ImageView(img)
            view.setFitHeight(40.0)
            view.setPreserveRatio(true)
            button.graphic = view
            button.styleClass.add("change-font-lilitaone")
            button.styleClass.add("transparent");
            button.styleClass.add("bold")
            button.contentDisplay = ContentDisplay.CENTER
        }

        fun boiteDeplacer(button: Button) {
            val input = FileInputStream("assets/deplace.png")
            val img = Image(input)
            val view = ImageView(img)
            view.setFitHeight(50.0)
            view.setPreserveRatio(true)
            button.graphic = view
            button.styleClass.add("transparent");
            button.contentDisplay = ContentDisplay.CENTER
        }

        val inputPlay = FileInputStream("assets/fondPlay.png")
        val imgPlay = Image(inputPlay)
        val viewPlay = ImageView(imgPlay)
        viewPlay.setFitHeight(60.0)
        viewPlay.setPreserveRatio(true)
        boutonJouer.graphic = viewPlay
        boutonJouer.styleClass.add("change-font-lilitaone")
        boutonJouer.styleClass.add("transparent");
        boutonJouer.styleClass.add("grand-bouton")
        boutonJouer.styleClass.add("bold")
        boutonJouer.contentDisplay = ContentDisplay.CENTER

        val inputRetour = FileInputStream("assets/fondRetour.png")
        val imgRetour = Image(inputRetour)
        val viewRetour = ImageView(imgRetour)
        viewRetour.setFitHeight(60.0)
        viewRetour.setPreserveRatio(true)
        boutonRetour.styleClass.add("change-font-lilitaone")
        boutonRetour.graphic = viewRetour
        boutonRetour.styleClass.add("transparent");
        boutonRetour.styleClass.add("grand-bouton")
        boutonRetour.styleClass.add("bold")
        boutonRetour.contentDisplay = ContentDisplay.CENTER

        val scoreBox = HBox()
        scoreLabel.styleClass.add("change-font-lilitaone")

        slider.min = 50.0
        slider.max = 200.0
        slider.isShowTickMarks = true
        slider.isShowTickLabels = true
        slider.isSnapToTicks = true

        scoreIndicator.styleClass.add("change-font-lilitaone")

        scoreBox.children.addAll(scoreLabel, slider, scoreIndicator)
        scoreBox.spacing = 20.0
        scoreBox.alignment = Pos.CENTER


        couleurBouton(boutonAjouterJoueur,"assets/ajouter.png")
        boutonAjouterJoueur.styleClass.add("green")

        couleurBouton(boutonModifierJoueur,"assets/modifier.png")
        boutonModifierJoueur.styleClass.add("orange")

        couleurBouton(boutonSupprimerJoueur,"assets/supprimer.png")
        boutonSupprimerJoueur.styleClass.add("red")

        boiteDeplacer(boutonSelectDroite)
        boiteDeplacer(boutonSelectGauche)

        fun animationBoutton(bouton: Button){
            bouton.setOnMouseEntered{
                bouton.scaleX = 1.1
                bouton.scaleY = 1.1
            }
            bouton.setOnMouseExited{
                bouton.scaleX = 1.0
                bouton.scaleY = 1.0
            }
        }

        animationBoutton(boutonRetour)
        animationBoutton(boutonJouer)
        animationBoutton(boutonSelectGauche)
        animationBoutton(boutonSelectDroite)
        animationBoutton(boutonAjouterJoueur)
        animationBoutton(boutonModifierJoueur)
        animationBoutton(boutonSupprimerJoueur)

        this.padding = Insets(10.0, 10.0, 10.0, 10.0)
        this.spacing = 5.0

        val titre = Label("Sélection des joueurs")
        val flowPane = FlowPane(titre)
        flowPane.alignment = Pos.CENTER

        val labelJoueursSauvegardes = Label("Joueurs sauvegardés")
        val labelGuideSauvgarde = Label("nom | parties jouées | 1ère place")
        val joueursSauvegardes = VBox(labelJoueursSauvegardes,labelGuideSauvgarde, listeJoueursSauvegardes)
        joueursSauvegardes.styleClass.add("box-solid")
        joueursSauvegardes.padding = Insets(10.0)
        joueursSauvegardes.spacing = 20.0

        joueursSauvegardes.prefWidth = 500.0
        joueursSauvegardes.prefHeight = 500.0

        val buttonVBox = VBox(boutonSelectGauche, boutonSelectDroite)
        buttonVBox.alignment = Pos.CENTER
        buttonVBox.spacing = 5.0
        buttonVBox.prefWidth = boutonSelectGauche.width

        val labelJoueursSelectionnes = Label("Joueurs Sélectionnés")
        val labelGuideSelection = Label("nom | parties jouées | 1ère place")
        val joueursSelectionnes = VBox(labelJoueursSelectionnes,labelGuideSelection, listeJoueursSelectionnes)
        joueursSelectionnes.styleClass.add("box-solid")
        joueursSelectionnes.padding = Insets(10.0)
        joueursSelectionnes.spacing = 20.0

        joueursSelectionnes.prefWidth = 500.0
        joueursSelectionnes.prefHeight = 500.0

        val controlPanel = HBox(boutonAjouterJoueur, boutonModifierJoueur, boutonSupprimerJoueur)
        controlPanel.alignment = Pos.CENTER
        controlPanel.padding = Insets(10.0, 10.0, 10.0, 10.0)
        controlPanel.spacing = 5.0

        val joueurs = BorderPane()
        joueurs.padding = Insets(20.0)
        joueurs.left = joueursSauvegardes
        joueurs.right = joueursSelectionnes
        joueurs.center = buttonVBox
        joueurs.bottom = controlPanel

        boutonsBorderPane.left = boutonRetour
        boutonsBorderPane.center = scoreBox
        boutonsBorderPane.right = boutonJouer
        boutonsBorderPane.padding = Insets(10.0)
        this.children.addAll(flowPane, joueurs, boutonsBorderPane)
    }

    fun fixeControleurBouton(bouton: Button, eventHandler: EventHandler<ActionEvent>) {
        bouton.onAction = eventHandler
    }

    fun fixeListenerListesJoueurs(eventHandler: EventHandler<MouseEvent>){
        this.listeJoueursSauvegardes.onMouseClicked=eventHandler
        this.listeJoueursSelectionnes.onMouseClicked=eventHandler
    }

    fun isDeleteButtonDisabled(etat: Boolean) {
        boutonSupprimerJoueur.isDisable = etat
    }
    fun isModifyButtonDisabled(etat: Boolean) {
        boutonModifierJoueur.isDisable = etat
    }
    fun isSelectGaucheButtonDisabled(etat: Boolean) {
        boutonSelectGauche.isDisable = etat
    }
    fun isSelectDroiteButtonDisabled(etat: Boolean) {
        boutonSelectDroite.isDisable = etat
    }
}
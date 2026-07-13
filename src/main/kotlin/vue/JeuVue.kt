package vue


import iut.info1.flip7.IJoueur
import iut.info1.flip7.cartes.Carte
import iut.info1.flip7.etats.EtatJoueur
import javafx.animation.PauseTransition
import javafx.animation.ScaleTransition
import javafx.animation.SequentialTransition
import javafx.beans.property.SimpleStringProperty
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.ContentDisplay.CENTER
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.ScrollPane.ScrollBarPolicy
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.util.Duration
import java.io.FileInputStream

class JeuVue(joueurs : List<IJoueur>,scoresMancheActuelle: List<Int>) : StackPane(){

    var vueCarteAnimation = ImageView()

    var animationEnCours = false

    val vueGlobal = GridPane()

    val joueurs = joueurs

    val listVBoxJoueursScore = joueurs.mapIndexed { index, joueur ->
        var nomJoueur = joueur.donneNom()
        val labelNom = Label(nomJoueur)
        val labelScore = Label(
            scoresMancheActuelle[index].toString()
        )

        VBox(labelNom, labelScore).apply {
            alignment = Pos.CENTER
            styleClass.add("vbox-joueur")
            prefWidth = 250.0
        }
    }

    val labelMancheCourante = Label()
    val labelTour = Label("0").apply { prefWidth = 200.0 }
    val labelScoreMax = Label()

    val header = HBox()

    var listVBoxJoueursDeck = creationDeck(joueurs)

    val decks = VBox()

    val labelPiocheText = Label("Pioche")
    val labelPiocheNombre = Label("0")
    val vBoxPioche = VBox(labelPiocheText,labelPiocheNombre)
    val labelDefausseText = Label("Défausse")
    val labelDefausseNombre = Label("0")
    val vBoxDefausse = VBox(labelDefausseText,labelDefausseNombre)
    val hBoxCartes = HBox(vBoxPioche,vBoxDefausse)

    val buttonPiocher = Button("Piocher")

    val buttonStop = Button("Stop")
    val vBoxButtonsPartie = VBox(buttonPiocher,buttonStop)

    val comboTheme = ComboBox<String>()

    val plateau = VBox()

    init {
        val imageDos = imageCartes("dosFlip7").image
        vueCarteAnimation.image = imageDos
        vueCarteAnimation.fitHeight = 400.0
        vueCarteAnimation.isPreserveRatio = true

        this.children.addAll(vueGlobal, vueCarteAnimation)

        vueCarteAnimation.isManaged = false
        vueCarteAnimation.isVisible = false

        vueGlobal.vgap = 30.0
        vueGlobal.hgap = 30.0


        creerBoitePiocheLabel(labelPiocheNombre)
        creerBoitePiocheLabel(labelDefausseNombre)
        creerBoitePetitLabel(labelMancheCourante)
        creerBoiteBoutonPicoher(buttonPiocher)
        creerBoiteBoutonStop(buttonStop)

        buttonPiocher.styleClass.add("change-font-lilitaone")
        buttonStop.styleClass.add("change-font-lilitaone")

        buttonPiocher.setOnMouseEntered { buttonPiocher.scaleX = 1.1; buttonPiocher.scaleY = 1.1 }
        buttonPiocher.setOnMouseExited  { buttonPiocher.scaleX = 1.0; buttonPiocher.scaleY = 1.0 }

        buttonStop.setOnMouseEntered    { buttonStop.scaleX = 1.1;    buttonStop.scaleY = 1.1 }
        buttonStop.setOnMouseExited     { buttonStop.scaleX = 1.0;    buttonStop.scaleY = 1.0 }

        header.spacing = 30.0
        header.alignment = Pos.CENTER_LEFT
        header.padding = Insets(5.0)
        header.children.addAll(labelMancheCourante, labelTour)
        header.children += listVBoxJoueursScore

        decks.spacing = 20.0
        decks.children += listVBoxJoueursDeck

        val scrollPaneDecks = ScrollPane(decks).apply {
            hbarPolicy = ScrollBarPolicy.NEVER
            vbarPolicy = ScrollBarPolicy.AS_NEEDED

            style = "-fx-background-color:transparent; -fx-background-insets: 0; -fx-padding: 0;"

            isFitToWidth = true

            maxHeight = 1000.0

        }

        val colonneGauche = VBox(20.0, header, scrollPaneDecks)
        colonneGauche.padding = Insets(10.0)

        VBox.setMargin(scrollPaneDecks, Insets(40.0, 0.0, 0.0, 0.0))

        hBoxCartes.spacing = 60.0
        hBoxCartes.padding = Insets(20.0)
        hBoxCartes.alignment = Pos.CENTER

        vBoxButtonsPartie.spacing = 20.0
        vBoxButtonsPartie.alignment = Pos.CENTER

        plateau.children.addAll(hBoxCartes, vBoxButtonsPartie, comboTheme)
        comboTheme.styleClass.add("box-solid")
        comboTheme.styleClass.add("combo-box")

        comboTheme.setOnMouseEntered {
            comboTheme.scaleX = 1.05
            comboTheme.scaleY = 1.05
        }
        comboTheme.setOnMouseExited{
            comboTheme.scaleX = 1.0
            comboTheme.scaleY = 1.0
        }

        val colonneDroite = VBox(40.0, labelScoreMax,plateau,comboTheme).apply {
            alignment = Pos.TOP_CENTER
            padding = Insets(20.0)
        }


        vueGlobal.add(colonneGauche, 0, 0)
        vueGlobal.add(colonneDroite, 1, 0)

        val col0= ColumnConstraints()
        col0.percentWidth = 70.0

        val col1 = ColumnConstraints()
        col1.percentWidth = 30.0

        vueGlobal.columnConstraints.addAll(col0, col1)
    }

    fun annimationCartePioche(carte : Carte) {
        animationEnCours = true

        val cartePiochee = carteToStringAsset(carte)

        vueCarteAnimation.isVisible = true
        vueCarteAnimation.isManaged = true
        vueCarteAnimation.scaleX = 1.0
        vueCarteAnimation.image = imageCartes("dosFlip7").image

        val retournementDosFlip7 = ScaleTransition(Duration.millis(250.0), vueCarteAnimation)
        retournementDosFlip7.fromX = 1.0
        retournementDosFlip7.toX = 0.0

        retournementDosFlip7.setOnFinished {
            val imageCartePiochee = imageCartes(cartePiochee).image
            vueCarteAnimation.image = imageCartePiochee
        }

        val retournementCartePioche = ScaleTransition(Duration.millis(300.0), vueCarteAnimation)
        retournementCartePioche.fromX = 0.0
        retournementCartePioche.toX = 1.0

        val pauseApresRetournement = PauseTransition(Duration.millis(400.0))

        val animation = SequentialTransition(retournementDosFlip7,retournementCartePioche,pauseApresRetournement)

        animation.setOnFinished {
            vueCarteAnimation.isVisible = false
            vueCarteAnimation.isManaged = false

            animationEnCours = false
        }

        animation.play()
    }

    fun creerBoitePetitLabel(label: Label){
        val inputBoite = FileInputStream("assets/boitePetit.png")
        val imgBoite = Image(inputBoite)
        val view = ImageView(imgBoite)
        view.fitHeight = 60.0
        view.isPreserveRatio = true

        label.graphic = view
        label.contentDisplay = CENTER
    }

    fun creerBoiteBoutonPicoher(button: Button){
        val inputBoite = FileInputStream("assets/boutonPiocher.png")
        val imgBoite = Image(inputBoite)
        val view = ImageView(imgBoite)
        view.fitHeight = 60.0
        view.isPreserveRatio = true

        button.graphic = view
        button.contentDisplay = CENTER
        button.style = "-fx-background-color: transparent;"
        button.styleClass.add("grand-bouton")
    }

    fun creerBoiteBoutonStop(button: Button) {
        val inputBoite = FileInputStream("assets/boutonStop.png")
        val imgBoite = Image(inputBoite)
        val view = ImageView(imgBoite)
        view.fitHeight = 60.0
        view.isPreserveRatio = true

        button.graphic = view
        button.contentDisplay = CENTER
        button.style = "-fx-background-color: transparent;"
        button.styleClass.add("grand-bouton")
    }

    fun creerBoitePiocheLabel(label: Label){
        val inputBoite = FileInputStream("assets/pioche.png")
        val imgBoite = Image(inputBoite)
        val view = ImageView(imgBoite)

        view.fitHeight = 140.0
        view.fitWidth = 120.0

        label.graphic = view
        label.contentDisplay = CENTER

    }

    fun setComboTheme(combo: ComboBox<String>, items: Array<String>, selectedItem: SimpleStringProperty) {
        combo.items.addAll(items)
        combo.selectionModel.select(selectedItem.get())
    }

    fun fixeControleurBouton(bouton: Button, action: EventHandler<ActionEvent>) {
        bouton.onAction = action
    }

    fun carteToStringAsset(carte : Carte) : String {
        var carteString = ""
        when {
            carte.estCarteNum() -> {
                carteString =carte.valeur.toString()
            }
            carte.estCarteBonusMultiplie() -> {
                carteString ="x2"
            }
            carte.estCarteBonusPlus() -> {
                carteString ="+${carte.valeur}"
            }
            carte.estCarte2ndeChance() -> {
                carteString ="2ndeChance"
            }
            carte.estCarteStop() -> {
                carteString ="stop"
            }
            carte.estCarte3aLaSuite() -> {
                carteString ="3suite"
            }
        }
        return carteString
    }

    fun imageCartes(carte : String) : ImageView{
        val image = ImageView(Image(FileInputStream("assets/cartes_decoupees_style_${this.comboTheme.valueProperty().get() ?: "flip7"}/carte_$carte.png")))
        image.fitHeight = 80.0
        image.isPreserveRatio = true
        image.styleClass.add("image")

        return image
    }

    fun creationDeck(joueurs : List<IJoueur>) : List<HBox> {
        return joueurs.map { nomJoueur ->

            val hBoxCartesNum = HBox()
            val labelNomJoueur = Label(nomJoueur.donneNom())
            val vBoxCartesNum = VBox(labelNomJoueur,hBoxCartesNum)

            val hBoxCartesBonus = HBox()
            val labelCartesBonus = Label("Cartes Bonus")
            val vBoxCartesBonus = VBox(labelCartesBonus,hBoxCartesBonus)

            val hBoxCartesSpecial = HBox()
            val labelCartesSpecial = Label("Cartes Spéciales")
            val vBoxCartesSpecials = VBox(labelCartesSpecial,hBoxCartesSpecial)

            val hBoxDeck = HBox(vBoxCartesNum,vBoxCartesBonus,vBoxCartesSpecials).apply {
                styleClass.add("vbox-joueur")
                padding = Insets(10.0)
                spacing = 30.0
            }

            hBoxDeck
        }
    }

    fun updateAllJeuVue(nbCartesPioche : Int, nbCartesDefausse : Int, joueurCourant : Int, etatJoueurs : Map<Int, EtatJoueur>, mains : Map<Int, List<Carte>>,scoresMancheActuelle: List<Int>,mancheActuelle : Int,tour:Int,scoreMax : Int){
        updateScoreJoeurs(scoresMancheActuelle)
        updatePiocheDefausseNombresCartes(nbCartesPioche, nbCartesDefausse)
        updateDecks( joueurCourant, etatJoueurs , mains)
        updateManche(mancheActuelle)
        updateTour(tour)
        updateScoreMax(scoreMax)
    }

    fun updateManche(manche: Int){
        labelMancheCourante.text = "Manche : ${(manche+1).toString()}"
    }

    fun updateTour(tour:Int){

        labelTour.text = "Tour: ${tour.toString()}"

    }

    fun updateScoreMax(scoreMax : Int){
        labelScoreMax.text = "Score Max: $scoreMax"
    }

    fun updateScoreJoeurs(scoresMancheActuelle: List<Int>){
        listVBoxJoueursScore.mapIndexed { index, JoueurScore ->
            val labelJoueur = JoueurScore.children[1] as Label
            labelJoueur.text = scoresMancheActuelle[index].toString()
        }
    }

    fun updatePiocheDefausseNombresCartes(nbCartesPioche : Int, nbCartesDefausse : Int) {
        labelPiocheNombre.text = nbCartesPioche.toString()
        labelDefausseNombre.text = nbCartesDefausse.toString()
    }

    fun updateDecks( joueurCourant : Int, etatJoueurs : Map<Int, EtatJoueur>, mains : Map<Int, List<Carte>>){
        listVBoxJoueursDeck.forEach { it.styleClass.clear() }
        listVBoxJoueursDeck.forEachIndexed { i, joueur ->
            when (etatJoueurs[i]) {
                EtatJoueur.JOUE_ENCORE -> joueur.styleClass.add("vbox-joueur")
                EtatJoueur.STOP -> joueur.styleClass.add("vbox-joueur-stopper")
                EtatJoueur.PERDU -> joueur.styleClass.add("vbox-joueur-perdu")
                else -> {}
            }
        }

        listVBoxJoueursDeck[joueurCourant].styleClass.add("vbox-joueur-selectionner")

        val spacing = 5.0

        for (joueurs in mains){
            var cartesDuJoueur = joueurs.value
            if (cartesDuJoueur.isNotEmpty()) {
                val hBoxDeckGlobal = listVBoxJoueursDeck[joueurs.key]
                val vBoxCartesNum = hBoxDeckGlobal.children[0] as VBox
                val hBoxImagesNum = vBoxCartesNum.children[1] as HBox
                val vBoxCartesBonus = hBoxDeckGlobal.children[1] as VBox
                val hBoxImagesBonus = vBoxCartesBonus.children[1] as HBox
                val vBoxCartesSpecial = hBoxDeckGlobal.children[2] as VBox
                val hBoxImagesSpecial = vBoxCartesSpecial.children[1] as HBox

                hBoxImagesNum.children.clear()
                hBoxImagesBonus.children.clear()
                hBoxImagesSpecial.children.clear()

                for (carte in cartesDuJoueur){
                    val carteString = carteToStringAsset(carte)

                    when {
                        carte.estCarteNum() -> {
                            val nouvelleCarte = imageCartes(carteString)
                            hBoxImagesNum.children += nouvelleCarte
                            hBoxImagesNum.spacing = spacing
                        }
                        carte.estCarteBonusMultiplie() -> {
                            val nouvelleCarte = imageCartes(carteString)
                            hBoxImagesBonus.children += nouvelleCarte
                            hBoxImagesBonus.spacing = spacing
                        }
                        carte.estCarteBonusPlus() -> {
                            val nouvelleCarte = imageCartes(carteString)
                            hBoxImagesBonus.children += nouvelleCarte
                            hBoxImagesBonus.spacing = spacing
                        }
                        carte.estCarte2ndeChance() -> {
                            val nouvelleCarte = imageCartes(carteString)
                            hBoxImagesSpecial.children += nouvelleCarte
                            hBoxImagesSpecial.spacing = spacing
                        }
                        carte.estCarteStop() -> {
                            val nouvelleCarte = imageCartes(carteString)
                            hBoxImagesSpecial.children += nouvelleCarte
                            hBoxImagesSpecial.spacing = spacing
                        }
                        carte.estCarte3aLaSuite() -> {
                            val nouvelleCarte = imageCartes(carteString)
                            hBoxImagesSpecial.children += nouvelleCarte
                            hBoxImagesSpecial.spacing = spacing
                        }
                    }
                }
            }
        }
    }
}
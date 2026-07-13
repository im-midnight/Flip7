package controleur

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.control.TextInputDialog
import javafx.scene.input.MouseEvent
import model.Flip7Model
import model.Joueur
import vue.AccueilVue
import vue.MenuVue

fun showNameInputEmpty(): Boolean {
    val dialog = Alert(Alert.AlertType.ERROR)
    dialog.title = "Choix invalide"
    dialog.headerText = null
    dialog.contentText = "Veuillez choisir un pseudonyme valide."

    val answer = dialog.showAndWait()

    return answer.isPresent && answer.get() == ButtonType.OK
}

fun showNameInputAlreadyExists(): Boolean {
    val dialog = Alert(Alert.AlertType.ERROR)
    dialog.title = "Choix invalide"
    dialog.headerText = null
    dialog.contentText = "Ce pseudo est déjà utilisé."

    val answer = dialog.showAndWait()

    return answer.isPresent && answer.get() == ButtonType.OK
}

private fun updateButtons(menuVue: MenuVue, model: Flip7Model) {
    if (model.joueursSauvegardes.isEmpty() && model.joueursSelectionnes.isEmpty()) {
        menuVue.isDeleteButtonDisabled(true)
        menuVue.isModifyButtonDisabled(true)
    } else {
        menuVue.isDeleteButtonDisabled(false)
        menuVue.isModifyButtonDisabled(false)
    }

    if (menuVue.listeJoueursSauvegardes.selectionModel.selectedItem == null) {
        menuVue.isSelectDroiteButtonDisabled(true)
    } else menuVue.isSelectDroiteButtonDisabled(false)
    if (menuVue.listeJoueursSelectionnes.selectionModel.selectedItem == null) {
        menuVue.isSelectGaucheButtonDisabled(true)
    } else menuVue.isSelectGaucheButtonDisabled(false)
}

private fun updateView(menuVue: MenuVue, model: Flip7Model) {
    menuVue.listeJoueursSauvegardes.items.clear()

    if (model.joueursSauvegardes.isEmpty()) {
        menuVue.listeJoueursSauvegardes.items.clear()
        menuVue.isDeleteButtonDisabled(true)
        menuVue.isModifyButtonDisabled(true)
    } else {
        model.joueursSauvegardes.forEach { menuVue.listeJoueursSauvegardes.items.add(it) }
        menuVue.isDeleteButtonDisabled(false)
        menuVue.isModifyButtonDisabled(false)
    }

    menuVue.listeJoueursSelectionnes.items.clear()

    if (model.joueursSelectionnes.isEmpty()) {
        menuVue.listeJoueursSelectionnes.items.clear()
        menuVue.isDeleteButtonDisabled(true)
        menuVue.isModifyButtonDisabled(true)
    } else {
        model.joueursSelectionnes.forEach { menuVue.listeJoueursSelectionnes.items.add(it) }
        menuVue.isDeleteButtonDisabled(false)
        menuVue.isModifyButtonDisabled(false)
    }

    updateButtons(menuVue, model)
}

fun ouvrirMenuVue(source: Node, model: Flip7Model) {
    val menuVue = MenuVue()

    menuVue.fixeListenerListesJoueurs(MenuControleurClicListeJoueurs(menuVue, model))

    val CBS = ControleurBindingSlider(menuVue, model)
    CBS.bindModeleVue()

    menuVue.fixeControleurBouton(menuVue.boutonRetour, MenuControleurBoutonRetour(menuVue, model))
    menuVue.fixeControleurBouton(menuVue.boutonJouer, MenuControleurBoutonPlay(menuVue, model))
    menuVue.fixeControleurBouton(menuVue.boutonSelectGauche, MenuControleurBoutonDeplacerVersGauche(menuVue, model))
    menuVue.fixeControleurBouton(menuVue.boutonSelectDroite, MenuControleurBoutonDeplacerVersDroite(menuVue, model))
    menuVue.fixeControleurBouton(menuVue.boutonAjouterJoueur, MenuControleurBoutonAjouterJoueur(menuVue, model))
    menuVue.fixeControleurBouton(menuVue.boutonModifierJoueur, MenuControleurBoutonModifierJoueur(menuVue, model))
    menuVue.fixeControleurBouton(menuVue.boutonSupprimerJoueur, MenuControleurBoutonSupprimerJoueur(menuVue, model))

    updateView(menuVue, model)

    source.scene.root = menuVue
}



class ControleurBindingSlider(
    private val vue: MenuVue,
    private val model: Flip7Model
) {
    fun bindModeleVue() {
        model.scoreMax.bind(vue.slider.valueProperty())
        vue.scoreIndicator.textProperty().bind(model.scoreMax.asString())
    }
}

class MenuControleurBoutonRetour (vue : MenuVue, model: Flip7Model) : EventHandler<ActionEvent> {

    private val vue = vue
    private val model = model

    override fun handle(event: ActionEvent?) {
        val source = event?.source as Node
        val accueilVue = AccueilVue()

        source.scene.root = accueilVue
        accueilVue.fixeControleurBouton(accueilVue.buttonPlay, AccueilControleurBoutonPlay(accueilVue,model))
        accueilVue.fixeControleurBouton(accueilVue.buttonRegles, AccueilControleurBoutonRegles(accueilVue,model))
        accueilVue.fixeControleurBouton(accueilVue.buttonExit, AccueilControleurBoutonExit(accueilVue,model))
    }
}

class MenuControleurBoutonPlay (vue : MenuVue, model: Flip7Model) : EventHandler<ActionEvent> {

    private val vue = vue
    private val model = model

    override fun handle(event: ActionEvent?) {

        if (model.joueursSelectionnes.size !in 2..4) {
            showNotEnoughPlayers()
            return
        }

        model.initialisationFlip7()
        if (model.flip7.joueurCourant == 0){
            model.tour++
        }
        val source = event?.source as Node
        ouvrirJeuVue(source, model)
    }

    private fun showNotEnoughPlayers() {
        val dialog = Alert(Alert.AlertType.INFORMATION)
        dialog.title = "Nombre de joueurs invalide"
        dialog.headerText = null
        dialog.contentText = "Veuillez sélectionner un nombre de joueurs valide."

        dialog.showAndWait()
    }
}

class MenuControleurBoutonAjouterJoueur (vue : MenuVue, model: Flip7Model) : EventHandler<ActionEvent> {

    private val vue = vue
    private val model = model

    override fun handle(event: ActionEvent?) {
        val newName = showNameInput()

        val nouveauNomJoueur = Joueur(newName)

        if (newName.isNotBlank() && !model.verifierContientJoueur(nouveauNomJoueur)) {
            model.joueursSauvegardes.add(Joueur(newName.trim()))
            model.sauvegarderListeJoueursActuel()

        } else if (newName.isBlank()) {
            if (showNameInputEmpty()) handle(event)
        } else if (model.verifierContientJoueur(nouveauNomJoueur)) {
            if (showNameInputAlreadyExists()) handle(event)
        }
        updateView(vue, model)
    }

    private fun showNameInput(): String {
        val dialog = TextInputDialog("Choisissez un pseudo:")
        dialog.title = "Création Joueur"
        dialog.headerText = null
        dialog.contentText = "Choisissez un pseudo:"

        val answer = dialog.showAndWait()
        return answer.get()
    }
}

class MenuControleurBoutonModifierJoueur (vue : MenuVue, model: Flip7Model) : EventHandler<ActionEvent> {

    private val vue = vue
    private val model = model

    override fun handle(event: ActionEvent?) {
        val joueurPortantLeNom = vue.listeJoueursSauvegardes.selectionModel.selectedItem
        if (joueurPortantLeNom == null) return

        val joueur = model.getJoueurSauvegarde(joueurPortantLeNom)
        if (joueur == null) return

        val nouveauNom = showNewNameInput()

        val nouveauNomJoueur = Joueur(nouveauNom)

        if (!nouveauNom.isBlank() && !model.verifierContientJoueur(nouveauNomJoueur)) {
            val index = model.joueursSauvegardes.indexOf(joueur)
            model.joueursSauvegardes[index] = joueur.copy(nom = nouveauNomJoueur.donneNom())
            model.sauvegarderListeJoueursActuel()
        } else if (nouveauNom.isBlank()) {
            if (showNameInputEmpty()) handle(event)
        } else if (model.verifierContientJoueur(nouveauNomJoueur)) {
            if (showNameInputAlreadyExists()) handle(event)
        }
        updateView(vue, model)
    }

    private fun showNewNameInput(): String {
        val dialog = TextInputDialog("pseudo")
        dialog.title = "Modification Joueur"
        dialog.headerText = null
        dialog.contentText = "Choisissez un nouveau pseudo:"

        val answer = dialog.showAndWait()
        return answer.get()
    }
}

class MenuControleurBoutonSupprimerJoueur(vue: MenuVue, model: Flip7Model) : EventHandler<ActionEvent> {
    private val vue = vue
    private val model = model
    private var selectedItem: Joueur ?= null
    private var joueur: Joueur? = null

    override fun handle(event: ActionEvent?) {

        if(vue.listeJoueursSauvegardes.selectionModel.selectedItem != null) {
            if (showDeleteConfirmation()) {
                selectedItem = vue.listeJoueursSauvegardes.selectionModel.selectedItem
                joueur = model.getJoueurSauvegarde(selectedItem!!)
                model.joueursSauvegardes.remove(joueur)
                model.sauvegarderListeJoueursActuel()
                if (model.joueursSauvegardes.isNotEmpty()) {
                    vue.listeJoueursSauvegardes.selectionModel.selectFirst()
                }
            }
        } else if (vue.listeJoueursSelectionnes.selectionModel.selectedItem != null) {
            if (showDeleteConfirmation()) {
                selectedItem = vue.listeJoueursSelectionnes.selectionModel.selectedItem
                joueur = model.getJoueurSelectionne(selectedItem!!)
                model.joueursSelectionnes.remove(joueur)
                if (model.joueursSelectionnes.isNotEmpty()) {
                    vue.listeJoueursSelectionnes.selectionModel.selectFirst()
                }
            }
        } else {
            return
        }

        updateView(vue, model)

        if (model.joueursSauvegardes.isNotEmpty()) {
            vue.listeJoueursSauvegardes.selectionModel.selectFirst()
        } else if (model.joueursSelectionnes.isNotEmpty()) {
            vue.listeJoueursSelectionnes.selectionModel.selectFirst()
        }
    }

    private fun showDeleteConfirmation(): Boolean {
        val dialog = Alert(Alert.AlertType.CONFIRMATION)
        dialog.title = "Suppression joueur"
        dialog.headerText = null
        dialog.contentText = "Êtes-vous sûr de vouloir supprimer ce joueur ?"
        val answer = dialog.showAndWait()

        return answer.isPresent && answer.get() == ButtonType.OK
    }
}

class MenuControleurBoutonDeplacerVersDroite (vue : MenuVue, model: Flip7Model) : EventHandler<ActionEvent> {

    private val vue = vue
    private val model = model

    override fun handle(event: ActionEvent?) {
        val joueurSelectionne = model.getJoueurSauvegarde(vue.listeJoueursSauvegardes.selectionModel.selectedItem)


        if (joueurSelectionne != null) {
            //(model.flip7.joueurs as MutableList<Joueur>).add(joueurSelectionne)

            model.joueursSelectionnes.add(joueurSelectionne)
            model.joueursSauvegardes.remove(joueurSelectionne)
            updateView(vue, model)
            if (model.joueursSauvegardes.isNotEmpty()) {
                vue.listeJoueursSauvegardes.selectionModel.selectFirst()
                updateButtons(vue, model)
            }
        }
    }

}

class MenuControleurBoutonDeplacerVersGauche (vue : MenuVue, model: Flip7Model) : EventHandler<ActionEvent> {

    private val vue = vue
    private val model = model

    override fun handle(event: ActionEvent?) {
        val joueurSelectionne = model.getJoueurSelectionne(vue.listeJoueursSelectionnes.selectionModel.selectedItem)

        if (joueurSelectionne != null) {
            //(model.flip7.joueurs as MutableList<Joueur>).remove(joueurSelectionne)

            model.joueursSelectionnes.remove(joueurSelectionne)
            model.joueursSauvegardes.add(joueurSelectionne)
            updateView(vue, model)
            if (model.joueursSelectionnes.isNotEmpty()) {
                vue.listeJoueursSelectionnes.selectionModel.selectFirst()
                updateButtons(vue, model)
            }
        }
    }
}

class MenuControleurClicListeJoueurs (vue : MenuVue, model: Flip7Model) : EventHandler<MouseEvent> {

    private val vue = vue
    private val model = model

    override fun handle(event: MouseEvent?) {
        if (event != null) {
            updateButtons(vue, model)
            if (event.clickCount == 2) {
                //(model.flip7.joueurs as MutableList<Joueur>).add(joueurSelectionne)

                if(vue.listeJoueursSauvegardes.selectionModel.selectedItem != null) {
                    val selectedItem = model.getJoueurSauvegarde(vue.listeJoueursSauvegardes.selectionModel.selectedItem)
                    model.joueursSelectionnes.add(selectedItem)
                    model.joueursSauvegardes.remove(selectedItem)
                    updateView(vue, model)

                } else if (vue.listeJoueursSelectionnes.selectionModel.selectedItem != null) {
                    val selectedItem = model.getJoueurSelectionne(vue.listeJoueursSelectionnes.selectionModel.selectedItem)
                    model.joueursSauvegardes.add(selectedItem)
                    model.joueursSelectionnes.remove(selectedItem)
                    updateView(vue, model)

                } else {
                    return
                }
            }
        }
    }
}
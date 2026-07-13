# Rapport

***Image***

## Classe `GameMaster`

- play(pointsMax) : Player: Démarre la boucle pour lancer le jeu, les pointsMax représente le seuil à atteindre pour finir la partie, il coordonne les interractions entre les joueur, le deck, la pioche et la défosse, et renvoie le joueur gagnant.
- manche() : Démarre la boucle pour lancer une manche unique.
- playerTurn() : Definis le joueur qui doit jouer.


## Enumclass `EtatJeu`

Nous avons choisis de definir EtatJeu en enum class car cela permet de definir que le joueur ne peut être que dans un etat definis, que le jeu est en chargement ou dans le menu.

## Enumclass 'EtatJoueur'

Permet de suivre en temps réel la situation d'un joueur pendant le déroulement d'une manche. Le joueur peut être EN_JEU lorsqu'il poursuit son tour ou sa manche, ou HORS_JEU s'il subit une élimination suite à un doublon critique.

## Classe `Player`

Une classe Player qui permet la création d'objet player avec un score et un total de points.

## Classe `Deck`

Nous avons décidé de discocier le deck du player pour permettre de reinitialiser simplement le deck apres une manche sans pour autant modifier le joueur.


Nous avons décidé de dissocier le deck du player pour permettre de réinitialiser simplement le deck après une manche sans pour autant modifier le joueur.

Dans l'analyse, le joueur a lui même les différents types de cartes avec un nombre limité. Pour une raison de practicité pour le développement, nous avons décidé de passer cela en liste globale de type Carte.



## Classe `Pioche`
Représente la réserve de cartes de la partie.

## Classe `Defausse`

Modélise la pile de cartes où sont rejetées les cartes éliminées ou nettoyées en fin de manche.

## Classe `Carte`

Le choix a été fait, pour l'implémentation des cartes, d'utiliser une interface.
Nous pensons que de séparer les catégories de cartes en différentes sous-classes de l'interface `carte` est un choix judicieux étant donné que, selon la catégorie, nous devons en limiter le nombre.
De plus, les trois catégories de cartes n'ont pas les mêmes attributs, leurs effets étant différents.

## Classe `CarteChiffre`
Cette classe concrète matérialise les cartes numériques (valeurs de 1 à 7).

## Classe `CarteBonus`
Modélise les cartes appliquant des modificateurs de score. Elle encapsule une valeur ainsi qu'un type énuméré nommé Operation (contenant les constantes ADDITION et MULTIPLICATION ).

## Interface `CarteSpeciale`

Cette interface sert à catégoriser et isoler les cartes possédant des comportements complexes altérant le cours et les règles normales d'un tour de jeu.


## Classe `CarteStop`

Son application fige le score actuel pour la manche.

## Classe `CarteSecondeChance`
Si un joueur pioche un doublon éliminatoire,cette carte s'active pour annuler la pénalité et lui offrir une opportunité de poursuivre son tour.


## Classe `CarteTroisALaSuite`
Déclenche une règle forçant le joueur à obligatoirement et immédiatement à trois actions de pioche consécutives sans possibilité de s'arrêter.


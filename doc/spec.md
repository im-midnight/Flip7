# Analyse et spécifications du jeu Flip7

Dans cette étape du travail, il vous est demandé d'analyser le jeu Flip7 ; de nombreuses ressources sont disponibles sur
Internet, en particulier [https://flip-7.com](https://flip-7.com) et
[https://catchupgames.com/nos-jeux/flip-7/](https://catchupgames.com/nos-jeux/flip-7/)

Les règles du jeu au format PDF sont
disponibles [ici](https://flip-7.com/wp-content/uploads/2025/10/flip7_regles_fr.pdf)

1. Il vous est demandé de concevoir un **diagramme de classes UML niveau Analyse** décrivant le jeu Flip7 ; vous
   apporterez une attention particulière au respect des notations et des concepts UML.

> [!NOTE]
> Cette étape est complètement indépendante de tous les choix techniques : il s'agit de décrire le jeu tel qu'il est,
> sans considérer le langage Kotlin/JavaFX, la bibliothèque, etc.

2. Il vous est ensuite demandé un **second diagramme de classes UML niveau Conception détaillée** qui sera un
   « raffinement » du diagramme précédent, intégrant vos choix techniques d'implémentation.

> [!NOTE]
> À cette étape, vous devez prendre en compte les choix d'implémentation qui ont déjà été faits pour vous (langage
> Kotlin, toolkit graphique JavaFX…), en vous plaçant dans la posture de quelqu'un qui va implémenter toute la mécanique
> du jeu.

## Livrables attendus

Vous rendrez dans le dossier `livrables/uml/` de votre dépôt Git :

- Un rapport de spécification précisant vos choix d'analyse et de conception, **exclusivement** au
  format [Markdown](https://fr.wikipedia.org/wiki/Markdown) (sans fioriture), dans un fichier nommé `rapport.md`

> [!TIP]
> Markdown est un format largement suffisant pour ce qui vous est demandé, et contrairement à Word & co, ce n'est que du
> code ;-)

- un diagramme de classes UML niveau analyse, produit **exclusivement** avec [PlantUML](https://plantuml.com/fr/),
  dans les fichiers `analyse.puml` (source PlantUML) et `analyse.png` (PNG généré)

- un diagramme de classes UML niveau spécification détaillée, produit **exclusivement** avec PlantUML,
  dans les fichiers `detaille.puml` (source PlantUML) et `detaille.png` (PNG généré)

> [!TIP]
> PlantUML, à la différence de nombreux éditeurs graphiques UML, a le grand avantage d'être uniquement du code à
> écrire ;-)

Pour écrire du PlantUML, vous pouvez installer le binaire, utiliser l'interface web ou un plugin pour VScode ou
IntelliJ...

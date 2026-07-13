# Tests d'une bibliothèque implémentant le jeu Flip7

La bibliothèque `iut.info1.flip7-1.x.jar` implémentant la mécanique du jeu Flip7 vous est décrite [**ici**](lib.md).

Il vous est demandé de concevoir et d'implémenter des cas de tests Junit afin de tester méthodiquement cette
bibliothèque. Il s'agit de mettre en œuvre les techniques étudiées en R2.03, en particulier :

- conception de tests unitaires par approche fonctionnelle,
- analyse de la testabilité,
- implémentation de tests unitaires,
- (diagnostic)

## Livrables attendus

#### Cas de tests Junit

Vous mettrez sous test la librairie `iut.info1.flip7-1.x.jar`, en particulier 

1. les différentes classes représentant les cartes du jeu Flip7
2. toutes les méthodes de la classe `OutilsCarte`
3. le constructeur et les méthodes de la classe `Flip7`

Vous implémenterez dans `src/test/kotlin/` tous les cas de tests Junit nécessaire.

> [!NOTE]
> Nous avons de notre côté presque **400** cas de tests pour "tester" la bibliothèque ; on en vous demande pas autant, mais ca vous donne une idée

Il est bien possible que vous trouviez des bugs. N'hésitez pas à nous les remonter via Mattermost ; ce sera
bonifié !

> [!IMPORTANT]
> Remonter un bug consiste à partager un cas de test qui échoue, suffisamment documenté pour comprendre pourquoi.

Le dépôt Gitlab devra contenir tout le code de vos cas de tests.

#### Rapport de test

Vous rendrez dans le dossier `livrables/test/` de votre dépôt Git un rapport de test, **exclusivement** au
format [Markdown](https://fr.wikipedia.org/wiki/Markdown) (sans fioriture), dans un fichier nommé `rapport.md`

> [!TIP]
> Markdown est un format largement suffisant pour ce qui vous est demandé, et contrairement à Word & co, ce n'est que du
> code ;-)

Ce rapport de tests servira à documenter l'implémentation de vos cas de tests ; un client ne vous croira pas sur
parole : « avez-vous testé ? », « oui », « comment ? » ; nous attendons donc que vous rendiez un document court mais
synthétique commentant

- la conception de tests unitaires par approche fonctionnelle (donc quelques tables de décisions, quelques scénarios de
  test couvrant des séquences),

- l'analyse de la testabilité (en discutant les heuristiques de testabilité)

- l'implémentation de tests unitaires (des commentaires sur le code source avec l'intention des cas de test et des
  oracles)

#### Revue de tests

La revue de tests sera l'occasion d'évaluer la pertinence et la qualité des cas de tests que vous aurez proposés pour
tester la bibliothèque, mais aussi d'échanger sur le contenu de votre rapport de test.







<pre style="background: transparent; border: none; padding: 0; margin: 0; font-family: monospace;">

        ________    ________     _____
       / ____/ /   /  _/ __ \   /__  /
      / /_  / /    / // /_/ /     / / 
     / __/ / /____/ // ____/     / /  
    /_/   /_____/___/_/         /_/   

</pre>

**Equipe-34** : Arthur Sillard, Kevin Phomasone, Noé Kerloch, Kris Fouda, Léo Guillez

## Télécharger le jeu -> à terminer

### En téléchargant le fichier zip (plus simple)

Cliquer sur `Code` puis `zip`

Sur votre machine, déziper l'archive.

### En clonant le projet (Si vous faites partie de l'IUT)

Afin de clôner le projet via git, il faut d'abord s'assurer de l'avoir d'installé sur votre machine. Ouvrir un terminal, puis rentrer la commande suivante :

```shell
git --version
```

Si le terminal renvoie `git: command not found`, suivre le tutoriel suivant :

## Tuto d'installation git en fonction de l'os

### Via le terminal :

Ouvrir votre terminal, puis vous rendre dans le dossier du jeu :

```shell
cd chemin/vers/le/dépôt/equipe-34
```

**Pour les distributions basées sur RFE / Fedora / CentOS :**

```shell
sudo dnf install git -y
```
    
### Cloner le projet

Une fois Git installé, ouvrez votre terminal, placez-vous dans le dossier où vous souhaitez enregistrer le jeu, puis lancez la commande suivante :


```shell
git clone https://gitlab.univ-nantes.fr/iut.info1.dev.objets/sae201/sae201.2026/equipes/equipe-34.git
```

Puis rentrer vos identifiants.

# Configurer le fichier equipe-34/gradle.properties

Si vous etes connecté sur la connexion à l'iut : 

	kotlin.code.style=official
	org.gradle.parallel=true
	systemProp.http.proxyHost=srv-proxy-etu-2.iut-nantes.univ-nantes.prive
	systemProp.http.proxyPort=3128
	systemProp.https.proxyHost=srv-proxy-etu-2.iut-nantes.univ-nantes.prive
	systemProp.https.proxyPort=3128
	systemProp.http.nonProxyHosts=localhost|nexus.dep-info.iut-nantes.univ-nantes.prive
	systemProp.https.nonProxyHosts=localhost|nexus.dep-info.iut-nantes.univ-nantes.prive

Autrement : 

	kotlin.code.style=official
	org.gradle.parallel=true

# Configurer le fichier equipe-34/build.gradle.kts

**Reperer la ligne avec**


    repositories { 


Si vous etes connecté sur la connexion à l'iut : 

	repositories {
		maven {
		    url = uri("http://nexus.dep-info.iut-nantes.univ-nantes.prive/repository/public/")
		    isAllowInsecureProtocol = true
		}

		gradlePluginPortal()
		mavenCentral()
	}


Autrement : 

	repositories {
	//    maven {
	//        url = uri("http://nexus.dep-info.iut-nantes.univ-nantes.prive/repository/public/")
	//        isAllowInsecureProtocol = true
	//    }

		gradlePluginPortal()
		mavenCentral()
	}

# Configurer le fichier equipe-34/settings.gradle.kts

**Reperer la ligne avec**

    repositories { 


Si vous etes connecté sur la connexion à l'iut : 

	repositories {
		maven {
		    url = uri("http://nexus.dep-info.iut-nantes.univ-nantes.prive/repository/public/")
		    isAllowInsecureProtocol = true
		}

		gradlePluginPortal()
		mavenCentral()
	}


Autrement : 

	repositories {
	//    maven {
	//        url = uri("http://nexus.dep-info.iut-nantes.univ-nantes.prive/repository/public/")
	//        isAllowInsecureProtocol = true
	//    }

		gradlePluginPortal()
		mavenCentral()
	}


# Lancer le jeu
Depuis le terminal

Ouvrez votre terminal et déplacez-vous dans le dossier du projet que vous venez de récupérer :


```shell
cd chemin/vers/le/dossier/equipe-34
```

Puis lancer la compilation et l'exécution via gradle :

```shell
./gradlew run
```
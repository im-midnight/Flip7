from PIL import Image, ImageDraw
import os

# ==========================================
# 1. Configuration
# ==========================================
IMAGE_PATH = "toutes_cartes_flip7.webp"
OUTPUT_DIR = "cartes_decoupees"  # Retour au dossier d'origine
COLONNES = 7
LIGNES = 4

# --- AMÉLIORATION DE LA RÉSOLUTION ---
FACTEUR_RESOLUTION = 2.0

# --- PARAMÈTRE DE L'ARRONDI ---
RAYON_ARRONDI = 10

NUMEROS_CARTES = [
    "0", "1", "10", "11", "12", "2", "+8",
    "3", "4", "5", "6", "7", "8", "2ndeChance",
    "9", "dosFlip7", "+10", "+2", "+4", "+6", "stop",
    "3suite", "x2"
]

os.makedirs(OUTPUT_DIR, exist_ok=True)


# ==========================================
# 2. Fonction : Masque Arrondi Anti-Crénelé
# ==========================================
def creer_masque_arrondi_lisse(taille, rayon):
    facteur_anti_crenelage = 4
    taille_haute_res = (taille[0] * facteur_anti_crenelage, taille[1] * facteur_anti_crenelage)
    rayon_haut_res = rayon * facteur_anti_crenelage

    masque_geant = Image.new('L', taille_haute_res, 0)
    draw = ImageDraw.Draw(masque_geant)
    draw.rounded_rectangle((0, 0, taille_haute_res[0], taille_haute_res[1]), radius=rayon_haut_res, fill=255)

    # Filtre de haute qualité LANCZOS pour créer le flou de lissage sur les bords
    return masque_geant.resize(taille, Image.Resampling.LANCZOS)


# ==========================================
# 3. Traitement principal
# ==========================================
img = Image.open(IMAGE_PATH).convert("RGBA")
largeur_totale, hauteur_totale = img.size

largeur_base = largeur_totale // COLONNES
hauteur_base = hauteur_totale // LIGNES

largeur_haute_res = int(largeur_base * FACTEUR_RESOLUTION)
hauteur_haute_res = int(hauteur_base * FACTEUR_RESOLUTION)
nouvelle_taille_carte = (largeur_haute_res, hauteur_haute_res)

print(f"Traitement en cours... Nouvelle dimension PNG : {largeur_haute_res}x{hauteur_haute_res} px")

masque_arrondi = creer_masque_arrondi_lisse(nouvelle_taille_carte, RAYON_ARRONDI)

index = 0
total_cartes_a_faire = len(NUMEROS_CARTES)

for l in range(LIGNES):
    for c in range(COLONNES):
        if index >= total_cartes_a_faire:
            break

        gauche = c * largeur_base
        haut = l * hauteur_base
        droite = gauche + largeur_base
        bas = haut + hauteur_base

        carte_rognee = img.crop((gauche, haut, droite, bas))

        # Redimensionnement HD de la carte
        carte_haute_res = carte_rognee.resize(nouvelle_taille_carte, Image.Resampling.LANCZOS)

        # Application du masque transparent
        carte_finale = Image.new("RGBA", nouvelle_taille_carte, (0, 0, 0, 0))
        carte_finale.paste(carte_haute_res, (0, 0), mask=masque_arrondi)

        # Sauvegarde en PNG (écrase automatiquement l'ancien fichier s'il existe)
        num_carte = NUMEROS_CARTES[index]
        chemin_sortie = f"{OUTPUT_DIR}/carte_{num_carte}.png"

        carte_finale.save(chemin_sortie, "PNG")

        index += 1
    else:
        continue
    break

print(f"Terminé ! {index} cartes PNG générées et lissées dans '{OUTPUT_DIR}'.")
package com.example.monprofil

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Acteur(navController: NavController,
           viewmodel: MainViewModel,
           windowSizeClass: WindowSizeClass
) {
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally) {
                DescriptionActeur(viewmodel, 2);
            }
        }
        else -> {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly) {
                DescriptionActeur(viewmodel, 3);
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition", "SimpleDateFormat")
@Composable
fun DescriptionActeur(viewmodel: MainViewModel,
                    nbColonne: Int
) {
    val acteur: ModelActeurOnly by viewmodel.actor.collectAsState();
    val idActeur by viewmodel.acteur;
    var departement = acteur.known_for_department;
    val taille: Modifier;
    val scrollState = rememberScrollState(0);
    val profile = "https://image.tmdb.org/t/p/w500" + acteur.profile_path;
    val genre = acteur.gender;
    val debutNe: String;
    val debutDecede: String;

    if(departement != null){
        if(departement == "Acting")  departement = "Interprétation";
        if(departement == "Actors")  departement = "Artistes";
        if(departement == "Art")  departement = "Artistique";
        if(departement == "Camera")  departement = "Image";
        if(departement == "Costume & Make-Up")  departement = "Costumes et maquillage";
        if(departement == "Creator")  departement = "Création";
        if(departement == "Crew")  departement = "Équipe technique";
        if(departement == "Directing")  departement = "Réalisation";
        if(departement == "Editing")  departement = "Montage";
        if(departement == "Lighting")  departement = "Éclairage";
        if(departement == "Production")  departement = "Production";
        if(departement == "Sound")  departement = "Son";
        if(departement == "Visual Effects")  departement = "Effets visuels";
        if(departement == "Writing")  departement = "Écriture";
    }

    viewmodel.getActeur(idActeur);

    if(nbColonne == 3) {
        taille = Modifier
            .padding(top = 50.dp)
    }
    else{
        taille = Modifier;
    }

    if(genre == 1){
        debutNe = "Née ";
    }
    else{
        debutNe = "Né ";
    }

    if(genre == 1){
        debutDecede = "Décédée le ";
    }
    else{
        debutDecede = "Décédé le ";
    }

    Column(modifier = taille
        .verticalScroll(scrollState)
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (acteur.profile_path != null) {
            Image(
                painter = rememberAsyncImagePainter(profile),
                contentDescription = "Poster",
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(16.dp))
            );
        }
        Text(
            text = acteur.name,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        );
        Text(
            text = departement,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center
        );
        ListeAussiConnuComme(acteur, genre);
        Spacer(modifier = Modifier.size(10.dp));
        if(acteur.birthday != null || acteur.place_of_birth != null){
            val dateNaissance = try{
                val dateN = SimpleDateFormat("yyyy-MM-dd").parse(acteur.birthday);
                DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE).format(dateN);
            }
            catch(e : java.lang.Exception){
                acteur.birthday
            }

                Text(text = debutNe +
                    buildAnnotatedString {
                withStyle(
                    style = SpanStyle(fontStyle = FontStyle.Italic
                    )
                ) {

                    if(acteur.birthday != null) {
                        append("le " + dateNaissance + " ");
                    }
                    if(acteur.place_of_birth != null) {

                        append("à " + acteur.place_of_birth);
                    }
                }
            },
                textAlign = TextAlign.Center
            );
            Spacer(modifier = Modifier.size(10.dp));
        }
        if(acteur.deathday != null) {
            val dateDeces = try{
                val dateD = SimpleDateFormat("yyyy-MM-dd").parse(acteur.birthday);
                DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE).format(dateD);
            }
            catch(e : java.lang.Exception){
                acteur.deathday
            }

            Text(
                text = debutDecede + dateDeces,
                textAlign = TextAlign.Center
            );
            Spacer(modifier = Modifier.size(10.dp));
        }
    }
}

@Composable
fun ListeAussiConnuComme(acteur: ModelActeurOnly, genre: Int){
    val listeAaussiConnuComme = acteur.also_known_as;
    val debutConnu: String;

    if(genre == 1){
        debutConnu = "Aussi connue comme ";
    }
    else{
        debutConnu = "Aussi connu comme ";
    }

    if(listeAaussiConnuComme.isNotEmpty()){
        Text(text = debutConnu,
            fontStyle = FontStyle.Italic
        );
        (listeAaussiConnuComme).forEach() { surnom ->
            Text(text =
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(fontStyle = FontStyle.Italic
                        )
                    ) {
                        append(surnom);
                        if (listeAaussiConnuComme.last() != surnom) {
                            append(", ");
                        }
                    }
                }
            )
        }
    }
}
package br.com.movieapp.movie_favorite_feature.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.movieapp.R
import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.core.presentation.components.common.AsyncImageUrl
import br.com.movieapp.ui.theme.black
import br.com.movieapp.ui.theme.white
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.ImageResult

@Composable
fun MovieFavoriteItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    onClick: (id: Int) -> Unit
    ) {

    Card (
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(movie.id)
            }
    ){
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .background(black),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)){

                AsyncImageUrl(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    imageUrl = movie.imageUrl,
                    contentScale = ContentScale.FillWidth
                )
            }
            Text(
                text = movie.title,
                maxLines = 1,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = white,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun MovieFavoriteItemPreview() {
    MovieFavoriteItem(
        movie =  Movie(
            id = 1,
            title = "Homem Aranha",
            voteAvarage = 7.89,
            imageUrl = ""
        ),
        onClick = {

        }
    )
}
package com.example.lab6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.lab6.ui.screens.CinemaSeatBookingScreen
import com.example.lab6.ui.screens.createTheaterSeating
import com.example.lab6.ui.theme.Lab6Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab6Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Bai1(Movie.getSampleMovies())
//                    Bai2()
                    CinemaSeatBookingScreen(
                        createTheaterSeating(
                            totalRows = 12,
                            totalSeatsPerRow = 9,
                            aislePositionInRow = 4,
                            aislePositionInColumn = 5
                        ), totalSeatsPerRow = 9
                    )
                }
            }
        }
    }
}


//Bai1
@Composable
fun Bai1(movies:List<Movie>){
    LazyRow(
        state = rememberLazyListState(),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movies.size){
            index ->
            MovieItem(movie = movies[index])
        }
    }
}

@Composable
fun MovieItem(movie: Movie){
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6 .dp)
    ) {
        Column(
            modifier = Modifier.size(175.dp, 330.dp)
        ) {
            AsyncImage(model = movie.postURL,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(255.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp)))
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = movie.title, style = MaterialTheme.typography.titleSmall, maxLines = 2)
                Text(text = "Thời lượng ${movie.year}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}


//Bai2
@Composable
fun Bai2(){
    Column {
        var listType by remember { mutableStateOf(ListType.ROW) }
        Row(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = { listType=ListType.ROW }) {
                Text(text = "Row")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { listType=ListType.COMLUMN }) {
                Text(text = "Column")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {listType=ListType.GRID }) {
                Text(text = "Grid")
            }
        }
        when(listType){
            ListType.ROW-> MovieRow(movies = Movie.getSampleMovies())
            ListType.COMLUMN-> MovieColumn(movies = Movie.getSampleMovies())
            ListType.GRID-> MovieGrid(movies = Movie.getSampleMovies())

        }
    }
}

@Composable
fun MovieColumn(movies: List<Movie>){
    LazyColumn(
        state = rememberLazyListState(),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movies.size){
            index ->
            MovieItemColumn(movie = movies[index], listType = ListType.COMLUMN)
        }
    }
}

@Composable
fun MovieRow(movies: List<Movie>){
    LazyRow(
        state = rememberLazyListState(),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movies.size){
            index ->
            MovieItemRowGrid(movie = movies[index], listType = ListType.ROW)
        }
    }
}

@Composable
fun MovieGrid(movies: List<Movie>){
    val gridState= rememberLazyStaggeredGridState()
    LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2),
        state = gridState,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalItemSpacing = 8.dp,
        contentPadding = PaddingValues(8.dp)
    ) {
        items(movies.size){
            index -> 
            MovieItemRowGrid(movie = movies[index], listType = ListType.GRID)
        }
    }
}
@Composable
fun MovieItemRowGrid(movie: Movie, listType: ListType){
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.then(getItemSizeModifier(listType = listType))
        ) {
            AsyncImage(model = movie.postURL,
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth())

            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = movie.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2, 
                    overflow = TextOverflow.Ellipsis)
                
                BoldValueText(label = "Thời lượng", value = movie.year)
                BoldValueText(label = "Khởi chiếu", value = movie.year)
            }
        }
    }
}

@Composable
fun MovieItemColumn(movie: Movie, listType: ListType){
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(model = movie.postURL,
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .then(
                        getItemSizeModifier(listType = listType)
                    )
                    .wrapContentHeight())
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = movie.title, style = MaterialTheme.typography.titleSmall, maxLines = 2, overflow = TextOverflow.Ellipsis)
                BoldValueText(label = "Thời lượng", value = movie.year)
                BoldValueText(label = "Khởi chiếu", value = movie.year)
                BoldValueText(label = "Thể loại", value = movie.year)
                Text(text = "Tóm tắt nội dung",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp, bottom = 2.dp))

                Text(text = movie.title,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(end = 2.dp))
            }
        }
    }
}

@Composable
fun BoldValueText(label:String, value:String, style:TextStyle=MaterialTheme.typography.bodySmall){
    Text(buildAnnotatedString {
        append(label)
        withStyle(style= SpanStyle(fontWeight = FontWeight.Bold)){
            append(value)
        }
    }, style = style)
}

@Composable
private fun getItemSizeModifier(listType: ListType):Modifier{
    return when(listType){
        ListType.ROW->Modifier.width(175.dp)
        ListType.COMLUMN->Modifier.width(130.dp)
        ListType.GRID->Modifier.fillMaxWidth()
    }
}



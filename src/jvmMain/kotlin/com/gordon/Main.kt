package com.gordon
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

//
//@OptIn(ExperimentalPagerApi::class, ExperimentalCoilApi::class)
//@Composable
//fun HorizontalPagerWithOffsetTransition(modifier: Modifier = Modifier) {
//    HorizontalPager(
//        count = 10,
//        // Add 32.dp horizontal padding to 'center' the pages
//        contentPadding = PaddingValues(horizontal = 32.dp),
//        modifier = modifier.fillMaxSize()
//    ) { page ->
//        Card(
//            Modifier
//                .graphicsLayer {
//                    // Calculate the absolute offset for the current page from the
//                    // scroll position. We use the absolute value which allows us to mirror
//                    // any effects for both directions
//                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
//
//                    // We animate the scaleX + scaleY, between 85% and 100%
//                    lerp(
//                        start = 0.85f,
//                        stop = 1f,
//                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
//                    ).also { scale ->
//                        scaleX = scale
//                        scaleY = scale
//                    }
//
//                    // We animate the alpha, between 50% and 100%
//                    alpha = lerp(
//                        start = 0.5f,
//                        stop = 1f,
//                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
//                    )
//                }
//                .fillMaxWidth()
//                .aspectRatio(1f)
//        ) {
//            Box {
//                Image(
//                    painter = rememberImagePainter(
//                        data = rememberRandomSampleImageUrl(width = 500),
//                    ),
//                    contentDescription = null,
//                    modifier = Modifier.fillMaxSize(),
//                )
//
//                ProfilePicture(
//                    Modifier
//                        .align(Alignment.BottomCenter)
//                        .padding(16.dp)
//                        // We add an offset lambda, to apply a light parallax effect
//                        .offset {
//                            // Calculate the offset for the current page from the
//                            // scroll position
//                            val pageOffset =
//                                this@HorizontalPager.calculateCurrentOffsetForPage(page)
//                            // Then use it as a multiplier to apply an offset
//                            IntOffset(
//                                x = (36.dp * pageOffset).roundToPx(),
//                                y = 30
//                            )
//                        }
//                )
//            }
//        }
//    }
//}
//@OptIn(ExperimentalCoilApi::class)
//@Composable
//private fun ProfilePicture(modifier: Modifier = Modifier) {
//    Card(
//        modifier = modifier,
//        shape = CircleShape,
//        border = BorderStroke(2.dp, MaterialTheme.colors.surface)
//    ) {
//        Image(
//            painter = rememberImagePainter(rememberRandomSampleImageUrl()),
//            contentDescription = null,
//            modifier = Modifier.size(50.dp),
//        )
//    }
//}


@Composable
@Preview
fun App() {
    MaterialTheme {
        Scaffold(
             topBar = {topBar("Crypto Util")},
        ) {
//            content()
           Box(modifier = Modifier.background(Color.Gray)){
               tabLayout()
           }
        }
    }
}

fun main() = application {
    Window(
        title = "CryptoUtil",
        onCloseRequest = ::exitApplication) {
        App()
    }
}

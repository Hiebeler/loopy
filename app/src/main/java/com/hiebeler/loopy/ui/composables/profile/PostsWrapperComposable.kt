package com.hiebeler.loopy.ui.composables.profile


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hiebeler.loopy.domain.model.Post
import com.hiebeler.loopy.domain.model.Wrapper
import com.hiebeler.loopy.ui.composables.post.SmallPost

fun LazyListScope.PostsWrapperComposable(
    feedWrapper: Wrapper<Post>?,
    navController: NavController
) {
    if (feedWrapper != null) {
        val rows = feedWrapper.data.chunked(3)
        items(rows) { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 1.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                rowItems.forEachIndexed { index, post ->

                    val isTopLeft = rowItems == rows.first() && index == 0
                    val isTopRight = rowItems == rows.first() && index == 2
                    val isBottomLeft = rowItems == rows.last() && index == 0
                    val isBottomRight = rowItems == rows.last() && (index == 2 || index == rowItems.size - 1)

                    var roundedCorners: Modifier = Modifier

                    if (isTopLeft) {
                        roundedCorners = roundedCorners.clip(RoundedCornerShape(topStart = 16.dp))
                    }
                    if (isBottomLeft) {
                        roundedCorners = roundedCorners.clip(RoundedCornerShape(bottomStart = 16.dp))
                    }
                    if (isTopRight) {
                        roundedCorners = roundedCorners.clip(RoundedCornerShape(topEnd = 16.dp))
                    }
                    if (isBottomRight) {
                        roundedCorners = roundedCorners.clip(RoundedCornerShape(bottomEnd = 16.dp))
                    }

                    Box(
                        Modifier
                            .padding(horizontal = 2.dp)
                            .weight(1f)
                    ) {

                        SmallPost(
                            post = post, navController = navController, modifier = roundedCorners
                        )
                    }
                }

                repeat(3 - rowItems.size) {
                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 2.dp)
                    )
                }
            }
        }
    }
}
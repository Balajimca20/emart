package com.example.emart.ui.view.productscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.emart.R
import com.example.emart.data.model.ProductResponseItem


@Composable
fun NormalText(title: String, color: Color) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = title,
        color = color,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun Rating(title: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier.size(12.dp),
            imageVector = Icons.Filled.Star,
            contentDescription = "Star",
            tint = colorResource(id = R.color.rating_color),
        )
        Text(
            text = title,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = color,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }

}

@Composable
fun BoldText(title: String, color: Color) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = color,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun FilterOption(onClickAction: () -> Unit, isGridView: Boolean) {

    IconButton(
        modifier = Modifier.background(
            shape = RoundedCornerShape(5.dp),
            color = Color.Black
        ),
        onClick = {
            onClickAction()
        },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = colorResource(id = R.color.text_color),
            contentColor = colorResource(id = R.color.white)
        ),
    ) {
        Icon(
            imageVector = if (isGridView) ImageVector.vectorResource(id = R.drawable.grid_view_24px) else
                ImageVector.vectorResource(id = R.drawable.page_info_24px), contentDescription = ""
        )
    }
}

@Composable
fun ProductItem(item: ProductResponseItem, rupeeSymbol: String, isGridView: Boolean) {
    Card(
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            if (isGridView) {
                CommonImageView(item.image)
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = if (isGridView) 8.dp else 0.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = if (isGridView) Alignment.Start else Alignment.CenterHorizontally
            ) {
                if (!isGridView) {
                    CommonImageView(item.image)
                }

                BoldText(title = item.title, color = colorResource(id = R.color.text_color))
                NormalText(title = item.description, color = Color.LightGray)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BoldText(
                        title = "$rupeeSymbol ${item.price}",
                        color = colorResource(id = R.color.text_color)
                    )
                    Rating(
                        item.rating.rate.toString(),
                        color = colorResource(id = R.color.text_color)
                    )
                }
            }
        }
    }
}


@Composable
fun CommonImageView(image: String) {
    AsyncImage(
        model = image,
        contentDescription = "backdrop_path",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .height(96.dp)
            .width(96.dp),
    )
}


@Composable
fun EmptyScreen(onClickReload: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = onClickReload,
                modifier = Modifier.size(60.dp) // Applied size here
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = stringResource(id = R.string.refresh)
                )
            }

            Spacer(modifier = Modifier.height(5.dp))
            Text(text = stringResource(id = R.string.no_data_found))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    EmptyScreen(
        onClickReload = {}
    )
}


@Composable
fun SearchBar(
    value: String,
    onSearchValueChanged: (String) -> Unit,
    clearText: Boolean,
    onDismissClearText: (Boolean) -> Unit,
    onDismiss: () -> Unit,
    onClickAction: () -> Unit,
    isGridView: Boolean,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            value = value.takeIf { !clearText } ?: "",
            onValueChange = {
                onDismissClearText(false)
                onSearchValueChanged(it)
            },
            textStyle = TextStyle(fontWeight = FontWeight.Bold),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(id = R.string.search),
                    tint = Color.LightGray
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        onDismiss()
                        onDismissClearText(true)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(id = R.string.close_icon),
                        tint = Color.LightGray
                    )
                }
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search),
                    color = Color.LightGray
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            )
        )
        FilterOption(onClickAction = onClickAction, isGridView = isGridView)
    }
}

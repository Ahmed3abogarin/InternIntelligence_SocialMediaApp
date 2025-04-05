package com.ahmed.instagramclone.presentation.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahmed.instagramclone.R
import com.ahmed.instagramclone.ui.theme.InstagramCloneTheme
import com.ahmed.instagramclone.util.Dimens.IconSize

@Composable
fun AppSearchBar(
    modifier: Modifier = Modifier,
    text: String,
    readOnly: Boolean = false,
    onClick: (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
) {

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val isClicked = interactionSource.collectIsPressedAsState().value
    LaunchedEffect(key1 = isClicked) {
        if (isClicked) {
            onClick?.invoke()
        }

    }

    Box(modifier = modifier) {
        TextField(
            modifier = Modifier
                .fillMaxWidth().height(45.dp),
            value = text,
            readOnly = readOnly,
            onValueChange = onValueChange,
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(IconSize),
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    tint = Color.Black
                )
            },
            placeholder = {
                Text(
                    text = "Search",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            },
            shape = RoundedCornerShape(4.dp),
            colors = TextFieldDefaults.colors(
                disabledTextColor =  Color.Black,
                cursorColor = Color.Black,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions (
                onSearch = {
                    onSearch()
                }
            ),
            textStyle = MaterialTheme.typography.bodySmall,
            interactionSource = interactionSource
        )
    }

}


@Preview
@Composable
fun Search2Preview(){
    InstagramCloneTheme {
        AppSearchBar(
            text ="",
            onValueChange = {},
            onSearch = {}
        )
    }
}
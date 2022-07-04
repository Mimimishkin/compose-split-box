import androidx.compose.foundation.gestures.Orientation.Horizontal
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalSplitPane(
    modifier: Modifier = Modifier,
    state: SplitBoxState = SplitBoxState(2),
    firstMinSize: Dp = 0.dp,
    first: ComposableFun,
    secondMinSize: Dp = 0.dp,
    second: ComposableFun,
    divider: ComposableFun = defaultDivider(Horizontal),
    handlerSize: Dp = 16.dp,
) = SplitBox(
    orientation = Horizontal,
    state = state,
    modifier = modifier,
    components = listOf(firstMinSize to first, secondMinSize to second),
    divider = divider,
    handlerSize = handlerSize
)
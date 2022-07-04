import androidx.compose.foundation.gestures.Orientation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SplitLine(
    vararg line: ComposableFun,
    state: SplitBoxState = SplitBoxState(line.size),
    minSize: Dp = 0.dp,
    modifier: Modifier = Modifier,
    divider: ComposableFun = defaultDivider(Orientation.Horizontal),
    handlerSize: Dp = 16.dp,
) = SplitBox(
    orientation = Orientation.Horizontal,
    state = state,
    modifier = modifier,
    components = line.map { minSize to it },
    divider = divider,
    handlerSize = handlerSize
)
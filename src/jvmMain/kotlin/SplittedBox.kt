import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.Orientation.Horizontal
import androidx.compose.foundation.gestures.Orientation.Vertical
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.indication
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.awt.Cursor
import java.awt.Cursor.E_RESIZE_CURSOR
import java.awt.Cursor.S_RESIZE_CURSOR

internal typealias ComposableFun = @Composable () -> Unit

val defaultDivider: @Composable (Orientation) -> ComposableFun = @Composable {
    when(it) {
        Vertical -> (@Composable { Divider(Modifier.fillMaxWidth().height(1.dp)) })
        Horizontal -> (@Composable { Divider(Modifier.fillMaxHeight().width(1.dp)) })
    }
}

class SplittedBoxState(private val count: Int) {
    var resizable by mutableStateOf(true)

    val percentageSizes = List(count) { 1f / count }.toMutableStateList()
}

@Composable
fun SplittedBox(
    orientation: Orientation,
    components: List<Pair<Dp, ComposableFun>>,
    state: SplittedBoxState = remember { SplittedBoxState(components.size) },
    modifier: Modifier = Modifier,
    divider: ComposableFun = defaultDivider(orientation),
    handlerSize: Dp = 16.dp,
) = with(state) {
    BoxWithConstraints(modifier) {
        val density = LocalDensity.current
        val vertical = orientation == Vertical
        val count = components.size

        val size = with(density) { (if (vertical) maxHeight else maxWidth).toPx() }
        var dividerSize by remember { mutableStateOf(0f) }

        fun Float.pxToPercent() = this / (size - dividerSize * (count - 1))

        abstract class ContainerScope {
            abstract fun Modifier.weight(weight: Float): Modifier
        }

        @Composable
        fun Container(modifier: Modifier = Modifier, content: @Composable ContainerScope.() -> Unit) {
            if (vertical) {
                Column(modifier) {
                    val scope = this
                    content(object : ContainerScope() {
                        override fun Modifier.weight(weight: Float): Modifier {
                            return with(scope) { weight(weight) }
                        }
                    })
                }
            } else {
                Row(modifier) {
                    val scope = this
                    content(object : ContainerScope() {
                        override fun Modifier.weight(weight: Float): Modifier {
                            return with(scope) { weight(weight) }
                        }
                    })
                }
            }
        }

        @Composable
        fun Handler(index: Int) {
            Box(Modifier
                .draggable(
                    orientation = orientation,
                    state = rememberDraggableState { delta ->
                        if (resizable) {
                            val percent = delta.pxToPercent()

                            val firstMinSize = with(density) { components[index].first.toPx() }
                            val secondMinSize = with(density) { components[index + 1].first.toPx() }

                            if (
                                percentageSizes[index] + percent >= firstMinSize.pxToPercent() &&
                                percentageSizes[index + 1] - percent >= secondMinSize.pxToPercent()
                            ) {
                                percentageSizes[index] += percent
                                percentageSizes[index + 1] -= percent
                            }
                        }
                    },
                    startDragImmediately = true,
                )
                .pointerHoverIcon(PointerIcon(
                    Cursor(if (orientation == Vertical) S_RESIZE_CURSOR else E_RESIZE_CURSOR)
                ))
                .then(
                    if (vertical) {
                        Modifier.fillMaxWidth().height(handlerSize)
                    } else {
                        Modifier.fillMaxHeight().width(handlerSize)
                    }
                )
            )
        }

        Container(Modifier.fillMaxSize()) {
            for (index in components.indices) {
                Box(Modifier.weight(percentageSizes[index])) {
                    components[index].second()
                }
                if (index != components.lastIndex) {
                    Box(Modifier.onSizeChanged {
                        dividerSize = (if (vertical) it.height else it.width).toFloat()
                    }) {
                        divider()
                    }

                    if (resizable) {
                        Box(Modifier.offset(x = -handlerSize / 2).pseudoNullSize()) {
                            Handler(index)
                        }
                    }
                }
            }
        }
    }
}

private fun Modifier.pseudoNullSize() = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    layout(0, 0) {
        placeable.place(0, 0)
    }
}


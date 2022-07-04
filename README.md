# Compose split box
[![](https://jitpack.io/v/Mimimishkin/compose-split-box.svg)](https://jitpack.io/#Mimimishkin/compose-split-box)

Simple vertical and horizontal split elements:

###### SplitBox (base component) interface:

    SplitBox(
        orientation: Orientation,
        state: SplittedBoxState, // state for disable resizing and control splitting 
        components: List<Pair<Dp, ComposableFun>>, // min sizes and components
        modifier: Modifier = Modifier,
        divider: ComposableFun = defaultDivider(orientation),
        handlerSize: Dp = 16.dp,
    )

###### VerticalSplitPane & HorizontalSplitPane:

    VerticalSplitPane(
        firstMinSize = 120.dp,
        first = {
            Text("First")
        },
        secondMinSize = 120.dp,
        second = {
            Text("Second")
        },
        divider = { 
            Divider()
        }
    )
    
###### SplitLine (e.g. for table header) :

    SplitLine(
        { Text("First") },
        { Text("Second") },
        { Text("Third") },
        handlerSize = 16.dp
    )

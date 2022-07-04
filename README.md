# Compose splitted box
Simple vertical and horizontal splitted elements:

###### SplittedBox (base component) interface:

    SplittedBox(
        orientation: Orientation,
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
    
###### SplittedLine (e.g. for table header) :

    SplittedLine(
        { Text("First") },
        { Text("Second") },
        { Text("Third") },
        handlerSize = 16.dp
    )
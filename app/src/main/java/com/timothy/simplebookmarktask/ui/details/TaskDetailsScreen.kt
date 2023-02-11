package com.timothy.simplebookmarktask.ui.details

import android.app.Application
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.timothy.simplebookmarktask.R
import com.timothy.simplebookmarktask.config.ColorPalette
import com.timothy.simplebookmarktask.config.Constants
import com.timothy.simplebookmarktask.domain.models.TaskItemModel
import com.timothy.simplebookmarktask.ui.navigation.navigateToConfigTaskListScreen
import com.timothy.simplebookmarktask.ui.theme.ColorTheme
import com.timothy.simplebookmarktask.utilities.GradientPositions
import com.timothy.simplebookmarktask.utilities.TImerUtils.Companion.formatTime
import com.timothy.simplebookmarktask.utilities.TImerUtils.Companion.getElapsedMinutes
import com.timothy.simplebookmarktask.utilities.TImerUtils.Companion.getRemainingMinutes
import com.timothy.simplebookmarktask.utilities.TImerUtils.Companion.toMinutes
import com.timothy.simplebookmarktask.utilities.gradientBackground
import kotlinx.coroutines.delay
import java.lang.Math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun TaskDetailsScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val application = context.applicationContext as Application

    val viewModel: TaskDetailsScreenViewModel = viewModel(
        factory = TaskDetailsViewModelFactory(application, navController)
    )
    //Link sa Notif at timer
    //TODO https://www.youtube.com/watch?v=JflJjPxhFQo&t=1942s

    val configIdentifier = viewModel.configIdentifier.value
    val taskItem = viewModel.taskItem.value

    LaunchedEffect(Unit){
        taskItem?.id?.let {
            viewModel.getTaskItemByIdFromDB(it)
        }
    }

        TaskDetailsScreenContent(
            modifier = Modifier,
            taskItem = taskItem,
            gotoEditPressed = {
                navigateToConfigTaskListScreen(
                    navController = navController,
                    configIdentifier = Constants.ConfigIdentifier.UPDATE_TASK_KEY,
                    taskItemId = taskItem?.id
                )
            },
            backNavPressed = {
                navController.popBackStack()
            }
        )

}

@Composable
fun TaskDetailsScreenContent(
    modifier: Modifier,
    taskItem: TaskItemModel?,
    gotoEditPressed: () -> Unit,
    backNavPressed: () -> Unit,
) {
    TaskDetailsScreenStructure(modifier = modifier,
        TopBar = {
            Box(Modifier.fillMaxWidth().padding(8.dp)){
                OutlinedButton(
                    onClick = gotoEditPressed,
                    modifier = Modifier.align(Alignment.CenterEnd).width(150.dp),

                ) {
                    Text(
                        text = stringResource(id = R.string.btn_edit)
                    )
                }
                OutlinedButton(
                    onClick = backNavPressed,
                    modifier = Modifier.align(Alignment.CenterStart).width(150.dp),

                ) {
                    Text(
                        text = stringResource(id = R.string.home_title)
                    )
                }
            }

        },
        BodyContent = {
            BodyContent(taskItem = taskItem)
        }
    )
}

@Composable
private fun TaskDetailsScreenStructure(
    modifier: Modifier,
    TopBar: @Composable () -> Unit,
    BodyContent: @Composable () -> Unit,
) {
    Column(
        modifier
            .fillMaxSize()
            .gradientBackground(
                colors = listOf(
                    ColorTheme.MainColor.darkskyBluer,
                    ColorTheme.MainColor.darkBlue,
                ),
                startPosition = GradientPositions.BOTTOM_START,
                endPosition = GradientPositions.TOP_END
            )
    ) {
        TopBar()
        BodyContent()
    }
}


@Composable
private fun BodyContent(
    modifier: Modifier = Modifier,
    taskItem: TaskItemModel?
) {
    var elapsedTime by remember {
        mutableStateOf("")
    }

    var remainingTime by remember {
        mutableStateOf("")
    }

    Box(
        modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f)
            .padding(dimensionResource(id = R.dimen.default_margin))
    ) {

        Column(Modifier.align(Alignment.Center)) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(70.dp)) {
                Column(modifier = Modifier
                    .align(Alignment.CenterStart)
                    .fillMaxHeight()) {
                    Text(
                        text = stringResource(id = R.string.min_elapsed), /// 1000L for minutes
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = ColorTheme.MainColor.creamWhite
                    )
                    Text(
                        text = elapsedTime, /// 1000L for minutes
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ColorTheme.MainColor.lightRed
                    )
                }
                Column(modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight()) {
                    Text(
                        text = stringResource(id = R.string.min_remaining), /// 1000L for minutes
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = ColorTheme.MainColor.creamWhite
                    )
                    Text(
                        text = remainingTime, /// 1000L for minutes
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ColorTheme.MainColor.lightGreen
                    )
                }
            }
            ProvideTextStyle(value = MaterialTheme.typography.h5) {
                Text(
                    modifier = Modifier
                        .padding(
                            start = 10.dp,
                            top = 10.dp
                        )
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = taskItem?.title.orEmpty(),
                    color = ColorTheme.MainColor.creamWhite,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(45.dp))
            Row(Modifier.align(Alignment.CenterHorizontally)) {
                taskItem?.duration?.let { duration ->
                    Timer(
                        modifier = Modifier
                            .size(200.dp)
                            .align(Alignment.CenterVertically),
                        totalTime = duration.toMinutes(),
                        handleColor = ColorTheme.MainColor.yellowGreen,
                        inactiveBarColor = ColorTheme.MainColor.gray,
                        activeBarColor = ColorTheme.MainColor.lightGreen,
                        remainingMinTime = {remaining ->
                            remainingTime = remaining.getRemainingMinutes()
                            elapsedTime = remaining.getElapsedMinutes(duration.toMinutes())
                        }
                    )
                }
            }
        }
    }
}


// create a composable to
// Draw arc and handle
@Composable
private fun Timer(
    modifier: Modifier = Modifier,
    totalTime: Long,
    handleColor: Color,
    inactiveBarColor: Color,
    activeBarColor: Color,
    initialValue: Float = 1f,
    strokeWidth: Dp = 5.dp,
    remainingMinTime: (Long) -> Unit
) {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    var value by remember {
        mutableStateOf(initialValue)
    }
    var currentTime by remember {
        mutableStateOf(totalTime)
    }
    var isTimerRunning by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
        if (currentTime > 0 && isTimerRunning) {
            delay(100L)
            currentTime -= 100L
            value = currentTime / totalTime.toFloat()
        }
        remainingMinTime(currentTime)
    }
    Column {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .onSizeChanged {
                    size = it
                }
        ) {
            // draw the timer
            Canvas(modifier = modifier) {
                drawArc(
                    color = inactiveBarColor,
                    startAngle = -215f,
                    sweepAngle = 250f,
                    useCenter = false,
                    size = Size(size.width.toFloat(), size.height.toFloat()),
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )
                drawArc(
                    color = activeBarColor,
                    startAngle = -215f,
                    sweepAngle = 250f * value,
                    useCenter = false,
                    size = Size(size.width.toFloat(), size.height.toFloat()),
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )
                // calculate the value from arc pointer position
                val center = Offset(size.width / 2f, size.height / 2f)
                val beta = (250f * value + 145f) * (PI / 180f).toFloat()
                val r = size.width / 2f
                val a = cos(beta) * r
                val b = sin(beta) * r
                // draw the circular pointer/ cap
                drawPoints(
                    listOf(Offset(center.x + a, center.y + b)),
                    pointMode = PointMode.Points,
                    color = handleColor,
                    strokeWidth = (strokeWidth * 3f).toPx(),
                    cap = StrokeCap.Round
                )
            }

            Column {
                Text(
                    text = (currentTime).formatTime(),
                    fontSize = 44.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row {
                    Text(
                        text = stringResource(id = R.string.field_minutes),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(25.dp))
                    Text(
                        text = stringResource(id = R.string.field_seconds),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )
                }
            }

            // create button to start or stop the timer
            /* Button(
                 onClick = {
                     if(currentTime <= 0L) {
                         currentTime = totalTime
                         isTimerRunning = true
                     } else {
                         isTimerRunning = !isTimerRunning
                     }
                 },
                 modifier = Modifier.align(Alignment.BottomCenter),
                 // change button color
                 colors = ButtonDefaults.buttonColors(
                     backgroundColor = if (!isTimerRunning || currentTime <= 0L) {
                         Color.Green
                     } else {
                         Color.Red
                     }
                 )
             ) {
                 Text(
                     // change the text of button based on values
                     text = if (isTimerRunning && currentTime >= 0L) "Stop"
                     else if (!isTimerRunning && currentTime >= 0L) "Start"
                     else "Restart"
                 )
             }*/
        }

        Row(modifier = Modifier) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(5.dp),
                onClick = {
                    if (isTimerRunning && currentTime >= 0L || !isTimerRunning && currentTime >= 0L){
                        currentTime = totalTime
                        isTimerRunning = false
                        remainingMinTime(totalTime)
                    }
                }
            ) {
                Icon(
                    modifier = Modifier.size(45.dp),
                    painter = painterResource(id = R.drawable.ic_stop),
                    contentDescription = "",
                    tint = ColorTheme.MainColor.darkRed
                )
            }
            IconButton(
                modifier = Modifier
                    .padding(5.dp),
                onClick = {
                    if (currentTime <= 0L) {
                        currentTime = totalTime
                        isTimerRunning = true
                    } else {
                        isTimerRunning = !isTimerRunning
                    }
                }
            ) {
                Icon(
                    modifier = Modifier.size(125.dp),
                    painter = painterResource(id = R.drawable.ic_play_line),
                    contentDescription = "PLay",
                    tint = ColorTheme.MainColor.lightGreen
                )
            }
            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(5.dp),
                onClick = {
                    if (currentTime <= 0L) {
                        currentTime = totalTime
                        isTimerRunning = true
                    } else {
                        isTimerRunning = !isTimerRunning
                    }
                }
            ) {
                Icon(
                    modifier = Modifier.size(45.dp),
                    painter = painterResource(id = R.drawable.ic_pause),
                    contentDescription = "",
                    tint = ColorTheme.MainColor.orange
                )
            }
        }
    }
}

@Composable
private fun TimerFromVM(
    modifier: Modifier = Modifier,
    totalTime: Long,
    handleColor: Color,
    inactiveBarColor: Color,
    activeBarColor: Color,
    initialValue: Float = 1f,
    strokeWidth: Dp = 5.dp,
    remainingMinTime: (Long) -> Unit
) {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    var value by remember {
        mutableStateOf(initialValue)
    }
    var currentTime by remember {
        mutableStateOf(totalTime)
    }
    var isTimerRunning by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
        if (currentTime > 0 && isTimerRunning) {
            delay(100L)
            currentTime -= 100L
            value = currentTime / totalTime.toFloat()
        }
        remainingMinTime(currentTime)
    }
    Column {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .onSizeChanged {
                    size = it
                }
        ) {
            // draw the timer
            Canvas(modifier = modifier) {
                drawArc(
                    color = inactiveBarColor,
                    startAngle = -215f,
                    sweepAngle = 250f,
                    useCenter = false,
                    size = Size(size.width.toFloat(), size.height.toFloat()),
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )
                drawArc(
                    color = activeBarColor,
                    startAngle = -215f,
                    sweepAngle = 250f * value,
                    useCenter = false,
                    size = Size(size.width.toFloat(), size.height.toFloat()),
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )
                // calculate the value from arc pointer position
                val center = Offset(size.width / 2f, size.height / 2f)
                val beta = (250f * value + 145f) * (PI / 180f).toFloat()
                val r = size.width / 2f
                val a = cos(beta) * r
                val b = sin(beta) * r
                // draw the circular pointer/ cap
                drawPoints(
                    listOf(Offset(center.x + a, center.y + b)),
                    pointMode = PointMode.Points,
                    color = handleColor,
                    strokeWidth = (strokeWidth * 3f).toPx(),
                    cap = StrokeCap.Round
                )
            }

            Column {
                Text(
                    text = (currentTime).formatTime(),
                    fontSize = 44.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row {
                    Text(
                        text = stringResource(id = R.string.field_minutes),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(25.dp))
                    Text(
                        text = stringResource(id = R.string.field_seconds),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )
                }
            }

            // create button to start or stop the timer
            /* Button(
                 onClick = {
                     if(currentTime <= 0L) {
                         currentTime = totalTime
                         isTimerRunning = true
                     } else {
                         isTimerRunning = !isTimerRunning
                     }
                 },
                 modifier = Modifier.align(Alignment.BottomCenter),
                 // change button color
                 colors = ButtonDefaults.buttonColors(
                     backgroundColor = if (!isTimerRunning || currentTime <= 0L) {
                         Color.Green
                     } else {
                         Color.Red
                     }
                 )
             ) {
                 Text(
                     // change the text of button based on values
                     text = if (isTimerRunning && currentTime >= 0L) "Stop"
                     else if (!isTimerRunning && currentTime >= 0L) "Start"
                     else "Restart"
                 )
             }*/
        }

        Row(modifier = Modifier) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(5.dp),
                onClick = {
                    if (isTimerRunning && currentTime >= 0L || !isTimerRunning && currentTime >= 0L){
                        currentTime = totalTime
                        isTimerRunning = false
                        remainingMinTime(totalTime)
                    }
                }
            ) {
                Icon(
                    modifier = Modifier.size(45.dp),
                    painter = painterResource(id = R.drawable.ic_stop),
                    contentDescription = "",
                    tint = ColorTheme.MainColor.darkRed
                )
            }
            IconButton(
                modifier = Modifier
                    .padding(5.dp),
                onClick = {
                    if (currentTime <= 0L) {
                        currentTime = totalTime
                        isTimerRunning = true
                    } else {
                        isTimerRunning = !isTimerRunning
                    }
                }
            ) {
                Icon(
                    modifier = Modifier.size(125.dp),
                    painter = painterResource(id = R.drawable.ic_play_line),
                    contentDescription = "PLay",
                    tint = ColorTheme.MainColor.lightGreen
                )
            }
            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(5.dp),
                onClick = {
                    if (currentTime <= 0L) {
                        currentTime = totalTime
                        isTimerRunning = true
                    } else {
                        isTimerRunning = !isTimerRunning
                    }
                }
            ) {
                Icon(
                    modifier = Modifier.size(45.dp),
                    painter = painterResource(id = R.drawable.ic_pause),
                    contentDescription = "",
                    tint = ColorTheme.MainColor.orange
                )
            }
        }
    }
}



//-----------------
@Preview(showBackground = true)
@Composable
private fun FullContentPreview() {
    TaskDetailsScreenContent(
        Modifier,
        TaskItemModel(
            id = 4,
            title = "This is Title Design App",
            duration = 12,
            themeColorString = ColorPalette.RED.colorStr,
            themeColor = ColorPalette.RED.colorValue,
        ),
        {},{}
    )
}
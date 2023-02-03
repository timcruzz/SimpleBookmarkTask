package com.timothy.simplebookmarktask.ui.home

import android.app.Application
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.timothy.simplebookmarktask.R
import com.timothy.simplebookmarktask.config.ColorPalette
import com.timothy.simplebookmarktask.config.Constants
import com.timothy.simplebookmarktask.domain.models.TaskItemModel
import com.timothy.simplebookmarktask.ui.navigation.navigateToConfigTaskListScreen
import com.timothy.simplebookmarktask.ui.theme.ColorTheme
import com.timothy.simplebookmarktask.utilities.GradientPositions
import com.timothy.simplebookmarktask.utilities.gradientBackground

@Composable
fun TaskListScreen(navController: NavHostController) {

    val context = LocalContext.current
    val application = context.applicationContext as Application

    val viewModel: TaskListScreenViewModel = viewModel(
        factory = TaskListViewModelFactory(application)
    )

    LaunchedEffect(Unit){
        viewModel.fetchTaskFromDB()
    }

    TaskListContent(
        navController = navController,
        modifier = Modifier,
        taskList = viewModel.taskList
    )
}

@Composable
fun TaskListContent(
    modifier: Modifier,
    navController: NavHostController?,
    taskList: List<TaskItemModel>
) {
    val scrollState = rememberScrollState()

    TaskListStructure(modifier = modifier,
        TopBar = { TopBar{
            navController?.let{
                navigateToConfigTaskListScreen(
                    navController = navController,
                    configIdentifier = Constants.ConfigIdentifier.ADD_TASK_KEY
                )
            }
        } },
        BodyList = {
            BodyList(
                scrollState,
                taskList =  taskList,
                onTaskClicked = {}
            )
        }
    )
}

@Composable
private fun TaskListStructure(
    modifier: Modifier,
    TopBar: @Composable () -> Unit,
    BodyList: @Composable () -> Unit,
) {
    Column(
        modifier
            .fillMaxSize()
            .background(ColorTheme.MainColor.skyBluer)
    ) {
        TopBar()
        BodyList()
    }
}

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    addOnClick: () -> Unit = {}
) {
    Box(
        Modifier
            .background(ColorTheme.MainColor.creamWhite)
            .height(70.dp)
    ) {
        Row(
            modifier = modifier
                .clip(RoundedCornerShape(bottomStart = 60.dp))
                .background(ColorTheme.MainColor.skyBluer)
                .height(70.dp)
                .fillMaxWidth()
        )
        {
            ProvideTextStyle(value = MaterialTheme.typography.h4) {
                Text(
                    modifier = Modifier.padding(
                        start = 50.dp,
                        top = 10.dp
                    ),
                    text = stringResource(id = R.string.home_title),
                    color = ColorTheme.MainColor.creamWhite,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start
                )
            }

            IconButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 60.dp, top = 10.dp),
                onClick = addOnClick
            ) {
                Icon(
                    modifier = Modifier.size(45.dp),
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "",
                    tint = ColorTheme.MainColor.darkBlue
                )
            }
        }
    }

}

@Composable
fun BodyList(
    scrollState: ScrollState,
    taskList: List<TaskItemModel>,
    onTaskClicked: (TaskItemModel) -> Unit
) {
    Column(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    topEnd = 50.dp,
                    bottomStart = 10.dp,
                    bottomEnd = 10.dp
                )
            )
            .fillMaxSize()
            .background(ColorTheme.MainColor.creamWhite)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(dimensionResource(id = R.dimen.default_margin))
        )
        {
            if(taskList.isNotEmpty()) {
                Spacer(modifier = Modifier.padding(5.dp))
                Box(
                    modifier = Modifier.padding(
                        start = dimensionResource(id = R.dimen.small_margin)
                    )
                ) {
                    Spacer(
                        modifier = Modifier
                            .width(3.dp)
                            .align(Alignment.Center)
                            .background(ColorTheme.MainColor.midBrown)
                            .height(20.dp)
                            .clip(RoundedCornerShape(5.dp))
                    )
                    Spacer(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(18.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(ColorTheme.MainColor.midBrown)
                            .alpha(0.7f)
                    )
                }
            }
            taskList.forEach {
                TaskListItem(
                    it,
                    onTaskClicked = onTaskClicked
                )
            }
        }
    }
}

@Composable
private fun TaskListItem(
    taskItem: TaskItemModel,
    onTaskClicked: (TaskItemModel) -> Unit
) {
    Row(
        Modifier
            .padding(
                start = dimensionResource(id = R.dimen.small_margin),
                end = dimensionResource(id = R.dimen.small_margin)
            )
    ) {
        Box(
            modifier = Modifier
        ) {
            Spacer(
                modifier = Modifier
                    .width(3.dp)
                    .align(Alignment.Center)
                    .background(ColorTheme.MainColor.midBrown)
                    .height(160.dp)
                    .clip(RoundedCornerShape(5.dp))
            )
            Spacer(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(18.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(taskItem.themeColor)
                    .alpha(0.7f)
            )
        }

        Spacer(Modifier.width(15.dp))

        Column {
            Card(
                elevation = 5.dp,
                shape = RoundedCornerShape(25.dp),
                backgroundColor = Color.Transparent,
                modifier = Modifier
                    .padding(0.dp)
                    .clickable(
                        onClick = { onTaskClicked(taskItem) }
                    )
                    .fillMaxSize()
                    .height(height = 140.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxSize()
                        .gradientBackground(
                            colors = listOf(
                                taskItem.themeColor,
                                ColorTheme.TileColor.skyBlue,
                            ),
                            startPosition = GradientPositions.BOTTOM_START,
                            endPosition = GradientPositions.TOP_END
                        )
                ) {
                    Column(
                        Modifier
                            .padding(10.dp)
                    ) {
                        ProvideTextStyle(value = MaterialTheme.typography.h6) {
                            Text(
                                modifier = Modifier.padding(
                                    start = 15.dp,
                                    top = 15.dp,
                                    end = 50.dp
                                ),
                                text = taskItem.title.orEmpty(),
                                color = ColorTheme.MainColor.creamWhite,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(20.dp))
        }

    }
}


//--------------------

@Preview(showBackground = true)
@Composable
private fun FullContentPreview() {
    TaskListContent(
        Modifier,
        null,
        listOf(
            TaskItemModel(
                id = 1,
                title = "This is Title Design App",
                duration = 12,
                themeColorString = ColorPalette.RED.colorStr,
                themeColor = ColorPalette.RED.colorValue,
            ),
            TaskItemModel(
                id = 2,
                title = "This is iApp D Title Design App",
                duration = 12,
                themeColorString =  ColorPalette.RED.colorStr,
                themeColor = ColorPalette.RED.colorValue,
            ),
            TaskItemModel(
                id = 3,
                title = "This is Title Design App",
                duration = 12,
                themeColorString =  ColorPalette.RED.colorStr,
                themeColor = ColorPalette.RED.colorValue,
            ),
            TaskItemModel(
                id = 4,
                title = "This is Title Design App",
                duration = 12,
                themeColorString =  ColorPalette.RED.colorStr,
                themeColor = ColorPalette.RED.colorValue,
            )
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun TopBarPreview() {
    TopBar()
}
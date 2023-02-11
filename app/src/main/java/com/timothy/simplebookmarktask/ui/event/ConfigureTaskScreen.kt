package com.timothy.simplebookmarktask.ui.event

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.timothy.simplebookmarktask.R
import com.timothy.simplebookmarktask.config.ColorPalette
import com.timothy.simplebookmarktask.config.Constants
import com.timothy.simplebookmarktask.domain.mapper.getActualColor
import com.timothy.simplebookmarktask.domain.models.TaskItemModel
import com.timothy.simplebookmarktask.ui.navigation.navigateToHomeTaskListScreen
import com.timothy.simplebookmarktask.ui.theme.ColorTheme
import com.timothy.simplebookmarktask.utilities.ExtensionUtils
import com.timothy.simplebookmarktask.utilities.GradientPositions
import com.timothy.simplebookmarktask.utilities.gradientBackground

@Composable
fun ConfigureTaskScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val application = context.applicationContext as Application

    val viewModel: ConfigureTaskScreenViewModel = viewModel(
        factory = ConfigureTaskViewModelFactory(application, navController)
    )

    val configIdentifier = viewModel.configIdentifier.value
    val taskItem = viewModel.taskItem.value

    ConfigureTaskScreenContent(
        modifier = Modifier,
        configIdentifier = configIdentifier,
        taskItem = taskItem,
        deleteClick = {
            viewModel.deleteTaskByIdFromDB(taskItem?.id)
            navigateToHomeTaskListScreen(navController)
        },
        cancelClick = {
            navController.popBackStack()
        },
        saveClick = {
            viewModel.updateOrAddTaskDB(it)
            navigateToHomeTaskListScreen(navController)
        }
    )
}

@Composable
fun ConfigureTaskScreenContent(
    modifier: Modifier,
    configIdentifier: String?,
    taskItem: TaskItemModel?,
    deleteClick: () -> Unit,
    cancelClick: () -> Unit,
    saveClick: (TaskItemModel) -> Unit,
) {
    ConfigureTaskScreenStructure(modifier = modifier,
        TopBar = {

        },
        BodyContent = {
            BodyContent(
                configIdentifier,
                taskItem,
                deleteClick = deleteClick,
                cancelClick = cancelClick,
                saveClick = saveClick
            )
        }
    )
}

@Composable
private fun ConfigureTaskScreenStructure(
    modifier: Modifier,
    TopBar: @Composable () -> Unit,
    BodyContent: @Composable () -> Unit,
) {
    Box(
        modifier
            .fillMaxSize()
            .gradientBackground(
                colors = listOf(
                    ColorTheme.MainColor.darkskyBluer,
                    ColorTheme.MainColor.skyBluer,
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
    configIdentifier: String?,
    taskItem: TaskItemModel?,
    deleteClick: () -> Unit,
    cancelClick: () -> Unit,
    saveClick: (TaskItemModel) -> Unit,
) {

    val taskName = remember {
        mutableStateOf("")
    }
    val durationInMinutes = remember {
        mutableStateOf("")
    }
    val themeText = remember {
        mutableStateOf("")
    }
    val themeColor = remember {
        mutableStateOf(ColorTheme.TileColor.lightYellow)
    }

    var expanded by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val stringRsc = stringResource(id = R.string.confirm_complete_form)
    val saveContentClick = {
        if (taskName.value.isEmpty() ||
            durationInMinutes.value.isEmpty() ||
            themeText.value.isEmpty()
        ) {
            Toast.makeText(context, stringRsc, Toast.LENGTH_LONG).show()
        } else {
            saveClick(
                TaskItemModel(
                    id = taskItem?.id ?: 0,
                    title = taskName.value,
                    duration = if (durationInMinutes.value.isDigitsOnly()) {
                        durationInMinutes.value.toInt()
                    } else {
                        1
                    },
                    themeColorString = themeText.value,
                    themeColor = themeColor.value
                )
            )
        }
    }
    taskItem?.let {
        LaunchedEffect(Unit){
            taskName.value = it.title.orEmpty()
            durationInMinutes.value = it.duration.toString()
            themeColor.value = getActualColor(it.themeColorString.orEmpty())
            themeText.value = it.themeColorString.orEmpty()
        }
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f)
            .padding(dimensionResource(id = R.dimen.default_margin))
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .clip(
                    RoundedCornerShape(45.dp)
                )
                .background(ColorTheme.MainColor.creamWhite)
                .padding(dimensionResource(id = R.dimen.default_margin))
        )
        {
            Row {
                ProvideTextStyle(value = MaterialTheme.typography.h4) {
                    Text(
                        modifier = Modifier.padding(
                            start = 10.dp,
                            top = 10.dp
                        ),
                        text = if (configIdentifier == Constants.ConfigIdentifier.UPDATE_TASK_KEY) {
                            stringResource(id = R.string.update_tasks)
                        } else {
                            stringResource(id = R.string.add_tasks)
                        },
                        color = ColorTheme.MainColor.darkBlue,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start
                    )
                }
                if (configIdentifier == Constants.ConfigIdentifier.UPDATE_TASK_KEY) {
                    IconButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 60.dp, top = 10.dp),
                        onClick = deleteClick
                    ) {
                        Icon(
                            modifier = Modifier.size(45.dp),
                            painter = painterResource(id = R.drawable.ic_delete_permanent),
                            contentDescription = "",
                            tint = ColorTheme.MainColor.darkRed
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(id = R.string.task_name)) },
                value = taskName.value,
                onValueChange = { taskName.value = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Info,
                        tint = ColorTheme.MainColor.darkBrown,
                        contentDescription = "emailIcon"
                    )
                },
            )

            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(id = R.string.field_minutes)) },
                value = durationInMinutes.value,
                onValueChange = { durationInMinutes.value = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_timer),
                        tint = ColorTheme.MainColor.darkBrown,
                        contentDescription = ""
                    )
                },
            )

            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        expanded = true
                    },
                enabled = false,
                label = { Text(text = stringResource(id = R.string.field_theme)) },
                value = themeText.value,
                onValueChange = { durationInMinutes.value = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_theme),
                        tint = ColorTheme.MainColor.darkBrown,
                        contentDescription = ""
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_color_picked),
                        tint = themeColor.value,
                        contentDescription = "",
                        modifier = Modifier.background(themeColor.value)
                    )
                },
            )

            Spacer(modifier = Modifier.height(15.dp))

            ColorPickerThemePopUp(expanded, onDismissed = {
                expanded = false
            }, onColorPicked = {
                expanded = false
                themeColor.value = it.colorValue
                themeText.value = it.colorStr
            })

            Row(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = cancelClick,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .height(50.dp),
                ) {
                    Text(text = stringResource(id = R.string.btn_cancel))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = saveContentClick,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .height(50.dp)
                ) {
                    Text(text = stringResource(id = R.string.btn_save))
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun ColorPickerThemePopUp(
    expanded: Boolean,
    onDismissed: () -> Unit,
    onColorPicked: (ColorPalette) -> Unit,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismissed() }
    ) {
        DropdownMenuItem(onClick = { onColorPicked(ColorPalette.YELLOW) }) {
            ColorPickerThemeItem(
                colorName = ColorPalette.YELLOW.colorStr,
                colorValue = ColorPalette.YELLOW.colorValue
            )
        }
        Divider()
        DropdownMenuItem(onClick = { onColorPicked(ColorPalette.GREEN) }) {
            ColorPickerThemeItem(
                colorName = ColorPalette.GREEN.colorStr,
                colorValue = ColorPalette.GREEN.colorValue
            )
        }
        Divider()
        DropdownMenuItem(onClick = { onColorPicked(ColorPalette.ORANGE) }) {
            ColorPickerThemeItem(
                colorName = ColorPalette.ORANGE.colorStr,
                colorValue = ColorPalette.ORANGE.colorValue
            )
        }
        Divider()
        DropdownMenuItem(onClick = { onColorPicked(ColorPalette.BLUE) }) {
            ColorPickerThemeItem(
                colorName = ColorPalette.BLUE.colorStr,
                colorValue = ColorPalette.BLUE.colorValue
            )
        }
        Divider()
        DropdownMenuItem(onClick = { onColorPicked(ColorPalette.VIOLET) }) {
            ColorPickerThemeItem(
                colorName = ColorPalette.VIOLET.colorStr,
                colorValue = ColorPalette.VIOLET.colorValue
            )
        }
        Divider()
        DropdownMenuItem(onClick = { onColorPicked(ColorPalette.RED) }) {
            ColorPickerThemeItem(
                colorName = ColorPalette.RED.colorStr,
                colorValue = ColorPalette.RED.colorValue
            )
        }
    }
}

@Composable
private fun ColorPickerThemeItem(
    modifier: Modifier = Modifier,
    colorName: String,
    colorValue: Color
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Text(text = colorName, modifier = Modifier.weight(1f))
        Spacer(
            modifier = Modifier
                .width(5.dp)
                .weight(1f)
        )
        Spacer(
            modifier = Modifier
                .size(10.dp)
                .background(colorValue)
                .weight(1f)
        )
    }
}


//---------------
@Preview(showBackground = true)
@Composable
private fun FullContentPreview() {
    ConfigureTaskScreenContent(
        Modifier,
        "Add Tasks",
        TaskItemModel(
            id = 4,
            title = "This is Title Design App",
            duration = 12,
            themeColorString = ColorPalette.RED.colorStr,
            themeColor = ColorPalette.RED.colorValue,
        ),
        deleteClick = {},
        cancelClick = {},
        saveClick = {

        }
    )
}
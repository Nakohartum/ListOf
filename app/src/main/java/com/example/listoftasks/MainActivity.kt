package com.example.listoftasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: TaskViewModel = viewModel()
            TaskListScreen(viewModel = viewModel)
        }
    }
}

@Composable
fun TaskListScreen(viewModel: TaskViewModel) {
    val number by viewModel.counter.collectAsState()
    var isAdding by remember {
        mutableStateOf(false)
    }
    var isEditing by remember {
        mutableStateOf(false)
    }

    if (isAdding){
        AddDialog(viewModel, onDismissRequest = {isAdding = false})
    }



    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Button(
            onClick = {
                isAdding = true
            }
        ) {
            Text("Add")
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(number){
                if (it.isEditing){
                    EditTask(viewModel, it)
                }
                TaskItem(it, viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDialog(viewModel: TaskViewModel, onDismissRequest: () -> Unit) {
    BasicAlertDialog(
        onDismissRequest = {
            onDismissRequest()
            viewModel.text.value = ""
        }
    ) {
        Card(

        ) {
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                OutlinedTextField(

                    value = viewModel.text.value,
                    onValueChange = {viewModel.text.value = it}
                )

                Button(
                    onClick = {
                        onDismissRequest()
                        viewModel.plus()
                        viewModel.text.value = ""
                    }
                ) {
                    Text("Add")
                }
            }
        }
    }
}


@Composable
fun TaskItem(it: Task, viewModel: TaskViewModel){
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp, vertical = 4.dp).clickable {
            viewModel.delete(it)
        }
    ) {
        Row(modifier = Modifier.fillMaxSize().background(color = if (it.isEditing) Color.Green else Color.Red)) {
            Text(text = it.title, modifier = Modifier.padding(5.dp))
            IconButton(onClick = {
                viewModel.update(it.copy(isEditing = true))
            }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTask(viewModel: TaskViewModel, task: Task){
    viewModel.text.value = task.title
    BasicAlertDialog(
        onDismissRequest = {
            viewModel.update(task = task.copy(isEditing = false))
            viewModel.text.value = ""
        }
    ) {
        Card(

        ) {
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                OutlinedTextField(
                    value = viewModel.text.value,
                    onValueChange = {viewModel.text.value = it}
                )

                Button(
                    onClick = {
                        viewModel.update(task.copy(title = viewModel.text.value, isEditing = false))
                        viewModel.text.value = ""
                    }
                ) {
                    Text("Update")
                }
            }
        }
    }
}



package com.example.juan_maxwel_comp304sec001_lab06

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.juan_maxwel_comp304sec001_lab06.ui.theme.Juan_maxwel_COMP304Sec001_Lab06Theme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterialApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Juan_maxwel_COMP304Sec001_Lab06Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.secondary
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

// Sample data classes
data class Program(val name: String, val courses: List<Course>)
data class Course(val name: String, val description: String)

// Sample data
val sem5Courses = listOf(
    Course("Mobile App Development", "In Mobile Application Development, students will gain hands-on experience in developing and deploying\n" +
            "native mobile applications on the Android platform. Coursework emphasizes how to create advanced\n" +
            "User Interfaces (UIs), handle events, access remote services, store and retrieve data on the device,\n" +
            "display maps, and use other Android APIs. Android Studio will be used to create a variety of mobile\n" +
            "applications."),
    Course ("Business & Entrepreneurship", " Topics for the\n" +
            "Product Development and Entrepreneurship component will include software entrepreneurial process,\n" +
            "principles of software business ownership, identifying software market opportunity, entrepreneurial\n" +
            "software marketing, software business communication and negotiation techniques, feasibility analysis,\n" +
            "entrepreneurial financing, legal structures and issues, software business plan development, risk\n" +
            "management. The course will promote collaborative teamwork."),
    Course ("Big Data Tools for Machine Learning", "In this course, students will be introduced to large scale learning: distributed learning. The concepts of\n" +
            "distributed storage systems and parallel processing will be discussed. Storage types for big data (NoSQL)\n" +
            "and big data tools (Hadoop eco system, YARN and Apache Spark) will be explained and students will\n" +
            "gain hands-on experience by applying the big data tools in real world applications."),
    Course ("Unsupervised and Reinforcement Learning", "In the first half of this course, students will be exposed to unsupervised learning (dimensionality reduction,\n" +
            "k-means clustering, DBSCAN, hierarchical clustering, Gaussian mixtures, autoencoders, and Kohonen\n" +
            "Self-Organizing Map (SOM)).\n" +
            "In the second half of the course, students will be exposed to Reinforcement Learning (policy gradient,\n" +
            "Markov Decision Processes, Q-Learning, and TF-Agents Library).\n" +
            "Students will gain hands-on experience by applying unsupervised learning and reinforcement learning\n" +
            "techniques."),
    Course ("Artificial Neural Networks", "This course covers artificial neural networks and their practical applications. Coursework emphasizes\n" +
            "fundamental models and algorithms starting with McCulloch-Pitts and Perceptron models, Multi-Layer\n" +
            "Perceptron (MLP) networks, backpropagation algorithm, activation functions, convolutional neural\n" +
            "networks, and recurrent neural networks. Students will gain hands-on experience by using Keras and\n" +
            "TensorFlow to build and train models for solving various classification/prediction problems."),
    Course ("IT project Management", "Students are taught the concepts and basic functions of Project Management, and the integration of these\n" +
            "concepts and functions into a coherent project management system. Also, role of the project manager\n" +
            "and the project management team in implementing and controlling projects. Further, the Project\n" +
            "Management Body of Knowledge PMBOK® as defined by the Project Management Institute PMI and its\n" +
            "application to Project Management. CNET-307 is offered as a technical elective in applicable programs."),
    // ... other courses
)
val sem4Courses = listOf(
    Course("Advance Business Communications", "Advanced Business Communications is a course designed to enhance professional communication skills. It covers effective writing, presentation techniques, interpersonal communication, and digital communication strategies. Emphasis is placed on clarity, persuasiveness, and adaptability to diverse business environments aiming to equip students with the skills necessary for successful business interactions and leadership roles."),
    Course ("Networking for Software Developers", "Networking for Software Developers is a comprehensive course designed to equip software developers with essential networking concepts and skills. It covers topics like network protocols, IP addressing, data transmission methods, and security practices. Emphasizing practical applications,the course helps developers understand how networking impacts software functionality and performance, enhancing their ability to create robust, network-aware applications."),
    Course ("Supervised Learning", "Supervised learning is a branch of machine learning where algorithms are trained using labeled data. The training process involves presenting the algorithm with input-output pairs, where it learns to map inputs to outputs, gradually improving its accuracy.This approach is commonly used for tasks like classification and regression, where the goal is to predict outcomes for new, unseen data based on patterns recognized from the training dataset. Supervised learning is foundational in applications such as image recognition, speech recognition, and predictive analytics. "),
    Course ("Data structures and Algorithms", "Data Structures and Algorithms is an essential course focusing on the organization, management, and storage of data using various data structures, and the efficient processing of this data using algorithms. It covers topics like arrays, linked lists, trees, sorting, and searching techniques, emphasizing algorithmic design and complexity analysis."),
    Course ("Software Testing and Quality Assurance", "Advanced Business Communications is a course designed to enhance professional communication skills. It covers effective writing, presentation techniques, interpersonal communication, and digital communication strategies. Emphasis is placed on clarity, persuasiveness, and adaptability to diverse business environments, aiming to equip students with the skills necessary for successful business interactions and leadership roles"),

)

val softwarePrograms = listOf(
    Program("AI semester 5", sem5Courses),
    Program("AI semester 4", sem4Courses),
    // ... other programs
)

// Navigation setup
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "programList") {
        composable("programList") {
            ProgramListScreen(softwarePrograms, navController)
        }
        composable("courseList/{programName}") { backStackEntry ->
            val programName = backStackEntry.arguments?.getString("programName")
            val program = softwarePrograms.find { it.name == programName }
            CourseListScreen(program?.courses ?: listOf(), navController)
        }
        composable("courseDetail/{courseName}") { backStackEntry ->
            val courseName = backStackEntry.arguments?.getString("courseName")
            val course = softwarePrograms.flatMap { it.courses }.find { it.name == courseName }

            if (course != null) {
                CourseDetailScreen(course)
            }
        }
    }

}

@Composable
fun ProgramListScreen(programs: List<Program>, navController: NavHostController) {
    LazyColumn {
        items(programs) { program ->
            // Since ProgramListScreen does not require an expandable ListItem,
            // you can provide a default value for description and isExpanded.
            ListItem(
                text = program.name,
                description = "", // Empty description
                isExpanded = false, // Default as not expanded
                onClick = { navController.navigate("courseList/${program.name}") }
            )
        }
    }
}

// More Composables for CourseListScreen and CourseDetailScreen will be added here
// Course List Screen
@Composable
fun CourseListScreen(courses: List<Course>, navController: NavHostController) {
    LazyColumn {
        items(courses) { course ->
            var isExpanded by remember { mutableStateOf(false) }
            ListItem(
                text = course.name,
                description = course.description,
                isExpanded = isExpanded,
                onClick = { isExpanded = !isExpanded }
            )
        }
    }
}

// Course Detail Screen with expandable description
@Composable
fun CourseDetailScreen(course: Course) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    var isExpanded by remember { mutableStateOf(false) }
    val surfaceColor by animateColorAsState(
        if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    )

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(
            text = "Course Name: ${course.name}",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(8.dp))

        Surface(
            shape = MaterialTheme.shapes.medium,
            elevation = 1.dp,
            color = surfaceColor,
            modifier = Modifier
                .animateContentSize()
                .clickable {
                    isExpanded = !isExpanded
                    if (isExpanded) {
                        coroutineScope.launch {
                            delay(300) // Wait for content to expand
                            if (scrollState.maxValue > 0) {
                                scrollState.animateScrollTo(scrollState.maxValue)
                            }
                        }
                    }
                }
                .padding(1.dp)
        ) {
            Text(
                text = "Description: ${course.description}",
                modifier = Modifier.padding(all = 4.dp),
                maxLines = if (isExpanded) Int.MAX_VALUE else 3
            )
        }
    }
}


@Composable
fun ListItem(text: String, description: String, isExpanded: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
            .animateContentSize()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = text, style = MaterialTheme.typography.h6)
            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = description, style = MaterialTheme.typography.body2)
            }
        }
    }
}







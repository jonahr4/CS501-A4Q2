# Simple Counter App

This is a straightforward Android application built with Jetpack Compose that demonstrates modern Android development practices. It features a simple counter that can be manually or automatically incremented.

## Features

- **Manual Counter**: Increment, decrement, and reset the count with button clicks.
- **Auto-Increment Mode**: Toggle an automatic counter that increments at a user-defined interval.
- **Custom Interval**: Navigate to a settings screen to change the speed of the auto-incrementer (from 0.25 to 60 seconds).
- **Clean Architecture**: Follows a basic MVVM (Model-View-ViewModel) pattern, separating UI, state, and logic.
- **Modern UI**: Built entirely with Jetpack Compose, including Material 3 design and support for system light/dark modes and dynamic color (on Android 12+).
- **Lifecycle Aware**: State is preserved during configuration changes (like screen rotation), and background processes are safely managed.

## App Structure

The project is organized into four main files, each with a clear responsibility:

-   `MainActivity.kt`: The app's main entry point. It sets up the UI, initializes the `ViewModel`, and handles basic navigation between the two screens.
-   `CounterViewModel.kt`: The brain of the app. It holds the application state (the count, auto-increment status) and contains all the business logic for modifying that state. It is completely independent of the UI.
-   `CounterScreens.kt`: Contains the Composable functions that define the app's user interface. It includes layouts for both the main counter screen and the settings screen, which are stateless and simply react to data they are given.

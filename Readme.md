# ABZ Agency Test Project

This project demonstrates a simple application for user registration and data retrieval using the ABZ Agency API. The application is built with a focus on Clean Architecture and follows the Model-View-ViewModel (MVVM) design pattern, aiming for modular, maintainable, and testable code. The project also follows the SOLID principles to enhance code scalability and flexibility.

## Project Features

1. **User List Pagination**: Fetches user data from the API in a paginated format.
2. **User Registration**: Allows users to register by submitting their details along with an image.
3. **Offline Connectivity Monitoring**: Tracks and handles network status changes to provide a more stable user experience.

## Screenshots

<div style="display: flex; gap: 10px;">
  <img src="https://github.com/user-attachments/assets/9017f5e4-9776-49e3-ba83-abe22d925292" alt="User List Page" width="45%">
  <img src="https://github.com/user-attachments/assets/93d265c3-5514-4726-b5e3-02ae7fe4aa90" alt="Registration Page" width="45%">
</div>

## Libraries Used

### Dependency Injection (Hilt)

- **`implementation(libs.hilt.android)`**
- **`kapt(libs.dagger.hilt.compiler)`**

Hilt is used for dependency injection to manage dependencies across the application, enhancing modularity and reducing coupling between components. Hilt simplifies DI by integrating directly into the Android lifecycle and managing dependencies automatically.

#### Advantages:
- Centralized dependency management.
- Reduces boilerplate code and improves testability by allowing easy injection of mock dependencies.

### Networking (Retrofit, OkHttp, Gson)

- **`implementation(libs.retrofit)`** and **`implementation(libs.converter.gson)`**

Retrofit is used as the primary HTTP client to interact with the ABZ API. It supports multiple HTTP operations (GET, POST, etc.), and the integration with Gson enables easy parsing of JSON responses into Kotlin data classes. OkHttp provides a flexible, efficient HTTP/2-enabled stack that handles requests and responses.

#### Advantages:
- **Retrofit**: Highly readable and flexible syntax for defining API requests.
- **Gson Converter**: Simplifies JSON serialization and deserialization by automatically mapping JSON data to Kotlin data classes.
- **OkHttp**: Enables efficient network calls with features like logging and custom interceptors for monitoring network traffic.

### Image Loading (Picasso)

- **`implementation(libs.picasso)`**

Picasso is used for loading images efficiently into `ImageView` components. This library handles image caching and memory management, which is essential for smooth user experiences, particularly when loading multiple images.

#### Advantages:
- Simplifies asynchronous image loading and caching.
- Reduces potential memory leaks and crashes by handling images efficiently.

### Navigation (Android Navigation Component)

- **`implementation(libs.androidx.navigation.fragment.ktx)`**
- **`implementation(libs.androidx.navigation.ui.ktx)`**

Android’s Navigation Component simplifies navigating between app destinations, particularly with fragments, and includes support for transitions and safe args.

#### Advantages:
- Reduces boilerplate in handling fragment transactions.
- Supports argument passing between fragments, simplifying data transfer within the app.

## Architecture

The project adheres to Clean Architecture principles, aiming to separate concerns into distinct layers. This structure makes the app modular and easy to maintain. The main layers used are:

1. **Data Layer**: Handles data sources, including API interactions and repository implementations.
2. **Domain Layer**: Contains use cases that represent business logic and act as intermediaries between the UI and data layers.
3. **Presentation Layer**: Manages UI elements and user interactions with ViewModel as the primary state holder.

### Design Pattern: MVVM

The Model-View-ViewModel (MVVM) pattern is used to improve the separation of concerns and testability:

- **Model**: Defines data structures for the app, like `User` and `RegisterComplete`.
- **View**: Displays data to the user and reacts to changes via data binding or LiveData/StateFlow observers.
- **ViewModel**: Acts as a connector between the View and the Model, holding UI-related data and handling user interactions. In this project, it leverages Kotlin coroutines and Flow for efficient asynchronous data handling.

## Principles of SOLID

1. **Single Responsibility**: Each class has a well-defined responsibility (e.g., ViewModels are responsible for UI state management, UseCases handle business logic, etc.).
2. **Open/Closed**: The architecture is built to allow new features to be added without modifying existing code (e.g., adding new API endpoints).
3. **Liskov Substitution**: Interfaces are defined in a way that allows implementation classes to be used interchangeably.
4. **Interface Segregation**: Interfaces like `UsersRepository` are designed to provide only what the class requires, allowing focused and minimal implementations.
5. **Dependency Inversion**: Higher-level modules depend on abstractions, not implementations. For instance, the `UsersRepository` interface is used instead of a direct dependency on Retrofit.

## Project Setup and Usage

1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd abz-agency-test-project
   ```

2. **Install Dependencies**:
   Ensure all dependencies are correctly set up in your Gradle files by running:
   ```bash
   ./gradlew build
   ```

3. **Run the Project**:
   Connect an Android device or use an emulator, then:
   ```bash
   ./gradlew installDebug
   ```

4. **Testing**:
   The project includes unit tests for use cases and ViewModels. You can run tests using:
   ```bash
   ./gradlew test
   ```

## Conclusion

This project demonstrates how to build a modular, clean, and scalable Android application. By adhering to MVVM, Clean Architecture, and SOLID principles, we achieve a robust codebase that’s easily extensible and maintainable. The project leverages the best Android libraries to improve developer productivity and ensure high performance, making it an excellent foundation for production-level applications.

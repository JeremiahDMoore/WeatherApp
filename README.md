# WeatherApp

A simple weather application built with Kotlin, utilizing the OpenWeather API to fetch and display current weather information.

## Features

- **Current Weather**: Displays real-time weather data for a specified location.
- **Temperature Details**: Shows current temperature along with minimum and maximum temperatures.
- **Weather Conditions**: Provides information on weather conditions such as humidity, pressure, and wind speed.

## Technologies Used

- **Kotlin**: Primary programming language for Android development.
- **OpenWeather API**: API service used to retrieve weather data.
- **Android Studio**: Integrated Development Environment (IDE) for Android app development.

## Installation

To run this application locally:

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/JeremiahDMoore/WeatherApp.git
   cd WeatherApp
   ```

2. **Open in Android Studio**:
   - Launch Android Studio.
   - Select "Open an existing project" and navigate to the cloned repository's directory.

3. **Configure the OpenWeather API Key**:
   - Sign up at [OpenWeather](https://openweathermap.org/) to obtain an API key.
   - In the project, locate the file where the API key is required (e.g., `Constants.kt` or a similar configuration file).
   - Replace the placeholder with your actual API key:
     ```kotlin
     const val API_KEY = "YOUR_API_KEY"
     ```

4. **Build and Run the Application**:
   - Connect an Android device or start an emulator.
   - Click the "Run" button in Android Studio to build and launch the app.

## Usage

- **View Current Weather**:
  - Upon launching the app, enter the desired city name.
  - The app will display the current weather information for the specified location.

## Acknowledgments

- Thanks to [OpenWeather](https://openweathermap.org/) for providing the weather API.
- Inspired by the need for a simple, user-friendly weather application.

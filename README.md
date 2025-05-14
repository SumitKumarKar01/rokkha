# Rokkha: Enabling Offline Emergency Location Sharing via SMS
The primary objective of this application is to facilitate the swift sharing of users' locations with trusted contacts when they feel vulnerable, with a minimal number of steps. Recognizing the urgent nature of such situations, the app has been meticulously designed to enable users to activate location sharing with just a single button press.

To begin using the app, users must sign up for a new account by providing their email address and setting a password. During the app's initial launch, users are prompted to grant permissions for GPS and SMS functionalities. Subsequently, users can add the phone number of their chosen loved one and specify the desired time interval for location sharing, as illustrated in Figure 5.1.

After the initial setup, users can location sharing with designated contacts by pressing the "Alert" button. The app then transmits the location information to the designated contacts at predetermined intervals via SMS, ensuring continuous updates. This functionality is illustrated in Figure 5.2.



Fig. 5.1: First Launch Setup
![5 1](https://github.com/SumitKar01/rokkha/assets/62619778/96548f1f-8375-49b4-bb9b-3373d29fc40f)

Fig. 5.2: Regular Use Case
![5 2](https://github.com/SumitKar01/rokkha/assets/62619778/d10fba7b-6ed1-4467-9e7c-6e638362343b)

6. System Design And Development:
Technical Information:

Programming Language (Kotlin): The app was developed using Kotlin, a modern and concise programming language that runs on the Java Virtual Machine (JVM). It offers excellent interoperability with existing Java codes, making it an ideal choice for Android development.

Integrated Development Environment (Android Studio): Android Studio, a powerful and feature-rich IDE, developed ROKKHA. Android Studio provides comprehensive tools for app development, including a code editor, emulator, and debugging capabilities.

Android SDK: ROKKHA utilizes the Android Software Development Kit (SDK), which provides the necessary APIs and tools for building Android applications. The SDK includes libraries and frameworks for accessing device sensors, GPS, network connectivity, and other essential features the app utilizes.

 SQLite Database: The app utilizes an SQLite database for storing and managing user contacts. SQLite is a lightweight, serverless, self-contained database engine with high performance and reliability. It is a widely adopted database solution for mobile applications.

Firebase: The app uses Firebase to store and authenticate users. The Firebase database holds the list of all registered users. Firebase provides a secure method to register and sign up for users.

Google Maps API: The API ensures the accurate location of users in real-time and provides the ability to have real-time tracking in the future.

Use Case Diagram:
Fig. 6.1: Use case Diagram
![5 3](https://github.com/SumitKar01/rokkha/assets/62619778/00b9c2c1-15f9-4101-bf20-21501cc6e25c)

UI Designs:

The UI was designed to be user-friendly, clean, and minimal, prioritizing easy navigation and quick access to important features. User feedback prompted the addition of Dark Mode, alongside Light Mode (fig.6.2), catering to user preferences and improving usability.
Fig.6.2: Light and Dark UI
![6 2](https://github.com/SumitKar01/rokkha/assets/62619778/69afe162-3763-4b99-89cd-7fd612a61c7c)
System Features: 

Alert Button: ROKKHA's UI features a prominent alert button that, when pressed, initiates periodic alert messages containing the user's current location to the saved contacts in the app's SQLite database.

Contact Management: ROKKHA offers efficient contact management, allowing users to add, edit, and delete contacts directly within the app. These contact details are securely stored in an SQLite database, ensuring data persistence and security. Additionally, ROKKHA leverages Firebase for user authentication, providing a reliable and robust framework for managing user credentials and authentication processes.

Time Interval Settings
ROKKHA integrates a time interval feature that allows users to define specific time intervals for automatic alert sending. Once the intervals are saved, the app will automatically send alerts, including the user's current location, at the designated time intervals. This feature ensures that users can proactively share their location information without the need for manual input for each time.

Location Retrieval
ROKKHA leverages the device's location services to precisely obtain the user's current location. This location data is then incorporated into the alert message sent to the designated contacts. By utilizing the device's location services, ROKKHA ensures that the alert message includes accurate and up-to-date information about the user's whereabouts.
7. System Evaluation:

During testing, we enlisted participations of 25 individuals representing different age groups (fig.7.2) and genders (fig.7.3). These users actively engaged with our app for a duration exceeding 60 days, providing valuable data for analysis. Based on their feedback we obtained the following results:
Fig.7.1: Survey on accuracy
![7 1](https://github.com/SumitKar01/rokkha/assets/62619778/a398d010-29db-434a-8ef7-d5e11ec2982d)
According to the data presented in Figure 7.1, it can be inferred that the app performed flawlessly on all the devices it was tested on, indicating a 100% success rate.
 Fig.7.2: App usage among Different Age groups
 ![7 2](https://github.com/SumitKar01/rokkha/assets/62619778/c8833b2d-bee2-476a-91d8-15ac98979e2e)
Fig.7.3: App usage among Different Genders
![7 3](https://github.com/SumitKar01/rokkha/assets/62619778/940f7d2c-3d22-4fd3-87ab-ae065a5b5771)

Based on the analysis of Figure 7.2, it is evident that the majority of users (highest frequency) belong to the age group of 22 to 24 years. Furthermore, based on Figure 7.3, approximately 62.5% of the app's users are male, while 37.5% are female. 

It is important to note that the lower percentage of female users may be attributed to certain factors, such as hesitancy towards using the app and a tendency to avoid potentially dangerous places. These factors can influence user preferences and adoption rates among different genders.



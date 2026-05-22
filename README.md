# Event Planner 📅✨

**Event Planner** is a sleek, intuitive, and feature-rich native Android application built with **Kotlin**. Designed with a modern, user-centric interface, it allows users to organize their schedules, plan diverse categories of events, and manage their calendars seamlessly.

---

## 🚀 Features

### 1. **Dynamic Calendar Interface**
* **Category Filtering:** Easily toggle between custom event categories like *Pet Parties*, *Wedding Venues*, *Corporate Events*, *Birthday Events*, and *Music Shows*.
* **Interactive Date Slider:** An elegant horizontal calendar wheel allowing users to view planned events for any specific day at a glance.

### 2. **Complete CRUD Event Management**
* **Create Events:** Add new events easily with an intuitive setup form including:
  * Event Name
  * Interactive Date & Time Pickers (`DatePickerDialog` and `TimePickerDialog` views)
  * Detailed Descriptions
* **Read / View Schedules:** Separate organized dashboard views for tracking **Upcoming** vs. **Past Events**.
* **Update & Edit:** Modify existing events on the fly with smooth database updates.
* **Delete Functionality:** Remove canceled or unwanted events with one-click interactions.

### 3. **Clean & Modern UI/UX**
* Fully customized bottom navigation layouts and custom components using the **Navigation Component**.
* Smooth state transitions and efficient data handling via **RecyclerView**.
* Clean, consistent color palette tailored with **Material Components** and responsive structures using **ConstraintLayout**.

---

## 🛠️ Tech Stack & Architecture

### **🏗 Architecture**
This project follows the **MVVM (Model View ViewModel)** architecture pattern for clean separation of concerns, maintainability, and scalability.

### Architecture Flow

```text
UI Layer
   ↓
ViewModel Layer
   ↓
Repository Layer
   ↓
Room Database
```

### Components Used
* **Activities / Fragments (View):** Responsible for rendering the UI and capturing user interactions. They observe data streams from the ViewModel.
* **ViewModel:** Acts as a bridge between the UI and the data layer. It holds and manages UI-related data in a lifecycle-conscious way, preserving data across configuration changes (like screen rotations).
* **LiveData:** An observable data holder class that directly links the database state to the UI components, automatically updating views when the backend data changes.
* **Repository Pattern:** Serves as a single source of truth for data access, completely abstracting the underlying Room database operations from the ViewModel.
* **Room Database:** A robust SQLite object-mapping library used to handle offline data persistence securely.
* **DAO (Data Access Objects):** Outlines the direct data access methods and clean SQL queries utilized for managing event data.
* **Entity Models:** Defines the database table structures and schemas for stored events.

### **🛠 Tech Stack**
* **Language:** Kotlin
* **Platform:** Android SDK
* **UI Layouts:** XML Layouts, Material Components, ConstraintLayout, RecyclerView
* **Jetpack Components:** LiveData, ViewModel, Room Database, Navigation Component
* **Pickers:** DatePickerDialog, TimePickerDialog

---

## 📸 Application Walkthrough

| Home | Event Dashboard | Create Event |
| :---: | :---: | :---: |
| <img src="https://github.com/user-attachments/assets/ff704d71-8317-4199-a4fc-d07bc8ac7249" width="200" /><br><br><img src="https://github.com/user-attachments/assets/bfd38166-33d0-4b12-89b1-3ec6e5d9c9ba" width="200" /> | <img src="https://github.com/user-attachments/assets/862a871e-e715-486a-83d1-93d2cb015dc6" width="200" /><br><br><img src="https://github.com/user-attachments/assets/261b258b-1b26-42d3-b318-4c6480a62279" width="200" /><br><br><img src="https://github.com/user-attachments/assets/55332680-d5bd-4411-836e-4112724a175e" width="200" /> | <img src="https://github.com/user-attachments/assets/0f1fddd2-6f5b-463d-b400-3b616d359ad3" width="200" /><br><br><img src="https://github.com/user-attachments/assets/a8a1c514-e05e-4d6c-a0b4-f8fdac909c0f" width="200" /> |

---

## ⚙️ Installation

Follow these steps to get the project running locally in Android Studio:

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/Dhanushya2406/Event_Planner.git](https://github.com/Dhanushya2406/Event_Planner.git)
   ```

### Open Project
Open the project in **Android Studio**

### Run Application
- Sync Gradle files
- Connect a physical device or emulator
- Run the application

---
## Why This Project?

This project was built to demonstrate practical Android development concepts and real-world application architecture.

Key concepts implemented:

- MVVM Architecture
- Room Database
- CRUD Operations
- RecyclerView Implementation
- Android Navigation Flow
- Date and Time Picker Handling
- Repository Pattern
- Local Offline Data Storage
- UI State Management

---

## Author

**Dhanushya**  
Android Developer focused on Kotlin and Android application development.

🔗 GitHub Profile: [Dhanushya2406](https://github.com/Dhanushya2406)

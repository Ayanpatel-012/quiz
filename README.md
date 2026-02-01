# Quiz App

A simple trivia quiz app built with Jetpack Compose, Hilt, Room, and Retrofit.

## Features

- 10 multiple-choice questions per quiz
- 10-second countdown timer per question
- Offline support with Room database
- Automatic answer locking on timeout
- Immediate feedback (correct/incorrect)
- Score tracking
- Restart same quiz or start new quiz

## Screenshots

<p align="center">
  <img src="assets/Screenshot_2026-02-01_at_12.13.58_PM-ac1e4ccc-cab5-4d2d-bbf3-7d43d583ffa2.png" width="200" alt="Start Screen"/>
  <img src="assets/Screenshot_2026-02-01_at_12.22.39_PM-8cf05731-05b5-4f8c-86a9-55b5822f6668.png" width="200" alt="Active Question"/>
  <img src="assets/Screenshot_2026-02-01_at_12.22.50_PM-697dd293-58d3-4560-8568-94967a4417fa.png" width="200" alt="Answer Selected"/>
</p>

<p align="center">
  <img src="assets/Screenshot_2026-02-01_at_12.22.56_PM-f913969e-5cd6-4749-a83c-f6de5d15355b.png" width="200" alt="Answer Feedback"/>
  <img src="assets/Screenshot_2026-02-01_at_12.23.04_PM-a3f8d184-363b-4a64-95a0-dfd33f443520.png" width="200" alt="Correct Answer"/>
  <img src="assets/Screenshot_2026-02-01_at_12.23.35_PM-97868781-37d9-4702-9e75-069fad4474cc.png" width="200" alt="Quiz Completed"/>
</p>

## App States

### 1. **Initial State - Start Screen**
- Welcome message
- "Start Quiz" button
- Entry point to the app

### 2. **Loading State**
- Shows circular progress indicator
- Displayed while fetching questions from API
- Falls back to stored questions if API fails

### 3. **Quiz State - Active Question**
- Question counter (e.g., "Question 1 of 10")
- Countdown timer (10 seconds)
- Question text
- 4 shuffled multiple-choice options
- Options are clickable

### 4. **Quiz State - Answer Selected**
- Selected answer highlighted in dark grey
- "Submit Answer" button appears
- Timer continues counting down
- Can change selection before submit

### 5. **Quiz State - Answer Locked**
- Timer stops or reaches 0
- Correct answer highlighted in green
- Incorrect answer (if selected) highlighted in red
- Feedback message ("Correct!" or "Incorrect!")
- "Next Question" button appears

### 6. **Completed State**
- "Quiz Completed!" message
- Final score display (e.g., "3 / 10")
- "Restart Same Quiz" button (replays stored questions)
- "Start New Quiz" button (fetches new questions from API)

### 7. **Error State**
- Displayed if API fails and no stored questions available
- Shows error message
- User can retry by going back to start screen

## Architecture

- **UI Layer**: Jetpack Compose with ViewModel
- **Domain Layer**: Use Cases for business logic
- **Data Layer**: Repository pattern with Room (local) + Retrofit (remote)
- **DI**: Hilt for dependency injection

## Key Components

- `MainActivity` - Handles lifecycle events (pause/resume timer)
- `MainViewModel` - Manages quiz state and business logic
- `QuestionRepository` - Fetches and stores questions
- `QuizDatabase` - Room database for offline storage
- `QuestionService` - Retrofit API interface

## Timer Behavior

- Timer pauses when app goes to background
- Timer resumes when app comes to foreground
- Timer auto-locks answer at 0 seconds
- User can submit before timer expires

## API

Uses The Trivia API: `https://the-trivia-api.com/v2/questions`

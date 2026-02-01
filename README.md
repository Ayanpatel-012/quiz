# Quiz App

A simple trivia quiz app built with Jetpack Compose, Hilt, Room, and Retrofit.
link to download -> https://drive.google.com/file/d/1uONrZROn9y3TL4w-qQwZa7eWS7_4SvVO/view?usp=sharing

## Features

- 10 multiple-choice questions per quiz
- 10-second countdown timer per question
- Offline support with Room database
- Automatic answer locking on timeout
- Immediate feedback (correct/incorrect)
- Score tracking
- Restart same quiz or start new quiz

## Screenshots
<img width="455" height="936" alt="Screenshot 2026-02-01 at 12 13 58 PM" src="https://github.com/user-attachments/assets/9057b4ee-3d0e-4484-a5c3-d43518c0be97" />
<img width="477" height="939" alt="Screenshot 2026-02-01 at 12 22 39 PM" src="https://github.com/user-attachments/assets/f430c6be-770d-45f2-ada4-282f91857c5d" />
<img width="439" height="919" alt="Screenshot 2026-02-01 at 12 22 50 PM" src="https://github.com/user-attachments/assets/0afd580b-32fd-4de7-b919-7b76dae6360c" />
<img width="450" height="933" alt="Screenshot 2026-02-01 at 12 22 56 PM" src="https://github.com/user-attachments/assets/18fcafb7-56df-4678-8491-d94c3f59d25c" />
<img width="450" height="933" alt="Screenshot 2026-02-01 at 12 23 04 PM" src="https://github.com/user-attachments/assets/3c1c06eb-4d3f-49c0-b31d-7165587930f1" />
<img width="468" height="929" alt="Screenshot 2026-02-01 at 12 23 35 PM" src="https://github.com/user-attachments/assets/cd289d45-7c62-46de-9d86-a9dd1946c16d" />


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

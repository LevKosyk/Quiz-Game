# 🎮 QuizGame App  
QUIZGAME APP  
last-commit

🌐 Language Switch | Зміна мови  
🇺🇸 English  
🇺🇦 Українська  

📍 Overview  
**Android Quiz Game** built with Kotlin and Android Studio.  
Users can register or log in via **Firebase Authentication** (Email & Google). Once authenticated, they can take interactive quizzes, get scores, and view results. Guest users can also explore the app in limited mode.  

---

🚀 Getting Started  

☑️ Prerequisites  
- Language: Kotlin  
- Environment: Android Studio with SDK  
- Firebase project set up  

---

<h2>⚙️ Local Installation</h2>

<ol>
  <li>
    <strong>Clone the repository</strong>
    <pre><code>git clone https://github.com/LevKosyk/Quiz-Game.git
cd Quiz-Game</code></pre>
    <p>Open the project in <strong>Android Studio</strong>.</p>
  </li>

  <li>
    <strong>Connect Firebase</strong>
    <ol type="a">
      <li>Go to <a href="https://console.firebase.google.com/" target="_blank">Firebase Console</a>.</li>
      <li>Create a new Firebase project (or use an existing one).</li>
      <li>Add an <strong>Android App</strong> to the project.</li>
      <li>Register your app using your <strong>package name</strong>.</li>
      <li>Download the <code>google-services.json</code> file.</li>
      <li>Place it into the <code>app/</code> directory of your Android Studio project.</li>
    </ol>
  </li>

  <li>
    <strong>Enable Firebase Authentication</strong>
    <ul>
      <li>In Firebase Console, go to <strong>Authentication &gt; Sign-in method</strong>.</li>
      <li>Enable the following providers:
        <ul>
          <li>✅ <strong>Email/Password</strong></li>
          <li>✅ <strong>Google Sign-In</strong></li>
        </ul>
      </li>
    </ul>
  </li>

  <li>
    <strong>(Optional) Set up Firestore or Realtime Database</strong>
    <ul>
      <li>In <strong>Firebase Console</strong>, go to <strong>Firestore Database</strong> or <strong>Realtime Database</strong>.</li>
      <li>Create a database and optionally seed it with quiz questions or user data.</li>
    </ul>
  </li>

  <li>
    <strong>Build and Run</strong>
    <ul>
      <li>Use an emulator or a physical device.</li>
      <li>Click the ▶️ <strong>Run</strong> button in Android Studio.</li>
    </ul>
  </li>
</ol>

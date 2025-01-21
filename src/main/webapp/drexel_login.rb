puts <<-HTML
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Drexel Sign-In</title>
  <style>
    /* Basic Reset */
    * { margin: 0; padding: 0; box-sizing: border-box; }
    body {
      font-family: Arial, sans-serif;
      background-color: #E5F0F6; /* Example Drexel-inspired color */
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      height: 100vh;
    }
    .drexel-logo {
      width: 400px; 
      margin-bottom: 2rem;
      animation: fadeIn 2s forwards;
      opacity: 0;
    }

    .button-container {
      display: flex;
      gap: 1.5rem;
      margin-bottom: 1rem;
    }
    .drexel-button {
      background-color: #005DAB; /* Drexel brand-like color */
      color: #fff;
      padding: 1rem 2rem;
      text-decoration: none;
      border-radius: 4px;
      transition: background-color 0.3s ease;
      font-weight: bold;
      animation: bounceIn 1.5s ease;
    }
    .drexel-button:hover {
      background-color: #003D70; /* Darker shade */
    }
    .forgot-container {
      margin-top: 1rem;
    }
    .forgot-link {
      color: #005DAB;
      text-decoration: underline;
      font-size: 0.9rem;
    }

    /* Animations */
    @keyframes fadeIn {
      to { opacity: 1; }
    }
    @keyframes bounceIn {
      0%   { transform: scale(0.8); opacity: 0.5; }
      50%  { transform: scale(1.1); }
      100% { transform: scale(1); opacity: 1; }
    }
  </style>
</head>
<body>

  <!-- Drexel Logo -->
  <img src="https://www.drexelteam.com/wp-content/uploads/drexel-happiness-logo.jpg" 
       alt="Drexel Happiness Logo"
       class="drexel-logo">

  <!-- Sign In / Sign Up Buttons -->
  <div class="button-container">
    <a href="/drexel-app-0.0.1-SNAPSHOT/ruby-signin" class="drexel-button">Sign In</a>
    <a href="/drexel-app-0.0.1-SNAPSHOT/ruby-signup" class="drexel-button">Sign Up</a>
  </div>

  <!-- Forgot Password Link -->
  <div class="forgot-container">
    <a href="/drexel-app-0.0.1-SNAPSHOT/ruby-forgot" class="forgot-link">Forgot Password?</a>
  </div>

</body>
</html>
HTML

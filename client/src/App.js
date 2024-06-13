import React, { useState } from "react";
import Login from "./components/Login";
import './index.css'
import CustomerList from "./components/CustomerList";

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const handleLogin = (token) => {
    localStorage.setItem("jwtToken", token);
    setIsLoggedIn(true);
  };

  return (
    <div>
      {!isLoggedIn ? <Login onLogin={handleLogin} /> : <CustomerList />}
    </div>
  );
}

export default App;

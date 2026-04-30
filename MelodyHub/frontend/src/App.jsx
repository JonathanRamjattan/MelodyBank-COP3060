import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import Home from "./pages/Home";
import Data from "./pages/Data";
import Form from "./pages/Form";
import Register from "./pages/Register";
import Login from "./pages/Login";
import User from "./pages/User";
import "./App.css";
import logo from "./assets/logo.png";

function App() {
  return (
    <Router>
      <div className="app">
        <nav className="navbar">
          <Link to="/" className="logo-link">
            <img src={logo} alt="MelodyBank Logo" className="logo-img" />
          </Link>
          <div className="nav-links">
            <Link to="/">Dashboard</Link>
            <Link to="/data">MIDI Library</Link>
            <Link to="/form">Upload MIDI</Link>
            <Link to="/user">User</Link>
          </div>
        </nav>

        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/data" element={<Data />} />
          <Route path="/form" element={<Form />} />
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login />} />
          <Route path="/user" element={<User />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;

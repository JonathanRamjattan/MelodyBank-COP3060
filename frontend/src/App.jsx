import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import Home from "./pages/Home";
import Data from "./pages/Data";
import Form from "./pages/Form";
import "./App.css";

function App() {
  return (
    <Router>
      <div className="app">
        <nav className="navbar">
          <h2 className="logo">MelodyBank</h2>
          <div className="nav-links">
            <Link to="/">Dashboard</Link>
            <Link to="/data">MIDI Library</Link>
            <Link to="/form">Upload MIDI</Link>
          </div>
        </nav>

        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/data" element={<Data />} />
          <Route path="/form" element={<Form />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;

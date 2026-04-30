import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function Form() {
  const navigate = useNavigate();

  const [checkingLogin, setCheckingLogin] = useState(true);
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const [title, setTitle] = useState("");
  const [keySignature, setKeySignature] = useState("");
  const [tempoBpm, setTempoBpm] = useState("");
  const [category, setCategory] = useState("");
  const [file, setFile] = useState(null);
  const [message, setMessage] = useState("");

  useEffect(() => {
    axios
        .get("http://localhost:8080/api/auth/me", {
          withCredentials: true,
        })
        .then(() => {
          setIsLoggedIn(true);
          setCheckingLogin(false);
        })
        .catch(() => {
          setIsLoggedIn(false);
          setCheckingLogin(false);
          navigate("/login");
        });
  }, [navigate]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!isLoggedIn) {
      navigate("/login");
      return;
    }

    if (!title || !keySignature || !tempoBpm || !category || !file) {
      setMessage("All fields are required.");
      return;
    }

    if (isNaN(parseInt(tempoBpm))) {
      setMessage("BPM must be a number.");
      return;
    }

    if (
        !file.name.toLowerCase().endsWith(".mid") &&
        !file.name.toLowerCase().endsWith(".midi")
    ) {
      setMessage("Please upload a .mid or .midi file.");
      return;
    }

    const formData = new FormData();
    formData.append("title", title);
    formData.append("keySignature", keySignature);
    formData.append("tempoBpm", parseInt(tempoBpm));
    formData.append("category", category);
    formData.append("file", file);

    try {
      await axios.post("http://localhost:8080/api/midi/upload", formData, {
        withCredentials: true,
      });

      setMessage("MIDI uploaded successfully.");
      setTitle("");
      setKeySignature("");
      setTempoBpm("");
      setCategory("");
      setFile(null);

      document.getElementById("midi-file-input").value = "";
    } catch (error) {
      console.error("Upload error:", error);

      if (error.response?.status === 401 || error.response?.status === 403) {
        setMessage("You must be logged in to upload MIDI.");
        navigate("/login");
      } else if (error.response) {
        console.error("Status:", error.response.status);
        console.error("Backend response:", error.response.data);
        setMessage(`Upload failed: ${error.response.status}`);
      } else if (error.request) {
        console.error("No response from backend:", error.request);
        setMessage("No response from backend. Check Spring Boot or CORS.");
      } else {
        console.error("Axios error:", error.message);
        setMessage("Upload failed: " + error.message);
      }
    }
  };

  if (checkingLogin) {
    return (
        <main className="page">
          <h1>Checking login...</h1>
          <p>Please wait.</p>
        </main>
    );
  }

  return (
      <main className="page">
        <h1>Upload MIDI</h1>
        <p>Add a new melody, chord progression, or loop to the library.</p>

        <form className="form-box" onSubmit={handleSubmit}>
          <input
              placeholder="MIDI Title"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
          />

          <input
              placeholder="Key, example: C Minor"
              value={keySignature}
              onChange={(e) => setKeySignature(e.target.value)}
          />

          <input
              placeholder="BPM, example: 140"
              value={tempoBpm}
              onChange={(e) => setTempoBpm(e.target.value)}
          />

          <select value={category} onChange={(e) => setCategory(e.target.value)}>
            <option value="">Select Category</option>
            <option value="Chords">Chords</option>
            <option value="Melody">Melody</option>
            <option value="Loop">Loop</option>
            <option value="Bassline">Bassline</option>
            <option value="Arp">Arp</option>
          </select>

          <input
              id="midi-file-input"
              type="file"
              accept=".mid,.midi"
              onChange={(e) => setFile(e.target.files[0])}
          />

          <button type="submit">Upload MIDI</button>

          {message && <p>{message}</p>}
        </form>
      </main>
  );
}

export default Form;
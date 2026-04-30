import { useState } from "react";
import axios from "axios";
import { useNavigate, Link } from "react-router-dom";

function Register() {
    const navigate = useNavigate();

    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [creator, setCreator] = useState(true);
    const [message, setMessage] = useState("");
    const [loading, setLoading] = useState(false);

    const handleRegister = async (e) => {
        e.preventDefault();
        setMessage("");

        if (!username || !email || !password) {
            setMessage("Username, email, and password are required.");
            return;
        }

        if (password.length < 6) {
            setMessage("Password should be at least 6 characters.");
            return;
        }

        try {
            setLoading(true);

            const response = await axios.post(
                "http://localhost:8080/api/auth/register",
                {
                    username,
                    email,
                    password,
                    creator,
                },
                {
                    withCredentials: true,
                }
            );

            localStorage.setItem("user", JSON.stringify(response.data));

            setMessage("Account created successfully.");

            setTimeout(() => {
                navigate("/");
            }, 700);
        } catch (error) {
            if (error.response?.data) {
                setMessage(error.response.data);
            } else {
                setMessage("Unable to register. Make sure the backend is running.");
            }
        } finally {
            setLoading(false);
        }
    };

    return (
        <main className="page auth-page">
            <section className="auth-card">
                <h1>Create Account</h1>
                <p>Join MelodyBank to save your favorites.</p>

                <form className="auth-form" onSubmit={handleRegister}>
                    <label>
                        Username
                        <input
                            type="text"
                            placeholder="example: melodymaker"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                        />
                    </label>

                    <label>
                        Email
                        <input
                            type="email"
                            placeholder="example@email.com"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />
                    </label>

                    <label>
                        Password
                        <input
                            type="password"
                            placeholder="At least 6 characters"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </label>

                    <label className="checkbox-row">
                        <input
                            type="checkbox"
                            checked={creator}
                            onChange={(e) => setCreator(e.target.checked)}
                        />
                        I want to upload MIDI files
                    </label>

                    <button type="submit" disabled={loading}>
                        {loading ? "Creating Account..." : "Register"}
                    </button>

                    {message && <p className="form-message">{message}</p>}
                </form>

                <p className="auth-switch">
                    Already have an account? <Link to="/login">Login</Link>
                </p>
            </section>
        </main>
    );
}

export default Register;
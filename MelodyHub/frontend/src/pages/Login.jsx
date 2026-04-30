import { useState } from "react";
import axios from "axios";
import { useNavigate, Link } from "react-router-dom";

function Login() {
    const navigate = useNavigate();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");
    const [loading, setLoading] = useState(false);

    const handleLogin = async (e) => {
        e.preventDefault();
        setMessage("");

        if (!username || !password) {
            setMessage("Username and password are required.");
            return;
        }

        try {
            setLoading(true);

            const response = await axios.post(
                "http://localhost:8080/api/auth/login",
                {
                    username,
                    password,
                },
                {
                    withCredentials: true,
                }
            );

            localStorage.setItem("user", JSON.stringify(response.data));

            setMessage("Login successful.");

            setTimeout(() => {
                navigate("/");
            }, 700);
        } catch (error) {
            if (error.response?.data) {
                setMessage(error.response.data);
            } else {
                setMessage("Unable to login. Make sure the backend is running.");
            }
        } finally {
            setLoading(false);
        }
    };

    return (
        <main className="page auth-page">
            <section className="auth-card">
                <h1>Login</h1>
                <p>Sign in to upload and manage MIDI sequences.</p>

                <form className="auth-form" onSubmit={handleLogin}>
                    <label>
                        Username
                        <input
                            type="text"
                            placeholder="Enter your username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                        />
                    </label>

                    <label>
                        Password
                        <input
                            type="password"
                            placeholder="Enter your password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </label>

                    <button type="submit" disabled={loading}>
                        {loading ? "Logging In..." : "Login"}
                    </button>

                    {message && <p className="form-message">{message}</p>}
                </form>

                <p className="auth-switch">
                    Don&apos;t have an account? <Link to="/register">Register</Link>
                </p>
            </section>
        </main>
    );
}

export default Login;
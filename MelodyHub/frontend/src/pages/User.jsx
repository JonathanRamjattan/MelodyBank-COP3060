import { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";


function User() {
    const [user, setUser] = useState(null);
    const [checkingLogin, setCheckingLogin] = useState(true);
    const [favorites, setFavorites] = useState([]);
    useEffect(() => {
        axios
            .get("http://localhost:8080/api/auth/me", {
                withCredentials: true,
            })
            .then((response) => {
                setUser(response.data);
                localStorage.setItem("user", JSON.stringify(response.data));
                setCheckingLogin(false);

                axios
                    .get("http://localhost:8080/api/favorites", {
                        withCredentials: true,
                    })
                    .then((res) => {
                        setFavorites(res.data);
                    })
                    .catch((err) => {
                        console.error("Could not load favorites:", err);
                    });
            })
            .catch(() => {
                setUser(null);
                localStorage.removeItem("user");
                setCheckingLogin(false);
            });
    }, []);

    const handleLogout = async () => {
        try {
            await axios.post(
                "http://localhost:8080/api/auth/logout",
                {},
                {
                    withCredentials: true,
                }
            );

            localStorage.removeItem("user");
            setUser(null);
        } catch (error) {
            console.error("Logout failed:", error);
        }
    };

    if (checkingLogin) {
        return (
            <main className="page user-page">
                <h1>Checking account...</h1>
            </main>
        );
    }

    if (!user) {
        return (
            <main className="page user-page">
                <section className="auth-card user-card">
                    <h1>Account</h1>
                    <p>You need an account to upload and manage MIDI files.</p>

                    <div className="user-actions">
                        <Link to="/login" className="primary-action">
                            Login
                        </Link>

                        <Link to="/register" className="secondary-action">
                            Register
                        </Link>
                    </div>
                </section>
            </main>
        );
    }

    return (
        <main className="page user-page">
            <section className="auth-card user-card">
                <h1>Welcome, {user.username}</h1>
                <p>This is your MelodyBank account page.</p>

                <div className="profile-info">
                    <p>
                        <strong>Username:</strong> {user.username}
                    </p>

                    <p>
                        <strong>Email:</strong> {user.email}
                    </p>

                    <p>
                        <strong>Creator Account:</strong> {user.creator ? "Yes" : "No"}
                    </p>
                </div>

                <div className="user-actions">
                    <Link to="/form" className="primary-action">
                        Upload MIDI
                    </Link>

                    <Link to="/data" className="secondary-action">
                        View Library
                    </Link>

                    <button className="logout-button" onClick={handleLogout}>
                        Logout
                    </button>
                </div>
                <div className="favorites-section">
                    <h2>Your Favorite MIDI Files</h2>

                    {favorites.length === 0 ? (
                        <p>You have no favorites as of yet.</p>
                    ) : (
                        <div className="favorite-list">
                            {favorites.map((midi) => (
                                <div className="favorite-item" key={midi.id}>
                                    <div>
                                        <strong>{midi.title}</strong>
                                        <p>
                                            {midi.keySignature} · {midi.tempoBpm} BPM · {midi.category}
                                        </p>
                                    </div>

                                    {midi.filePath && (
                                        <a
                                            href={`http://localhost:8080${midi.filePath}`}
                                            download
                                            className="download-link"
                                        >
                                            Download
                                        </a>
                                    )}
                                </div>
                            ))}
                        </div>
                    )}
                </div>
            </section>
        </main>
    );
}

export default User;
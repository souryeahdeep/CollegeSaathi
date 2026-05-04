import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const AdminLogin = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [id, setId] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [showPassword, setShowPassword] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    if (!username.trim() || !password.trim() || !id.trim()) {
      setError("Username, Password, and ID are required.");
      return;
    }

    setLoading(true);

    // API URL can be provided via Vite env: VITE_ADMIN_LOGIN_API
    // Fallback to http://localhost:8080/admin
    const baseApi =
      import.meta.env.VITE_ADMIN_LOGIN_API || "http://localhost:8080/admin";

    try {
      // Create HTTP Basic Authentication header (base64 encoded: username:password)
      const credentials = btoa(`${username.trim()}:${password.trim()}`);
      
      // Construct URL with ID as path parameter: /admin/{id}
      const url = `${baseApi}/${encodeURIComponent(id.trim())}`;

      console.log("Admin Login Request:", { url, username: username.trim() });

      const res = await fetch(url, {
        method: "GET",
        headers: {
          "Authorization": `Basic ${credentials}`,
        },
      });

      console.log("Response Status:", res.status);

      if (!res.ok) {
        const msg = res.status === 401 
          ? "Invalid credentials. Ensure username is 'admin' and password is 'admin123'." 
          : "Invalid credentials or server error.";
        setError(msg);
        setLoading(false);
        return;
      }

      // The endpoint returns a Boolean value
      const isAdmin = await res.json().catch(() => false);
      
      if (isAdmin === true) {
        // success -> navigate to Admin Dashboard
        navigate("/admin-dashboard");
      } else {
        setError("Access denied. Admin privileges required.");
      }
    } catch (err) {
      setError("Network error. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-slate-50 flex items-center justify-center p-6">
      <div className="w-full max-w-md bg-white rounded-2xl shadow-lg p-8">
        <h1 className="text-2xl font-bold text-center text-gray-800 mb-6">
          Administration Login
        </h1>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">
              ID
            </label>
            <input
              type="text"
              value={id}
              onChange={(e) => setId(e.target.value)}
              className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-400"
              placeholder="Enter admin ID"
              autoComplete="off"
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Username
            </label>
            <input
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-400"
              placeholder="Enter admin username"
              autoComplete="username"
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Password
            </label>
            <div className="relative">
              <input
                type={showPassword ? "text" : "password"}
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-400"
                placeholder="Enter admin password"
                autoComplete="current-password"
              />
              <button
                type="button"
                onClick={() => setShowPassword(!showPassword)}
                className="absolute right-3 top-2 text-gray-600 text-sm hover:text-gray-800"
              >
                {showPassword ? "Hide" : "Show"}
              </button>
            </div>
          </div>

          {error && (
            <div className="text-sm text-red-600 bg-red-50 border border-red-100 rounded p-2">
              {error}
            </div>
          )}

          <button
            type="submit"
            disabled={loading}
            className="w-full mt-2 px-4 py-3 bg-indigo-600 text-white rounded-lg font-semibold hover:bg-indigo-700 disabled:opacity-60"
          >
            {loading ? "Checking..." : "Login"}
          </button>
        </form>
      </div>
    </div>
  );
};

export default AdminLogin;

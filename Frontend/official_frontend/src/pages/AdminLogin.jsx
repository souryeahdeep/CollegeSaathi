import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const AdminLogin = () => {
  const navigate = useNavigate();
  const [name, setName] = useState("");
  const [id, setId] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [showId, setShowId] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    if (!name.trim() || !id.trim()) {
      setError("Both Name and Id are required.");
      return;
    }

    setLoading(true);

    // API URL can be provided via Vite env: VITE_ADMIN_LOGIN_API
    // Fallback to http://localhost:8080/admin. The fallback supports either
    // an `{id}` placeholder (e.g. "http://host/admin/{id}") or will append the id
    // as a path segment (e.g. "http://host/admin/<id>").
    const baseApi =
      import.meta.env.VITE_ADMIN_LOGIN_API || "http://localhost:8080/admin";
    const url = baseApi.includes("{id}")
      ? baseApi.replace("{id}", encodeURIComponent(id.trim()))
      : `${baseApi.replace(/\/$/, "")}/${encodeURIComponent(id.trim())}`;

    try {
      const res = await fetch(url, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name: name.trim(), id: id.trim() }),
      });

      if (!res.ok) {
        const body = await res.json().catch(() => ({}));
        const msg = body?.message || "Invalid credentials or server error.";
        setError(msg);
        setLoading(false);
        return;
      }

      const data = await res.json().catch(() => ({}));
      // Accept either explicit { success: true } or simply 200 OK
      if (data?.success === false) {
        setError(data?.message || "Invalid credentials.");
        setLoading(false);
        return;
      }

      // success -> navigate to Admin Dashboard where options live
      navigate("/admin-dashboard");
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
              Name
            </label>
            <input
              type="text"
              value={name}
              onChange={(e) => setName(e.target.value)}
              className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-400"
              placeholder="Enter admin name"
              autoComplete="name"
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Id
            </label>
            <input
              type="text"
              value={id}
              onChange={(e) => setId(e.target.value)}
              className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-400"
              placeholder="Enter admin id"
              autoComplete="off"
            />
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

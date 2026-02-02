import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";

const AtttendancePage = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const classData = location.state?.classData || null;
  const attendance = location.state?.attendance || null;
  const [students, setStudents] = useState([]);
  const [studentsLoading, setStudentsLoading] = useState(false);
  const [studentsError, setStudentsError] = useState("");

  const handleBack = () => navigate(-1);

  useEffect(() => {
    const controller = new AbortController();
    const fetchStudents = async () => {
      setStudentsLoading(true);
      setStudentsError("");
      let sem = classData.subjectCode[classData.subjectCode.length - 3];
      let year = 0;
      if (sem == "1" || sem == "2") {
        year = 1;
      } else if (sem == "3" || sem == "4") {
        year = 2;
      } else if (sem == "5" || sem == "6") {
        year = 3;
      } else if (sem == "7" || sem == "8") {
        year = 4;
      }
      console.log(sem);
      try {
        const apiUrl = `http://localhost:8080/student/get?branch=${encodeURIComponent(
          classData.stream
        )}&sem=${encodeURIComponent(sem)}&group=${encodeURIComponent(
          classData.groupNo
        )}&section=${encodeURIComponent(classData.sectionNo)}`;
        console.log(apiUrl);

        const response = await fetch(apiUrl, {
          method: "GET",
          headers: { "Content-Type": "application/json" },
          signal: controller.signal,
        });
        console.log(response);

        const body = await response.json().catch(() => null);
        if (!response.ok) {
          throw new Error(body?.message || "Failed to fetch student list.");
        }

        console.log(body);
        const list = Array.isArray(body?.students)
          ? body.students
          : Array.isArray(body)
          ? body
          : [];
        setStudents(list);
      } catch (err) {
        if (err.name === "AbortError") return;
        setStudents([]);
        setStudentsError(err.message || "Unable to load students.");
      } finally {
        setStudentsLoading(false);
      }
    };

    fetchStudents();
    return () => controller.abort();
  }, [attendance?.token]);

  // Revoke blob URLs on unmount to avoid leaks when the QR is loaded as an object URL.
  useEffect(() => {
    return () => {
      if (attendance?.qrDataUrl && attendance.qrDataUrl.startsWith("blob:")) {
        URL.revokeObjectURL(attendance.qrDataUrl);
      }
    };
  }, [attendance?.qrDataUrl]);

  if (!classData || !attendance) {
    return (
      <div className="min-h-screen bg-slate-50 flex items-center justify-center p-6">
        <div className="max-w-lg w-full bg-white rounded-2xl shadow p-8 text-center">
          <h1 className="text-2xl font-semibold text-gray-900 mb-4">
            Attendance Data Missing
          </h1>
          <p className="text-gray-600 mb-6">
            We could not find any attendance session details. Please start from
            the allotted classes page and try again.
          </p>
          <button
            onClick={handleBack}
            className="px-5 py-2.5 bg-indigo-600 text-white rounded-lg font-medium hover:bg-indigo-700 transition"
          >
            Go Back
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-slate-50 p-6">
      <div className="max-w-4xl mx-auto space-y-6">
        <div className="flex items-center justify-between">
          <div>
            <p className="text-sm text-gray-500">Report for</p>
            <h1 className="text-3xl font-bold text-gray-900">
              {classData.stream || "Class Session"}
            </h1>
            <p className="text-gray-600">
              {classData.subjectCode || "Subject"} • Section{" "}
              {classData.sectionNo || "N/A"} • Group{" "}
              {classData.groupNo || "N/A"}
            </p>
          </div>
          <button
            onClick={handleBack}
            className="px-4 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-100"
          >
            Back
          </button>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div className="bg-white rounded-xl shadow border border-gray-100 p-6 space-y-4">
            <h2 className="text-lg font-semibold text-gray-900">
              Class Details
            </h2>
            <div className="text-sm text-gray-600 space-y-2">
              <p>
                <span className="font-medium text-gray-800">Stream Code:</span>{" "}
                {classData.stream || "N/A"}
              </p>
              <p>
                <span className="font-medium text-gray-800">Subject:</span>{" "}
                {classData.subjectCode || "N/A"}
              </p>
              <p>
                <span className="font-medium text-gray-800">Section:</span>{" "}
                {classData.sectionNo || "N/A"}
              </p>
              <p>
                <span className="font-medium text-gray-800">Group:</span>{" "}
                {classData.groupNo || "N/A"}
              </p>
              <p>
                <span className="font-medium text-gray-800">Time:</span>{" "}
                {classData.startTime || "N/A"}
              </p>
            </div>
          </div>

          <div className="bg-white rounded-xl shadow border border-gray-100 p-6 space-y-4">
            <h2 className="text-lg font-semibold text-gray-900">
              Attendance Session
            </h2>
            
            {attendance.qrDataUrl ? (
              <div className="flex flex-col items-center">
                <img
                  src={attendance.qrDataUrl}
                  alt="Attendance QR"
                  className="w-48 h-48 object-contain border border-gray-200 rounded-lg"
                />
                <p className="mt-2 text-xs text-gray-500">
                  Scan this QR to log attendance.
                </p>
              </div>
            ) : (
              <p className="text-sm text-gray-500">No QR data available.</p>
            )}
          </div>
        </div>

        <div className="bg-white rounded-xl shadow border border-gray-100 p-6">
          <div className="flex items-center justify-between mb-4">
            <div>
              <h2 className="text-lg font-semibold text-gray-900">Students</h2>
              <p className="text-sm text-gray-500">
                Auto-fetched when the report opens.
              </p>
            </div>
            {studentsLoading && (
              <span className="text-xs font-medium text-indigo-600">
                Loading...
              </span>
            )}
          </div>

          {studentsError && (
            <div className="mb-4 text-sm text-red-600 bg-red-50 border border-red-100 rounded-lg p-3">
              {studentsError}
            </div>
          )}

          {!studentsLoading && !studentsError && students.length === 0 && (
            <p className="text-sm text-gray-500">
              No students have been recorded yet.
            </p>
          )}

          <ul className="divide-y divide-gray-100">
            {students.map((student, index) => (
              <li
                key={student?.id || index}
                className="py-3 flex items-center justify-between"
              >
                <div>
                  <p className="text-sm font-semibold text-gray-900">
                    {student?.studentName || "Unknown Student"}
                  </p>
                  <p className="text-xs text-gray-500">
                    ID: {student?.studentId || "N/A"}
                  </p>
                </div>
              </li>
            ))}
          </ul>
        </div>
      </div>
    </div>
  );
};

export default AtttendancePage;

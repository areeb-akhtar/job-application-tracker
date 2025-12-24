const API_BASE = "/api/applications";

const tableBody = document.getElementById("tableBody");
const statusFilter = document.getElementById("statusFilter");
const refreshBtn = document.getElementById("refreshBtn");
const errorBox = document.getElementById("errorBox");
const statusPill = document.getElementById("statusPill");

const createForm = document.getElementById("createForm");
const companyInput = document.getElementById("company");
const positionInput = document.getElementById("position");
const jobUrlInput = document.getElementById("jobUrl");
const resetBtn = document.getElementById("resetBtn");
const formHint = document.getElementById("formHint");

function setPill(ok, text) {
  statusPill.textContent = text;
  statusPill.classList.remove("ok", "bad");
  statusPill.classList.add(ok ? "ok" : "bad");
}

function setError(msg) {
  errorBox.textContent = msg || "";
}

function safeText(value) {
  return value === null || value === undefined ? "" : String(value);
}

function linkCell(url) {
  const u = safeText(url).trim();
  if (!u) return `<span class="muted">None</span>`;
  const escaped = u.replaceAll('"', "&quot;");
  return `<a class="link" href="${escaped}" target="_blank" rel="noreferrer">Open</a>`;
}

async function apiFetch(path = "", options = {}) {
  const res = await fetch(`${API_BASE}${path}`, {
    headers: { "Content-Type": "application/json" },
    ...options,
  });

  if (res.status === 204) return null;

  const contentType = res.headers.get("content-type") || "";
  const isJson = contentType.includes("application/json");
  const data = isJson ? await res.json() : await res.text();

  if (!res.ok) {
    const message =
      typeof data === "string"
        ? data
        : data?.message || data?.error || `Request failed with status ${res.status}`;
    throw new Error(message);
  }

  return data;
}

function renderRows(apps) {
  if (!apps || apps.length === 0) {
    tableBody.innerHTML = `<tr><td colspan="6" class="muted">No applications yet. Add one above.</td></tr>`;
    return;
  }

  const rows = apps
    .map((a) => {
      const id = a.id;
      const company = safeText(a.company);
      const position = safeText(a.position);
      const status = safeText(a.status);
      const appliedDate = safeText(a.appliedDate);
      const urlHtml = linkCell(a.jobUrl);

      return `
        <tr data-id="${id}">
          <td>${company}</td>
          <td>${position}</td>
          <td>
            <span class="pill status-${status}">
            ${status}
            </span>
            </td>

          <td>${appliedDate}</td>
          <td>${urlHtml}</td>
          <td>
            <div class="actionRow">
              <select class="statusSelect" aria-label="Status">
                <option value="APPLIED" ${status === "APPLIED" ? "selected" : ""}>APPLIED</option>
                <option value="INTERVIEW" ${status === "INTERVIEW" ? "selected" : ""}>INTERVIEW</option>
                <option value="OFFER" ${status === "OFFER" ? "selected" : ""}>OFFER</option>
                <option value="REJECTED" ${status === "REJECTED" ? "selected" : ""}>REJECTED</option>
              </select>
              <button class="btn primary updateBtn" type="button">Update</button>
              <button class="btn danger deleteBtn" type="button">Delete</button>
            </div>
          </td>
        </tr>
      `;
    })
    .join("");

  tableBody.innerHTML = rows;
}

async function loadApplications() {
  setError("");

  const status = statusFilter.value.trim();
  const query = status ? `?status=${encodeURIComponent(status)}` : "";

  try {
    const apps = await apiFetch(query, { method: "GET" });
    renderRows(apps);
    setPill(true, "Connected");
  } catch (e) {
    setPill(false, "API error");
    setError(e.message);
    tableBody.innerHTML = `<tr><td colspan="6" class="muted">Could not load applications.</td></tr>`;
  }
}

async function createApplication() {
  setError("");
  formHint.textContent = "Saving…";

  const payload = {
    company: companyInput.value,
    position: positionInput.value,
    jobUrl: jobUrlInput.value,
  };

  try {
    await apiFetch("", { method: "POST", body: JSON.stringify(payload) });
    formHint.textContent = "Saved.";
    createForm.reset();
    await loadApplications();
    setTimeout(() => (formHint.textContent = ""), 1200);
  } catch (e) {
    formHint.textContent = "";
    setError(e.message);
  }
}

async function updateStatus(id, newStatus) {
  setError("");

  try {
    await apiFetch(`/${id}/status`, {
      method: "PATCH",
      body: JSON.stringify({ status: newStatus }),
    });
    await loadApplications();
  } catch (e) {
    setError(e.message);
  }
}

async function deleteApplication(id) {
  setError("");

  try {
    await apiFetch(`/${id}`, { method: "DELETE" });
    await loadApplications();
  } catch (e) {
    setError(e.message);
  }
}

createForm.addEventListener("submit", async (evt) => {
  evt.preventDefault();
  await createApplication();
});

resetBtn.addEventListener("click", () => {
  createForm.reset();
  formHint.textContent = "";
  setError("");
});

refreshBtn.addEventListener("click", async () => {
  await loadApplications();
});

statusFilter.addEventListener("change", async () => {
  await loadApplications();
});

tableBody.addEventListener("click", async (evt) => {
  const row = evt.target.closest("tr[data-id]");
  if (!row) return;

  const id = row.getAttribute("data-id");
  const statusSelect = row.querySelector(".statusSelect");

  if (evt.target.classList.contains("updateBtn")) {
    await updateStatus(id, statusSelect.value);
  }

  if (evt.target.classList.contains("deleteBtn")) {
    await deleteApplication(id);
  }
});

(async function init() {
  setPill(true, "Connecting…");
  await loadApplications();
})();

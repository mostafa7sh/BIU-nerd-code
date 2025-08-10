import { useEffect, useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { useTheme } from '../context/ThemeContext';
import SidebarLayout from '../components/SidebarLayout';
import { authFetch } from '../utils/authFetch';
import './Labels.css';

export default function Labels() {
  const { token } = useAuth();
  const { isDarkMode } = useTheme();
  const [labels, setLabels] = useState([]);
  const [newLabel, setNewLabel] = useState('');
  const [error, setError] = useState('');

  useEffect(() => {
    const favicon = document.querySelector("link[rel='icon']");
    if (favicon) {
      favicon.href = isDarkMode ? '/hot-icon.png' : '/ice-icon.png';
    }
    document.title = isDarkMode ? 'HotMail - Labels' : 'IceMail - Labels';
  }, [isDarkMode]);

  useEffect(() => {
    const fetchLabels = async () => {
      try {
        const res = await authFetch('/api/labels', { token });
        const data = await res.json();
        if (!res.ok) throw new Error(data.error || 'Failed to fetch labels');
        setLabels(data);
      } catch (err) {
        setError(err.message);
      }
    };
    fetchLabels();
  }, [token]);

  const handleCreate = async (e) => {
    e.preventDefault();
    try {
      const res = await authFetch('/api/labels', {
        method: 'POST',
        token,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name: newLabel }),
      });
      const data = await res.json();
      if (!res.ok) throw new Error(data.error || 'Create failed');
      setNewLabel('');
      setLabels((prev) => [...prev, data]);
    } catch (err) {
      setError(err.message);
    }
  };

  const handleDelete = async (id) => {
    try {
      await authFetch(`/api/labels/${id}`, {
        method: 'DELETE',
        token,
      });
      setLabels((prev) => prev.filter((label) => label.id !== id));
    } catch (err) {
      setError('Delete failed');
    }
  };

  return (
    <SidebarLayout>
      <div className="labels-container">
        <h2 className="labels-title">Labels</h2>

        {error && <p className="error-msg">{error}</p>}

        <form className="label-form" onSubmit={handleCreate}>
          <input
            value={newLabel}
            onChange={(e) => setNewLabel(e.target.value)}
            placeholder="New label name"
            required
            className="label-input"
          />
          <button type="submit" className="create-btn">Create</button>
        </form>

        <ul className="labels-list">
          {labels.map((label) => (
            <li key={label.id} className="label-item">
              <span className="label-name">{label.name}</span>
              <button onClick={() => handleDelete(label.id)} className="delete-btn">
                Delete
              </button>
            </li>
          ))}
        </ul>
      </div>
    </SidebarLayout>
  );
}

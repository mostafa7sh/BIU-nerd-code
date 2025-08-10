const User = require('../models/User');
const blacklistService = require('../services/BlacklistService');

const addToBlacklist = async (req, res) => {
  const user = await User.findById(req.userId);
  if (!user) return res.status(400).json({ error: 'User not found.' });

  const { link } = req.body;
  if (!link) return res.status(400).json({ error: 'Link is required' });

  try {
    const result = await blacklistService.addToBlacklist(link);
    if (result.error) {
      return res.status(result.status || 400).json({ error: result.error });
    }
    res.status(201).json(result.data);
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Internal server error' });
  }
}

const removeFromBlacklist = async (req, res) => {
  const user = await User.findById(req.userId);
  if (!user) return res.status(400).json({ error: 'User not found.' });

  try {
    const result = await blacklistService.removeFromBlacklist(req.params.id);
    if (result.error) {
      return res.status(result.status || 400).json({ error: result.error });
    }
    res.status(204).end();
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Internal server error' });
  }
}

module.exports = { addToBlacklist, removeFromBlacklist }

const Blacklist = require('../models/Blacklist');
const { sendCommand } = require('./BloomService');

const addToBlacklist = async (link) => {
  const exists = await Blacklist.exists({ link });
  if (exists) return { error: 'Link already blacklisted', status: 409 };

  const response = await sendCommand(`POST ${link}`);
  if (!response.startsWith('201')) {
    return { error: `Bloom server rejected: ${response}` };
  }

  const newItem = await Blacklist.create({ link });
  return { data: newItem };
}

const removeFromBlacklist = async (id) => {
  const item = await Blacklist.findById(id);
  if (!item) return { error: 'Blacklist item not found', status: 404 };

  const response = await sendCommand(`DELETE ${item.link}`);
  if (!response.startsWith('204')) {
    return { error: `Bloom server rejected: ${response}` };
  }

  await item.deleteOne();
  return { data: true };
}

module.exports = { addToBlacklist, removeFromBlacklist }

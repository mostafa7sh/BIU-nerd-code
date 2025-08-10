const Label = require('../models/Label');
const User = require('../models/User');

const createLabel = async (userId, name) => {
  if (!name) return { error: 'Name is required' };

  const user = await User.findById(userId);
  if (!user) return { error: 'User not found', status: 404 };

  const exists = await Label.findOne({ name, userId });
  if (exists) return { error: 'Label already exists' };

  const newLabel = new Label({ name, userId });
  await newLabel.save();
  return { data: newLabel };
};

const getLabelsByUser = async (userId) => {
  const user = await User.findById(userId);
  if (!user) return { error: 'User not found', status: 404 };

  const labels = await Label.find({ userId });
  return { data: labels };
};

const getLabelById = async (userId, labelId) => {
  const user = await User.findById(userId);
  if (!user) return { error: 'User not found', status: 404 };

  const label = await Label.findOne({ _id: labelId, userId });
  if (!label) return { error: 'Label not found', status: 404 };

  return { data: label };
};

const updateLabel = async (userId, labelId, name) => {
  if (!name) return { error: 'Name is required' };

  const user = await User.findById(userId);
  if (!user) return { error: 'User not found', status: 404 };

  const label = await Label.findOneAndUpdate(
    { _id: labelId, userId },
    { name },
    { new: true }
  );

  if (!label) return { error: 'Label not found', status: 404 };
  return { data: label };
};

const deleteLabel = async (userId, labelId) => {
  const user = await User.findById(userId);
  if (!user) return { error: 'User not found', status: 404 };

  const deletedLabel = await Label.findOneAndDelete({ _id: labelId, userId });
  if (!deletedLabel) return { error: 'Label not found or not authorized to delete', status: 404 };

  return { data: true };
};

module.exports = {
  createLabel,
  getLabelsByUser,
  getLabelById,
  updateLabel,
  deleteLabel,
};

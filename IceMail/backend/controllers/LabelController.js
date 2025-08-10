const labelService = require('../services/LabelService');

const createLabel = async (req, res) => {
  const result = await labelService.createLabel(req.userId, req.body.name);
  if (result.error) return res.status(result.status || 400).json({ error: result.error });
  res.status(201).json(result.data);
}

const getLabelsByUser = async (req, res) => {
  const result = await labelService.getLabelsByUser(req.userId);
  if (result.error) return res.status(result.status || 400).json({ error: result.error });
  res.json(result.data);
}

const getLabelsById = async (req, res) => {
  const result = await labelService.getLabelById(req.userId, req.params.id);
  if (result.error) return res.status(result.status || 404).json({ error: result.error });
  res.json(result.data);
}

const updateLabel = async (req, res) => {
  const result = await labelService.updateLabel(req.userId, req.params.id, req.body.name);
  if (result.error) return res.status(result.status || 400).json({ error: result.error });
  res.json(result.data);
}

const deleteLabel = async (req, res) => {
  const result = await labelService.deleteLabel(req.userId, req.params.id);
  if (result.error) return res.status(result.status || 400).json({ error: result.error });
  res.status(204).end();
}

module.exports = { createLabel, getLabelsByUser, getLabelsById, updateLabel, deleteLabel }

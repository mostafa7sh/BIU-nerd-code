const userService = require('../services/UserService');

const signup = async (req, res) => {
  const result = await userService.signup(req.body, req.file);
  if (result.error) return res.status(result.status || 400).json({ error: result.error });
  res.status(201).json(result);
}

const login = async (req, res) => {
  const result = await userService.login(req.body);
  if (result.error) return res.status(result.status || 400).json({ error: result.error });
  res.status(200).json(result);
}

const getUserInfo = async (req, res) => {
  const result = await userService.getUserInfo(req.userId);
  if (result.error) return res.status(result.status || 404).json({ error: result.error });
  res.json(result);
}

const changePassword = async (req, res) => {
  const result = await userService.changePassword(req.userId, req.body);
  if (result.error) return res.status(result.status || 400).json({ error: result.error });
  res.json({ message: result.message });
}

const updateUser = async (req, res) => {
  const result = await userService.updateUser(req.userId, req.body, req.file);
  if (result.error) return res.status(result.status || 400).json({ error: result.error });
  res.json(result);
}

module.exports = { signup, login, getUserInfo, changePassword, updateUser }

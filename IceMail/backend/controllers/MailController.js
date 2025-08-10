const mailService = require('../services/MailService');

const createMail = async (req, res) => {
  try {
    const result = await mailService.createMail({
      userId: req.userId,
      to: req.body.to,
      subject: req.body.subject,
      body: req.body.body,
      files: req.files
    });

    res.status(201).json(result);
  } catch (err) {
    console.error(err);
    res.status(err.status || 500).json({ error: err.message });
  }
}

const createDraftMail = async (req, res) => {
  try {
    const result = await mailService.createDraftMail({
      userId: req.userId,
      to: req.body.to,
      subject: req.body.subject,
      body: req.body.body,
      files: req.files
    });

    res.status(201).json(result);
  } catch (err) {
    console.error(err);
    res.status(err.status || 500).json({ error: err.message });
  }
}

const getUserMails = async (req, res) => {
  try {
    const mails = await mailService.getUserMails(req.userId);
    res.json(mails);
  } catch (err) {
    console.error(err);
    res.status(err.status || 500).json({ error: err.message || 'Server error' });
  }
}

const getIdMails = async (req, res) => {
  try {
    const mail = await mailService.getMailById(req.userId, req.params.id);
    res.json(mail);
  } catch (err) {
    console.error(err);
    res.status(err.status || 500).json({ error: err.message || 'Server error' });
  }
}

const updateMail = async (req, res) => {
  try {
    const updatedMail = await mailService.updateMail(req.userId, req.params.id, req.body);
    res.json(updatedMail);
  } catch (err) {
    console.error(err);
    res.status(err.status || 500).json({ error: err.message || 'Server error' });
  }
}

const assignLabelsToMail = async (req, res) => {
  try {
    const updatedMail = await mailService.assignLabelsToMail(req.userId, req.params.id, req.body.labels);
    res.json(updatedMail);
  } catch (err) {
    console.error(err);
    res.status(err.status || 500).json({ error: err.message || 'Server error' });
  }
}

const deleteMail = async (req, res) => {
  try {
    await mailService.deleteMail(req.userId, req.params.id);
    res.status(204).end();
  } catch (err) {
    console.error(err);
    res.status(err.status || 500).json({ error: err.message || 'Server error' });
  }
}

const search = async (req, res) => {
  try {
    const results = await mailService.searchMails(req.userId, req.params.query);
    res.json(results);
  } catch (err) {
    console.error(err);
    res.status(err.status || 500).json({ error: err.message || 'Server error' });
  }
}

const bulkUpdateReadStatus = async (req, res) => {
  try {
    const message = await mailService.bulkUpdateReadStatus(req.userId, req.body);
    res.json({ message });
  } catch (err) {
    console.error(err);
    res.status(err.status || 500).json({ error: err.message || 'Server error' });
  }
}

const markAsImportant = async (req, res) => {
  try {
    const message = await mailService.toggleImportantStatus(req.userId, req.params.id);
    res.status(200).json({ message });
  } catch (error) {
    console.error(error);
    res.status(error.status || 500).json({ error: error.message || 'Server error' });
  }
};

module.exports = {
  createMail, createDraftMail, getUserMails, getIdMails, updateMail,
  assignLabelsToMail, deleteMail, search, bulkUpdateReadStatus, markAsImportant
}

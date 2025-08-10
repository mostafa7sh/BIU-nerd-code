const mongoose = require('mongoose');

const mailSchema = new mongoose.Schema({
  from: { type: String, required: true },
  to: { type: String, required: true },
  subject: { type: String, required: true },
  body: { type: String, required: true },
  attachments: [
    {
      data: Buffer,
      contentType: String,
      originalName: String,
    }
  ],
  isRead: { type: Boolean, default: false },
  isImportant: { type: Boolean, default: false },
  label: { type: String, default: null },
  labels: [String],
  date: { type: Date, default: Date.now }
}, { timestamps: true });

module.exports = mongoose.model('Mail', mailSchema);

const mongoose = require('mongoose');

const userSchema = new mongoose.Schema({
  name: {
    type: String,
    required: true
  },
  email: {
    type: String,
    required: true,
    unique: true,
    match: /^[^\s@]+@(hotmail|icemail)\.com$/
  },
  password: {
    type: String,
    required: true,
    match: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/
  },
  profilePic: {
    data: Buffer,
    contentType: String
  },
  dateOfBirth: {
    type: String,
    required: true
  }
}, { timestamps: true });

module.exports = mongoose.model('User', userSchema);

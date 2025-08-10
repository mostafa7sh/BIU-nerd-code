const mongoose = require('mongoose');

const blacklistSchema = new mongoose.Schema({
  link: {
    type: String,
    required: true,
    unique: true
  }
});

module.exports = mongoose.model('Blacklist', blacklistSchema);

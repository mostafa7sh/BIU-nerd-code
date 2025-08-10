const User = require('../models/User');
const fs = require('fs');
const path = require('path');
const jwt = require('jsonwebtoken');
const JWT_SECRET = 'SalehMuradMostafa';

const signup = async (data, file) => {
  const { email, password, name, dateOfBirth } = data;

  if (!email || !password || !name || !dateOfBirth) {
    return { error: 'Name, email, password, and date of birth are required' };
  }

  const existingUser = await User.findOne({ email });
  if (existingUser) return { error: 'Email already registered', status: 409 };

  let profilePic = null;

  if (file) {
    profilePic = {
      data: file.buffer,
      contentType: file.mimetype,
    };
  } else {
    const defaultPicPath = path.join(__dirname, '..', 'uploads', 'default-avatar.jpg');
    try {
      const data = fs.readFileSync(defaultPicPath);
      profilePic = {
        data,
        contentType: 'image/jpeg', 
      };
    } catch (err) {
      console.error('Failed to load default avatar:', err);
    }
  }

  const newUser = new User({ name, email, password, dateOfBirth, profilePic });
  await newUser.save();

  return {
    message: 'Signup successful',
    user: { id: newUser._id, email: newUser.email }
  };
}

const login = async ({ email, password }) => {
  const user = await User.findOne({ email, password });
  if (!user) return { error: 'Invalid email or password', status: 401 };

  const token = jwt.sign({ id: user._id, email: user.email }, JWT_SECRET, { expiresIn: '3h' });

  return {
    message: 'Login successful',
    token,
    user: {
      id: user._id,
      email: user.email,
      name: user.name,
      profilePic: user.profilePic,
      dateOfBirth: user.dateOfBirth
    }
  };
}

const getUserInfo = async (userId) => {
  const user = await User.findById(userId);
  if (!user) return { error: 'User not found' };

  let profilePicUrl = null;
  if (user.profilePic && user.profilePic.data) {
    const base64 = user.profilePic.data.toString('base64');
    profilePicUrl = `data:${user.profilePic.contentType};base64,${base64}`;
  }

  return {
    id: user._id,
    email: user.email,
    name: user.name,
    profilePic: profilePicUrl,
    dateOfBirth: user.dateOfBirth
  };
};


const changePassword = async (userId, { currentPassword, newPassword }) => {
  const user = await User.findById(userId);
  if (!user) return { error: 'User not found', status: 404 };

  if (user.password !== currentPassword) {
    return { error: 'Current password is incorrect', status: 401 };
  }

  user.password = newPassword;
  await user.save();

  return { message: 'Password updated successfully' };
}

const updateUser = async (userId, { name }, file) => {
  const user = await User.findById(userId);
  if (!user) return { error: 'User not found', status: 404 };

  if (name) user.name = name;

  if (file) {
    user.profilePic = {
      data: file.buffer,
      contentType: file.mimetype
    };
  }

  await user.save();

  return {
    message: 'Profile updated successfully',
    user: {
      id: user._id,
      name: user.name,
      email: user.email,
      dateOfBirth: user.dateOfBirth,
      profilePic: `data:${user.profilePic.contentType};base64,${user.profilePic.data.toString('base64')}`
    }
  };
}

module.exports = { signup, login, getUserInfo, changePassword, updateUser }

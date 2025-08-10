const Mail = require('../models/Mail');
const Blacklist = require('../models/Blacklist');
const User = require('../models/User');

const extractLinks = (text) => {
  const urlRegex = /(https?:\/\/[^\s]+|www\.[^\s]+)/g;
  return text.match(urlRegex) || [];
}

const createMail = async ({ userId, to, subject, body, files }) => {
  const sender = await User.findById(userId);
  if (!sender) throw { status: 400, message: 'User not found.' };

  if (!to || !subject || !body)
    throw { status: 400, message: 'All fields are required' };

  const recipient = await User.findOne({ email: to });
  if (!recipient) throw { status: 400, message: 'Recipient user not found.' };

  const links = extractLinks(`${subject} ${body}`);
  const isSpam = await Blacklist.exists({ link: { $in: links } });

  const attachments = (files || []).map(file => ({
    data: file.buffer,
    contentType: file.mimetype,
    originalName: file.originalname
  }));

  const baseData = {
    from: sender.email,
    to,
    subject,
    body,
    attachments,
    isRead: false,
    labels: [],
    date: new Date()
  };

  const senderDoc = new Mail({ ...baseData, isRead: true, label: 'sent' });
  const recipientDoc = new Mail({ ...baseData, label: isSpam ? 'spam' : null });

  await Promise.all([senderDoc.save(), recipientDoc.save()]);

  return {
    message: isSpam
      ? 'Mail sent marked as spam'
      : 'Mail sent successfully',
    id: senderDoc._id
  };
}

const createDraftMail = async ({ userId, to, subject, body, files }) => {
  const sender = await User.findById(userId);
  if (!sender) throw { status: 400, message: 'User not found.' };

  const attachments = (files || []).map(file => ({
    data: file.buffer,
    contentType: file.mimetype,
    originalName: file.originalname
  }));

  const draftMail = new Mail({
    from: sender.email,
    to,
    subject,
    body,
    attachments,
    isRead: true,
    label: 'drafts',
    date: new Date(),
    labels: []
  });

  await draftMail.save();

  return {
    message: 'Mail saved to drafts',
    id: draftMail._id
  };
}

const getUserMails = async (userId) => {
  const user = await User.findById(userId);
  if (!user) throw { status: 400, message: 'User not found.' };

  const userEmail = user.email;

  const mails = await Mail.find({
    $or: [
      { to: userEmail, label: { $ne: 'sent' } },
      { from: userEmail, label: 'sent' }
    ]
  })
    .sort({ date: -1 })
    .limit(50)
    .lean();

  const uniqueMailIds = new Set();
  const populatedMails = [];

  for (const mail of mails) {
    if (mail.from === userEmail && mail.to === userEmail && mail.label === 'sent') {
      continue;
    }

    if (uniqueMailIds.has(mail._id.toString())) continue;
    uniqueMailIds.add(mail._id.toString());

    const senderUser = await User.findOne({ email: mail.from }).lean();

    let fromProfilePic = null;
    if (senderUser?.profilePic?.data && senderUser.profilePic?.contentType) {
      const base64 = senderUser.profilePic.data.toString('base64');
      fromProfilePic = `data:${senderUser.profilePic.contentType};base64,${base64}`;
    }

    populatedMails.push({
      ...mail,
      direction: mail.to === userEmail ? 'inbox' : 'sent',
      fromName: senderUser?.name || mail.from,
      fromProfilePic
    });
  }

  return populatedMails;
}


const getMailById = async (userId, mailId) => {
  const user = await User.findById(userId);
  if (!user) throw { status: 404, message: 'User not found.' };

  const mail = await Mail.findById(mailId).lean();
  if (!mail) throw { status: 404, message: 'Mail not found.' };

  const isUserMail = mail.from === user.email || mail.to === user.email;
  if (!isUserMail) throw { status: 403, message: 'Access denied.' };

  const senderUser = await User.findOne({ email: mail.from }).lean();

  let fromProfilePic = null;
  if (senderUser?.profilePic?.data) {
    const base64 = senderUser.profilePic.data.toString('base64');
    fromProfilePic = `data:${senderUser.profilePic.contentType};base64,${base64}`;
  }

  return {
    ...mail,
    direction: mail.to === user.email ? 'inbox' : 'sent',
    fromName: senderUser?.name || mail.from,
    fromProfilePic: fromProfilePic
  };
}

const updateMail = async (userId, mailId, updates, files) => {
  const user = await User.findById(userId);
  if (!user) throw { status: 404, message: 'User not found.' };

  const mail = await Mail.findById(mailId);
  if (!mail) throw { status: 404, message: 'Mail not found.' };

  const isOwner = mail.from === user.email || mail.to === user.email;
  if (!isOwner) throw { status: 403, message: 'Access denied.' };

  const updatableFields = ['subject', 'body', 'label', 'labels', 'isRead'];
  updatableFields.forEach(field => {
    if (updates[field] !== undefined) {
      mail[field] = updates[field];
    }
  });

  if (files && files.length > 0) {
    mail.attachments = files.map(file => ({
      data: file.buffer,
      contentType: file.mimetype,
      filename: file.originalname
    }));
  }

  if (mail.label === 'spam') {
    const fullText = `${mail.subject} ${mail.body}`;
    const urlRegex = /(https?:\/\/[^\s]+|www\.[^\s]+)/g;
    const links = fullText.match(urlRegex) || [];

    for (const link of links) {
      const exists = await Blacklist.findOne({ link });
      if (!exists) {
        await Blacklist.create({ link });
        console.log(`Blacklisted by user: ${link}`);
      }
    }
  }

  await mail.save();
  return mail;
}

const assignLabelsToMail = async (userId, mailId, newLabels) => {
  const user = await User.findById(userId);
  if (!user) throw { status: 403, message: 'Not logged in' };

  if (!Array.isArray(newLabels)) {
    throw { status: 400, message: 'Labels must be an array' };
  }

  const mail = await Mail.findById(mailId);
  if (!mail) throw { status: 404, message: 'Mail not found' };

  const isOwner = mail.from === user.email || mail.to === user.email;
  if (!isOwner) throw { status: 403, message: 'Access denied' };

  mail.labels = Array.from(new Set([...(mail.labels || []), ...newLabels]));
  await mail.save();

  return mail;
}

const deleteMail = async (userId, mailId) => {
  const user = await User.findById(userId);
  if (!user) throw { status: 404, message: 'User not found.' };

  const mail = await Mail.findById(mailId);
  if (!mail) throw { status: 404, message: 'Mail not found.' };

  const isUserCopy = mail.from === user.email || mail.to === user.email;
  if (!isUserCopy) throw { status: 403, message: 'Access denied.' };

  await Mail.deleteOne({ _id: mail._id });
}

const searchMails = async (userId, queryParam) => {
  const query = queryParam.toLowerCase();
  const user = await User.findById(userId);
  if (!user) throw { status: 404, message: 'User not found.' };

  const userEmail = user.email;

  const enrichMail = async (mail) => {
    const senderUser = await User.findOne({ email: mail.from }).lean();

    let fromProfilePic = null;
    if (senderUser?.profilePic?.data && senderUser.profilePic.contentType) {
      const base64 = senderUser.profilePic.data.toString('base64');
      fromProfilePic = `data:${senderUser.profilePic.contentType};base64,${base64}`;
    }

    return {
      ...mail.toObject(),
      direction: mail.to === userEmail ? 'inbox' : 'sent',
      fromName: senderUser?.name || mail.from,
      fromProfilePic
    };
  };

  let baseFilter = {
    $or: [
      { from: userEmail, label: 'sent' },
      { to: userEmail, label: { $in: [null, 'spam'] } }
    ]
  };

  let filter;

  switch (query) {
    case 'sent':
      filter = { from: userEmail, label: 'sent' };
      break;
    case 'received':
      filter = { to: userEmail, label: { $in: [null, ''] } };
      break;
    case 'spam':
      filter = { to: userEmail, label: 'spam' };
      break;
    case 'important':
      filter = {
        ...baseFilter,
        isImportant: true
      };
      break;
    case 'drafts':
      filter = { from: userEmail, label: 'drafts' };
      break;
    default:
      const labelMatches = await Mail.find({
        ...baseFilter,
        labels: { $in: [query] }
      });

      if (labelMatches.length > 0) {
        return await Promise.all(labelMatches.slice(0, 50).map(enrichMail));
      }

      filter = {
        ...baseFilter,
        $and: [
          { subject: { $regex: query, $options: 'i' } },
          { body: { $regex: query, $options: 'i' } },
          { from: { $regex: query, $options: 'i' } }
        ]
      };
  }

  const mails = await Mail.find(filter).sort({ date: -1 }).limit(50);
  return await Promise.all(mails.map(enrichMail));
}

const bulkUpdateReadStatus = async (userId, { ids, isRead }) => {
  const user = await User.findById(userId);
  if (!user) throw { status: 403, message: 'Not logged in' };

  if (!Array.isArray(ids) || typeof isRead !== 'boolean') {
    throw { status: 400, message: 'Invalid request body' };
  }

  const result = await Mail.updateMany(
    { _id: { $in: ids }, to: user.email },
    { $set: { isRead } }
  );

  return `${result.modifiedCount} mails updated`;
}

const toggleImportantStatus = async (userId, mailId) => {
  const user = await User.findById(userId);
  if (!user) throw { status: 403, message: 'Not authorized' };

  const mail = await Mail.findById(mailId);
  if (!mail || (mail.to !== user.email && mail.from !== user.email)) {
    throw { status: 404, message: 'Mail not found or access denied' };
  }

  mail.isImportant = !mail.isImportant;
  await mail.save();

  return mail.isImportant ? 'Marked as important' : 'Unmarked as important';
};

module.exports = {
  createMail, createDraftMail, getUserMails, getMailById, updateMail,
  assignLabelsToMail, deleteMail, searchMails, bulkUpdateReadStatus, toggleImportantStatus
}

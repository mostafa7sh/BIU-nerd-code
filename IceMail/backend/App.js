const express = require('express');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');
const cors = require('cors');

const apiRoutes = require('./routes/Api');

const app = express();

app.use(cors());

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

mongoose.connect(process.env.CONNECTION_STRING);

app.use('/api', apiRoutes);

app.get('/', (req, res) => {
  res.send('Gmail-like MVC Server is running');
});

app.listen(process.env.PORT);

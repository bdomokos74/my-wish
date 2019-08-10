var webpack = require('webpack');

module.exports = {
  plugins: [
    new webpack.DefinePlugin({
      GOOGLE_CLIENT_ID: JSON.stringify(process.env.GOOGLE_CLIENT_ID)
    })
  ]
};

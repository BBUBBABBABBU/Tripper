module.exports = {
  devServer: {
    proxy: {
      "^/login": {
        target: process.env.VUE_APP_API_URL,
        changeOrigin: true,
        secure: false,
        pathRewrite: { "^/login": "/login" },
        logLevel: "debug",
      },
      "/user": {
        target: process.env.VUE_APP_API_URL,
      },
      "/board": {
        target: process.env.VUE_APP_API_URL,
      },
    },
  },
};

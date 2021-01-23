const path = require('path')
const resolve = dir => path.join(__dirname, dir)

module.exports = {
    devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost:54046',
                changeOrigin: true,
                pathRewrite: {
                    '^/api': ''
                }
            }
        }
    },
}


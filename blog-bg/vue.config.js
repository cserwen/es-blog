module.exports={
    devServer: {
        proxy:{
            '/api': {
                target: 'http://localhost:54046',
                changeOrigin: true,
                ws: true,
                pathRewrite: {
                    '^/api':''
                }
            }
        }
    }
}
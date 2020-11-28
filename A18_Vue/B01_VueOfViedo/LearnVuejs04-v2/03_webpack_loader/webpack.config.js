const path = require('path')

module.exports = {
    entry : './src/main.js',
    output : {
        path: path.resolve(__dirname,'dist'),
        filename: 'bundle.js'
    },
    module:{
        rules:[
            {
                test:/\.css$/,
                // css-loader，只负责加载
                use: [{
                    loader: 'style-loader'
                },{
                    loader: 'css-loader'
                }]
            },
            {
                test:/\.less$/,
                // css-loader，只负责加载
                use: [{
                    loader: 'style-loader'
                },{
                    loader: 'css-loader'
                },{
                    loader: 'less-loader'
                }]
            }
        ]
    }
}
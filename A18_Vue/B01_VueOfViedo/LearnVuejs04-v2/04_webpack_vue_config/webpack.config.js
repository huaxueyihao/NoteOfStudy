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
            },
            {
                test:/\.(png|jpg|gif|jpeg)$/,
                // css-loader，只负责加载
                use: [{
                    loader: 'url-loader',
                    options:{
                        limit: 8192,
                        name: 'img/[name].[hash:8].[ext]'
                    }
                }]
            },
            {
                test: /\.js$/,
                exclude: /(node_modules|bower_components)/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['es2015']
                    }
                }
            },
            {
               test: /\.vue$/,
               use: ['vue-loader']
            }
        ]
    },
    resolve:{
        extensions:['.js','.css','.vue'],
        alias:{
            'vue$': 'vue/dist/vue.esm.js'
        }
    }

}
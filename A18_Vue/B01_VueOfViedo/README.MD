### 常用的安装命令



> 安装css-loader
```

cnpm i --save-dev style-loader
cnpm i --save-dev css-loader
cnpm i --save-dev less-loader
cnpm i --save-dev url-loader

```


>  安装配置babel
```

# 安装babel
cnpm i --save-dev babel-loader@7 babel-core babel-preset-es2015

```

>  在webpack.config.js中配置
```
{
    test: /\.js$/,
    exclude: /(node_modules|bower_components)/,
    use: {
        loader: 'babel-loader',
        options: {
            presets: ['es2015']
        }
    }
}

```

> 安装vue-loader

```
cnpm install vue-loader@15.4.2 vue-template-compiler@2.5.21 --save-dev



{
    test: /\.vue$/,
    use: ['vue-loader']
}

```

> 安装plugins

```
cnpm install html-webpack-plugin@3.2.0 --save-dev



```

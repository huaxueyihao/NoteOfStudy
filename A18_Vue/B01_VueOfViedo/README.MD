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
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

cnpm install uglifyjs-webpack-plugin@1.1.1 --save-dev

cnpm install webpack-dev-server@2.9.3 --save-dev

cnpm install webpack-merge@4.1.5 --save-dev



```

> 安装Vue CLi

```
# 安装脚手架3
cnpm install -g @vue/cli

# 安装脚2.x的模板
cnpm install -g @vue/cli-init

#Vue CLI2初始化项目
vue init webpack my-project

#Vue CLi3初始化项目
vue create my-project

# runtimecompiler
vue init webpack runtimecompiler

# runtimeonly
vue init webpack runtimeonly

npm clean cache -force

#vue-cli3
vue create project_name

#安装路由
cnpm install vue-router --save





```


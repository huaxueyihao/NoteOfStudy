// 1.使用commonjs的模块化规范
const {add,mul} = require('./js/mathUtil.js')

console.log(add(20, 44));
console.log(mul(20, 30));



import {age,name,height} from './js/info';

console.log(age);
console.log(name);
console.log(height);


// 3.依赖css文件
require('./css/normal.css')

// 4.依赖less文件
require('./css/special.less')
document.writeln('<h2>你好啊</h2>')

// 5. 使用Vue进行开发
import  Vue from 'vue';
const app = new Vue({
    el: '#app',
    data:{
        message: 'Hello webpack'
    }
})





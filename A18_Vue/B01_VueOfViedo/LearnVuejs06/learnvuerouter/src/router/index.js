// 配置路由相关的信息
import VueRouter from 'vue-router'
import Vue from 'vue'
// import About from '../components/About'
// import Home from '../components/Home'
// import User from '../components/User'


const User = () => import('../components/User')
const Home = () => import('../components/Home')
const About = () => import('../components/About')
const HomeMessage = () => import('../components/HomeMessage')
const HomeNews = () => import('../components/HomeNews')
const Profile = () => import('../components/Profile')


// 1.通过Vue.use(插件),安装插件
Vue.use(VueRouter)

// 2.创建VueRouter对象

const routes = [
    {
        path: '',
        redirect: '/home'
    },
    {
        path: '/home',
        component: Home,
        meta: {
            title: '首页'
        },
        children: [
            {
                path: 'news',
                component: HomeNews
            },
            {
                path: 'message',
                component: HomeMessage
            },
        ]
    },
    {
        path: '/about',
        component: About,
        meta: {
            title: '关于'
        }
    },
    {
        path: '/user/:userId',
        component: User,
        meta: {
            title: '用户'
        },
    },
    {
        path: '/profile',
        component: Profile,
        meta: {
            title: '档案'
        },
    }
]
const router = new VueRouter({
    // 配置路由和组件之间的应用关系
    routes,
    mode: 'history'
})

router.beforeEach((to,from,next)=>{
    // 从from跳转到to
    document.title = to.matched[0].meta.title
    next()
})

// 3.将router对象传入到Vue实例

export default router;




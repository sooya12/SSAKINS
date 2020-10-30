import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '@/views/Main.vue'
import Notice from '@/views/Notice.vue'
import Create from '@/views/Create.vue'

Vue.use(VueRouter)

const routes =[
    {
        path : '/main',
        name : 'main',
        component : Main
    },
    {
        path : '/notice',
        name : 'notice',
        component : Notice
    },
    {
        path : '/create',
        name : 'create',
        component : Create
    },
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '@/views/Main.vue'
import test from '@/views/test.vue'

Vue.use(VueRouter)

const routes =[
    {
        path : '/main',
        name : 'Main',
        component : Main
    },
    {
        path : '/test',
        name : 'test',
        component : test
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '@/views/Main.vue'
import Notice from '@/views/Notice.vue'
import Create from '@/views/Create.vue'
import Game from '@/views/Game.vue'

Vue.use(VueRouter)

const routes =[
    {
        path : '/main',
        name : 'Main',
        component : Main
    },
    {
        path : '/notice',
        name : 'Notice',
        component : Notice
    },
    {
        path : '/create',
        name : 'Create',
        component : Create
    },
    {
        path : '/game',
        name : 'Game',
        component : Game
    },
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
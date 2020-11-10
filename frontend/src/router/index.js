import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '@/views/Home.vue'
import Main from '@/views/Main.vue'
import Notice from '@/views/Notice.vue'
import Create from '@/views/Create.vue'
import Game from '@/views/Game.vue'
import Edit from '@/views/Edit.vue'
import Detail from '@/views/Detail.vue'
import Login from '@/components/LoginComponent'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/create',
    name: 'Create',
    component: Create
  },
  {
    path: '/main',
    name: 'Main',
    component: Main
  },
  {
    path: '/edit',
    name: 'Edit',
    component: Edit 
  },
  {
    path: '/detail',
    name: 'Detail',
    component: Detail
  },
  {
    path: '/login/:email',
    name: 'Login',
    component: Login,
    props: true
  },
{
    path : '/notice',
    name : 'Notice',
    component : Notice
},
{
    path: '/home',
    name: 'Home',
    component: Home
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
import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '@/views/Home.vue'
import Main from '@/views/Main.vue'
import Reference from '@/views/Reference.vue'
import Create from '@/views/Create.vue'
import Game from '@/views/Game.vue'
import Edit from '@/views/Edit.vue'
import Detail from '@/views/Detail.vue'
import Login from '@/components/LoginComponent'
import PageNotFound from '@/views/PageNotFound'
import Test from '@/views/Test'
import TermsOfUse from "@/views/TermsOfUse";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path : '/main',
    name : 'Main',
    component : Main
  },
  {
    path : '/reference',
    name : 'Reference',
    component : Reference
  },
  {
    path : '/create',
    name : 'Create',
    component : Create
  },
  {
    path: '/edit',
    name: 'Edit',
    props: true,
    component: Edit 
  },
  {
    path: '/test',
    name: 'Test',
    component :Test
  },
  {
    path: '/detail',
    name: 'Detail',
    props: true,
    component: Detail
  },
  {
    path : '/game',
    name : 'Game',
    component : Game
  },
  {
    path: '/login/:email/:name/:image',
    name: 'Login',
    component: Login,
    props: true
  },
  {
    path : '*',
    redirect : '/404',
  },
  {
    path: '/404',
    component : PageNotFound,
  },
  {
    path: '/termsofuse',
    name: 'TermsOfUse',
    component: TermsOfUse
  }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
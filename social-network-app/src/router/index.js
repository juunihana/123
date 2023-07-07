import { createRouter, createWebHistory } from 'vue-router'
import  UserProfile         from '../views/UserProfile.vue'
import  About               from '../views/About.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'userProfile',
      component: UserProfile
    },
    {
      path: '/about',
      name: 'about',
      component: About
    }
  ]
})

export default router

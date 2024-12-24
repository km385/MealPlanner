import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const AboutView = () => import('@/views/AboutView.vue')
const LoginView = () => import('@/views/LoginView.vue')
const RegisterView = () => import('@/views/RegisterView.vue')

const paths = [
  {
    path: '/',
    name: 'Meal Planner',
    component: HomeView,
  },
  {
    path: '/about',
    name: 'About',
    component: AboutView,
  },
  {
    path: '/login',
    name: 'Login page',
    component: LoginView
  },
  {
    path: '/register',
    name: 'Register page',
    component: RegisterView,
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: paths,
})

// Set the title of the page to the name of the route
router.beforeEach((to, from, next) => {
  document.title = to.name
  next()
});

export default router

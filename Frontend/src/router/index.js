import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import { useAuthStore } from '@/stores/auth'

const AboutView = () => import('@/views/AboutView.vue')
const LoginView = () => import('@/views/LoginView.vue')
const RegisterView = () => import('@/views/RegisterView.vue')
const UserRecipesView = () => import('@/views/UserRecipesView.vue')
const RecipeDetailsView = () => import('@/views/RecipeDetailsView.vue')
const CreateNewRecipeView = () => import('@/views/CreateNewRecipeView.vue')
const MealPlanView = () => import('@/views/MealPlannerView.vue')

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
    component: LoginView,
  },
  {
    path: '/register',
    name: 'Register page',
    component: RegisterView,
  },
  {
    path: '/recipes',
    name: 'My recipes',
    component: UserRecipesView,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: '/recipes/:id',
    name: 'RecipeDetails',
    component: RecipeDetailsView,
    meta: { requiresAuth: true },
  },
  {
    path: '/recipes/add',
    name: 'Add recipe',
    component: CreateNewRecipeView,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: '/mealplan',
    name: 'mealplan',
    component: MealPlanView,
    meta: {
      requiresAuth: true,
    },
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: paths,
})

// Auth guard
router.beforeEach((to) => {
  const auth = useAuthStore()

  if (to.meta.requiresAuth && !auth.isAuthenticated) {
    return { name: 'Login page' }
  }
})

// Set the title of the page to the name of the route
router.beforeEach((to) => {
  document.title = to.name
})

export default router

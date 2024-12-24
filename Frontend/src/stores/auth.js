import { defineStore } from 'pinia'
import { ref } from 'vue'
import { useCookies } from '@/composables/useCookies'

export const useAuthStore = defineStore('auth', () => {
  const isAuthenticated = ref(false)
  const cookies = useCookies()

  const login = (token, expiresIn) => {
    cookies.setCookie('jwt', token, expiresIn)
    isAuthenticated.value = true
  }

  const logout = () => {
    cookies.removeCookie('jwt')
    isAuthenticated.value = false
    console.log(`wylogownaie ${isAuthenticated.value}`)
  }

  const checkAuth = () => {
    const token = cookies.getCookie('jwt')
    isAuthenticated.value = !!token
    
    return isAuthenticated.value
  }

  return {
    isAuthenticated,
    login,
    logout,
    checkAuth
  }
})
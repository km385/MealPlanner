<script setup>
import { RouterLink, RouterView, useRouter } from 'vue-router'
import { onMounted } from 'vue'
import { useAuthStore } from './stores/auth'

const auth = useAuthStore()

onMounted(() => {
  auth.checkAuth()
})
</script>

<template>
  <header>

    <div class="wrapper">
      <nav class="flex items-center justify-between px-2 h-[theme(height.navbar)] bg-gray-600 ">
        <div class="flex items-center">
          <RouterLink class="btn-nav" to="/">Home</RouterLink>
          <RouterLink class="btn-nav" to="/about">About</RouterLink>
        </div>
        <div class="flex items-center">
          <RouterLink v-if="!auth.isAuthenticated" class="btn-nav" to="/login">Login</RouterLink>
          <RouterLink v-if="!auth.isAuthenticated" class="btn-nav" to="/register">Register</RouterLink>
          <button v-if="auth.isAuthenticated" @click="auth.logout" class="btn-nav">Logout</button>
        </div>
      </nav>
    </div>
  </header>

  <RouterView />
</template>


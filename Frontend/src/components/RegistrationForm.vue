<template>
  <div class="h-[calc(100vh-theme(height.navbar))] flex items-center justify-center bg-gray-100">
    <div class="bg-white p-8 rounded-lg shadow-lg w-full max-w-md">
      <h2 class="text-2xl font-bold mb-6 text-center">Register</h2>
      <form @submit.prevent="handleRegister">
        <div class="mb-4">
          <label for="fullName" class="block text-gray-700">Full Name</label>
          <input
            type="text"
            id="fullName"
            v-model="fullName"
            class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:border-blue-300"
            required
          />
          <span v-if="formErrors.fullName" class="error">{{ formErrors.fullName }}</span>
        </div>
        <div class="mb-4">
          <label for="email" class="block text-gray-700">Email</label>
          <input
            type="email"
            id="email"
            v-model="email"
            class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:border-blue-300"
            required
          />
          <span v-if="formErrors.email" class="error">{{ formErrors.email }}</span>
        </div>
        <div class="mb-6">
          <label for="password" class="block text-gray-700">Password</label>
          <input
            type="password"
            id="password"
            v-model="password"
            class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:border-blue-300"
            required
          />
          <span v-if="formErrors.password" class="error">{{ formErrors.password }}</span>
        </div>
        <button
          type="submit"
          class="w-full bg-blue-500 text-white py-2 rounded-lg hover:bg-blue-600 transition duration-300"
        >
          Register
        </button>
        <div v-if="errorMessage" class="text-red-500 mt-4 text-center">
          {{ errorMessage }}
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useAuthStore } from '@/stores/auth'
import { useRouter } from 'vue-router'

const router = useRouter()
const auth = useAuthStore()

const fullName = ref('j')
const email = ref('test@test.com')
const password = ref('password')
const formErrors = ref({})
const errorMessage = ref('')
const handleRegister = async () => {
  formErrors.value = {}
  try {
    await axios.post('/auth/signup', {
      fullName: fullName.value,
      email: email.value,
      password: password.value,
    })
    await loginAfterRegister()
  } catch (error) {
    if (error.response && error.response.status === 400) {
      const errors = error.response.data.details
      formErrors.value = errors
    } else {
      errorMessage.value = error.response.data.details
    }
  }
}

const loginAfterRegister = async () => {
  try {
    const response = await axios.post('/auth/login', {
      email: email.value,
      password: password.value,
    })
    const jwt = response.data.token
    const expiresIn = response.data.expiresIn
    auth.login(jwt, expiresIn)
    router.push('/')
  } catch (error) {
    console.error(error)
  }
}
</script>

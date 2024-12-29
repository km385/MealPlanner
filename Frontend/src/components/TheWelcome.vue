<template>
  <div class="min-h-screen bg-gray-50">
    <!-- Hero Section -->
    <div class="container mx-auto px-4 py-16">
      <div class="text-center max-w-3xl mx-auto">
        <h1 class="text-4xl md:text-6xl font-bold text-gray-800 mb-6">Welcome to MealPlanner</h1>
        <p class="text-xl text-gray-600 mb-8">
          Plan your meals, organize your recipes, and simplify your cooking journey
        </p>
        <div class="flex justify-center gap-4">
          <RouterLink
            to="/register"
            class="px-6 py-3 bg-gray-600 text-white rounded-lg hover:bg-gray-700 transition-colors duration-200"
          >
            Get Started
          </RouterLink>
          <RouterLink
            to="/login"
            class="px-6 py-3 border-2 border-gray-600 text-gray-600 rounded-lg hover:bg-gray-100 transition-colors duration-200"
          >
            Sign In
          </RouterLink>
        </div>
      </div>
    </div>

    <!-- Features Section -->
    <div class="container mx-auto px-4 py-16">
      <div class="grid md:grid-cols-2 gap-8">
        <div
          class="p-6 bg-white rounded-lg shadow-lg transform hover:scale-105 transition-transform duration-200"
        >
          <h3 class="text-xl font-semibold text-gray-800 mb-4">Recipe Management</h3>
          <p class="text-gray-600">Store and organize your favorite recipes in one place</p>
        </div>
        <div
          class="p-6 bg-white rounded-lg shadow-lg transform hover:scale-105 transition-transform duration-200"
        >
          <h3 class="text-xl font-semibold text-gray-800 mb-4">Meal Planning</h3>
          <p class="text-gray-600">Plan your weekly meals with our intuitive calendar</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { RouterLink } from 'vue-router'
</script>

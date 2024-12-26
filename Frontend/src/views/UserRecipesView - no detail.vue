<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'
import { useCookies } from '@/composables/useCookies'

const router = useRouter()
const recipes = ref([])

const fetchRecipes = async () => {
  try {
    const response = await axios.get('/recipes', {
      headers: {
        Authorization: `Bearer ${useCookies().getCookie('jwt')}`,
      },
    })
    recipes.value = response.data
  } catch (error) {
    console.error('Error fetching recipes:', error)
  }
}

const navigateToDetails = (recipeId) => {
  router.push(`/recipes/${recipeId}`)
}

onMounted(() => {
  fetchRecipes()
})
</script>

<template>
  <div class="container mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold mb-8">My Recipes</h1>
    
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <div v-for="recipe in recipes" 
           :key="recipe.id" 
           @click="navigateToDetails(recipe.id)"
           class="bg-white rounded-lg shadow-lg overflow-hidden hover:shadow-xl transition-shadow duration-300 cursor-pointer">
        
        <div class="bg-gray-800 text-white p-4">
          <h2 class="text-xl font-semibold">{{ recipe.name }}</h2>
          <p class="text-sm text-gray-300">by {{ recipe.userName }}</p>
        </div>

        <div class="p-4">
          <p class="text-gray-600">{{ recipe.ingredients.length }} ingredients</p>
          <p class="text-gray-600 mt-2 line-clamp-2">{{ recipe.instructions }}</p>
        </div>
      </div>
    </div>
  </div>
</template>
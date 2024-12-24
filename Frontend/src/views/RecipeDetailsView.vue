<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'
import { useCookies } from '@/composables/useCookies'

const route = useRoute()
const router = useRouter()
const recipe = ref(null)

const fetchRecipe = async () => {
  try {
    const response = await axios.get(`/recipes/${route.params.id}`, {
      headers: {
        Authorization: `Bearer ${ useCookies().getCookie('jwt') }`,
      }
    })
    recipe.value = response.data
    console.log(recipe.value)
  } catch (error) {
    console.error('Error fetching recipe:', error)
  }
}

onMounted(() => {
  fetchRecipe()
})
</script>

<template>
  <div v-if="recipe" class="container mx-auto px-4 py-8">
    <button @click="router.back()" 
            class="mb-4 px-4 py-2 bg-gray-800 text-white rounded hover:bg-gray-700">
      Back to Recipes
    </button>

    <div class="bg-white rounded-lg shadow-lg overflow-hidden">
      <div class="bg-gray-800 text-white p-6">
        <h1 class="text-3xl font-bold">{{ recipe.name }}</h1>
        <p class="text-gray-300 mt-2">Created by {{ recipe.userName }}</p>
      </div>

      <div class="p-6">
        <div class="mb-8">
          <h2 class="text-2xl font-semibold mb-4">Ingredients</h2>
          <ul class="space-y-3">
            <li v-for="item in recipe.ingredients" 
                :key="item.ingredient.id"
                class="flex justify-between items-center bg-gray-50 p-3 rounded">
              <span class="font-medium">{{ item.ingredient.name }}</span>
              <span class="text-gray-600">
                {{ item.quantity }} {{ item.ingredient.unitOfMeasure }}
              </span>
            </li>
          </ul>
        </div>

        <div>
          <h2 class="text-2xl font-semibold mb-4">Instructions</h2>
          <p class="text-gray-600 whitespace-pre-line">{{ recipe.instructions }}</p>
        </div>
      </div>
    </div>
  </div>
</template>
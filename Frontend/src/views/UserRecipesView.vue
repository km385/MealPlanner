<template>
  <div class="container mx-auto px-4 py-8">
    <div class="flex justify-between items-center mb-8">
      <h1 class="text-3xl font-bold">My Recipes</h1>
      <button
        @click="router.push('/recipes/add')"
        class="px-4 py-2 bg-gray-600 text-white rounded hover:bg-gray-700 transition-colors duration-200 inline-flex items-center gap-2"
      >
        <IconPlus class="h-6 w-6" />
        Add Recipe
      </button>
    </div>
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <!-- Recipe Card -->
      <div
        v-for="recipe in recipes"
        :key="recipe.id"
        @click="navigateToDetails(recipe.id)"
        class="cursor-pointer bg-white rounded-lg shadow-lg overflow-hidden hover:scale-105 hover:shadow-xl transition-all duration-300 ease-in-out"
      >
        <!-- Recipe Header -->
        <div class="bg-gray-800 text-white p-4">
          <h2 class="text-xl font-semibold">{{ recipe.name }}</h2>
        </div>

        <!-- Recipe Content -->
        <div class="p-4">
          <!-- Ingredients Section -->
          <div class="mb-4">
            <h3 class="text-lg font-semibold mb-2 text-gray-700">Ingredients:</h3>
            <ul class="space-y-2">
              <li
                v-for="item in recipe.ingredients"
                :key="item.ingredient.id"
                class="flex justify-between items-center bg-gray-50 p-2 rounded"
              >
                <span class="font-medium">{{ item.ingredient.name }}</span>
                <span class="text-gray-600">
                  {{ item.quantity }} {{ item.ingredient.unitOfMeasure }}
                </span>
              </li>
            </ul>
          </div>

          <!-- Instructions Section -->
          <div>
            <h3 class="text-lg font-semibold mb-2 text-gray-700">Instructions:</h3>
            <p class="text-gray-600 whitespace-pre-line">{{ recipe.instructions }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios'
import { onMounted } from 'vue'
import { useCookies } from '@/composables/useCookies'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import IconPlus from '@/components/icons/IconPlus.vue'

const recipes = ref([])
const router = useRouter()

const navigateToDetails = (recipeId) => {
  router.push(`/recipes/${recipeId}`)
}

const fetchRecipes = async () => {
  try {
    const response = await axios.get('/recipes', {
      headers: {
        Authorization: `Bearer ${useCookies().getCookie('jwt')}`,
      },
    })
    recipes.value = response.data
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  fetchRecipes()
})
</script>

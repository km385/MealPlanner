<template>
  <div class="container mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold mb-8">Meal Planner</h1>

    <!-- Weekly Schedule Grid -->
    <div class="grid grid-cols-8 gap-4">
      <!-- Time Slots Column -->
      <div class="col-span-1">
        <div class="h-16"></div>
        <!-- Empty space for alignment -->
        <div
          v-for="mealType in mealTypes"
          :key="mealType"
          class="h-32 flex items-center font-semibold"
        >
          {{ formatMealType(mealType) }}
        </div>
      </div>

      <!-- Days Columns -->
      <div v-for="day in daysOfWeek" :key="day" class="col-span-1">
        <div
          class="h-16 flex items-center justify-center font-bold bg-gray-800 text-white rounded-t"
        >
          {{ formatDay(day) }}
        </div>
        <div v-for="mealType in mealTypes" :key="mealType" class="relative h-32 border p-2">
          <div v-if="getMeal(day, mealType)" class="h-full">
            <div class="bg-blue-100 h-full p-2 rounded flex flex-col">
              <span class="font-medium text-sm">{{ getMeal(day, mealType).recipeName }}</span>
              <button
                @click="removeMeal(day, mealType)"
                class="text-red-600 text-xs mt-1 hover:text-red-800"
              >
                Remove
              </button>
            </div>
          </div>
          <button
            v-else
            @click="openRecipeSelector(day, mealType)"
            class="absolute inset-0 w-full h-full flex items-center justify-center text-gray-400 hover:text-gray-600"
          >
            + Add meal
          </button>
        </div>
      </div>
    </div>

    <!-- Recipe Selection Modal -->
    <div
      v-if="showModal"
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center"
    >
      <div class="bg-white p-6 rounded-lg w-96">
        <h2 class="text-xl font-bold mb-4">Select Recipe</h2>
        <div class="max-h-96 overflow-y-auto">
          <div
            v-for="recipe in recipes"
            :key="recipe.id"
            @click="assignRecipe(recipe.id)"
            class="p-3 hover:bg-gray-100 cursor-pointer rounded"
          >
            {{ recipe.name }}
          </div>
        </div>
        <button
          @click="showModal = false"
          class="mt-4 px-4 py-2 bg-gray-600 text-white rounded hover:bg-gray-700"
        >
          Cancel
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useCookies } from '@/composables/useCookies'

const mealTypes = ['BREAKFAST', 'LUNCH', 'DINNER', 'SNACK']
const daysOfWeek = ['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY']

const mealPlan = ref([])
const recipes = ref([])
const showModal = ref(false)
const selectedDay = ref(null)
const selectedMealType = ref(null)

const formatDay = (day) => {
  return day.charAt(0) + day.slice(1).toLowerCase()
}

const formatMealType = (type) => {
  return type.charAt(0) + type.slice(1).toLowerCase()
}

const getMeal = (day, mealType) => {
  return mealPlan.value.find((meal) => meal.dayOfWeek === day && meal.mealType === mealType)
}

const openRecipeSelector = (day, mealType) => {
  selectedDay.value = day
  selectedMealType.value = mealType
  showModal.value = true
}

const assignRecipe = async (recipeId) => {
  try {
    const response = await axios.post(
      `/meal-plan/${recipeId}/${selectedDay.value}/${selectedMealType.value}`,
      {},
      {
        headers: {
          Authorization: `Bearer ${useCookies().getCookie('jwt')}`,
        },
      },
    )
    mealPlan.value.push(response.data)
    showModal.value = false
  } catch (error) {
    console.error('Error assigning recipe:', error)
  }
}

const removeMeal = async (day, mealType) => {
  try {
    await axios.delete(`/meal-plan/${day}/${mealType}`, {
      headers: {
        Authorization: `Bearer ${useCookies().getCookie('jwt')}`,
      },
    })
    const index = mealPlan.value.findIndex(
      (meal) => meal.dayOfWeek === day && meal.mealType === mealType,
    )
    if (index !== -1) {
      mealPlan.value.splice(index, 1)
    }
  } catch (error) {
    console.error('Error removing meal:', error)
  }
}

const fetchMealPlan = async () => {
  try {
    const response = await axios.get('/meal-plan/week', {
      headers: {
        Authorization: `Bearer ${useCookies().getCookie('jwt')}`,
      },
    })
    mealPlan.value = response.data
  } catch (error) {
    console.error('Error fetching meal plan:', error)
  }
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
    console.error('Error fetching recipes:', error)
  }
}

onMounted(() => {
  fetchMealPlan()
  fetchRecipes()
})
</script>

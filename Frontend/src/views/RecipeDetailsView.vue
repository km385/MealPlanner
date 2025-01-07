<template>
  <div class="container mx-auto px-4 py-8">
    <button
      @click="isReadonly ? router.back(): cancelEdit()"
      class="mb-4 px-4 py-2 bg-gray-600 text-white rounded hover:bg-gray-700 transition-colors duration-200 inline-flex items-center gap-2"
    >
      <IconBack class="h-6 w-6" />
      Back to Recipes
    </button>
    <TheForm
      v-if="recipe"
      :recipe="recipe"
      :readonly="isReadonly"
      :error-messages="errorMessages"
      @save:recipe="saveEdit"
      @edit:recipe="enterEditMode"
      @cancel:edit="cancelEdit"
      @delete:recipe="deleteRecipe"
      @update:errors="updateErrors"
    />
  </div>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router'
import axios from 'axios'
import { useCookies } from '@/composables/useCookies'
import { readonly, ref } from 'vue'
import TheForm from '../components/RecipeInfo/TheForm.vue'
import { onMounted } from 'vue'
import IconBack from '@/components/icons/IconBack.vue'
const router = useRouter()
const route = useRoute()

const recipe = ref(null)
const originalRecipe = ref(null)

const enterEditMode = () => {
  originalRecipe.value = JSON.parse(JSON.stringify(recipe.value))
  isReadonly.value = false
}

const cancelEdit = () => {
  recipe.value = JSON.parse(JSON.stringify(originalRecipe.value))
  isReadonly.value = true
  router.go()
}
const isReadonly = ref(true)

const errorMessages = ref(null)

const saveEdit = async (newRecipe) => {
  recipe.value = newRecipe
  try {
    await axios.put(`/recipes/${route.params.id}`, newRecipe, {
      headers: {
        Authorization: `Bearer ${useCookies().getCookie('jwt')}`,
      },
    })
    isReadonly.value = true
  } catch (e) {
    errorMessages.value = e.response.data
  }
}

const updateErrors = (newErrors) => {
  errorMessages.value = {
    ...errorMessages.value,
    details: newErrors,
  }
}

const deleteRecipe = async () => {
  await axios.delete(`/recipes/${route.params.id}`, {
    headers: {
      Authorization: `Bearer ${useCookies().getCookie('jwt')}`,
    },
  })
  router.push('/recipes')
}

const fetchRecipe = async () => {
  try {
    const response = await axios.get(`/recipes/${route.params.id}`, {
      headers: {
        Authorization: `Bearer ${useCookies().getCookie('jwt')}`,
      },
    })
    recipe.value = response.data
  } catch (error) {
    if (error.response.status === 404) {
      console.error('Recipe not found')
      // router.push('/404')
    } else if (error.response.status === 403) {
      router.push('/recipes')
    } else {
      console.error('Error fetching recipe:', error)
    }
  }
}

onMounted(() => {
  fetchRecipe()
})
</script>

<template>
  <div class="container mx-auto px-4 py-8">
    <TheForm
      :recipe="recipe"
      :readonly="isReadOnly"
      :error-messages="errorMessages"
      @save:recipe="saveEdit"
      @cancel:edit="cancelEdit"
      @update:errors="updateErrors"
    />
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useCookies } from '@/composables/useCookies'
import { useRouter } from 'vue-router'
import TheForm from '../components/RecipeInfo/TheForm.vue'

const router = useRouter()
const recipe = ref({
  name: '',
  ingredients: [],
  instructions: '',
})

const isReadOnly = ref(false)
const errorMessages = ref(null)

const saveEdit = async () => {
  try {
    await axios.post(`/recipes`, recipe.value, {
      headers: {
        Authorization: `Bearer ${useCookies().getCookie('jwt')}`,
      },
    })
    isReadOnly.value = true
    router.push('/recipes')
  } catch (error) {
    errorMessages.value = error.response.data
  }
}

const cancelEdit = () => {
  isReadOnly.value = true
  router.push('/recipes')
}

const updateErrors = (newErrors) => {
  errorMessages.value = {
    ...errorMessages.value,
    details: newErrors,
  }
}
</script>
